package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.config.Constants;
import com.rcsoyer.servicosjuridicos.domain.Authority;
import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.repository.AuthorityRepository;
import com.rcsoyer.servicosjuridicos.repository.UserRepository;
import static com.rcsoyer.servicosjuridicos.repository.UserRepository.USERS_BY_EMAIL_CACHE;
import static com.rcsoyer.servicosjuridicos.repository.UserRepository.USERS_BY_LOGIN_CACHE;
import com.rcsoyer.servicosjuridicos.security.AuthoritiesConstants;
import com.rcsoyer.servicosjuridicos.security.SecurityUtils;
import com.rcsoyer.servicosjuridicos.service.dto.UserDTO;
import com.rcsoyer.servicosjuridicos.service.util.RandomUtil;
import com.rcsoyer.servicosjuridicos.web.rest.errors.InvalidPasswordException;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {
    
    private final CacheManager cacheManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthorityRepository authorityRepository, CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }
    
    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        UnaryOperator<User> activateUser = user -> {
            user.setActivated(TRUE).setActivationKey(null);
            clearUserCaches(user);
            log.debug("Activated user: {}", user);
            return user;
        };
        return userRepository.findOneByActivationKey(key)
                             .map(activateUser);
    }
    
    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        UnaryOperator<User> resetUserPasswd = user -> {
            String encripytedPasswd = passwordEncoder.encode(newPassword);
            return user.setPassword(encripytedPasswd).setKeyDateResetToNull();
        };
        return userRepository.findOneByResetKey(key)
                             .filter(User::isResetDateUpToDate)
                             .map(resetUserPasswd.andThen(this::clearUserCaches));
    }
    
    public Optional<User> requestPasswordReset(String mail) {
        UnaryOperator<User> resetKeyDate = User::setKeyDateResetDefault;
        return userRepository.findOneByEmailIgnoreCase(mail)
                             .filter(User::getActivated)
                             .map(resetKeyDate.andThen(this::clearUserCaches));
    }
    
    public User registerUser(UserDTO userDTO, String password) {
        String encryptedPassword = passwordEncoder.encode(password);
        String activationKey = RandomUtil.generateActivationKey();
        UnaryOperator<User> findUserAuth = user -> {
            authorityRepository.findById(AuthoritiesConstants.USER)
                               .ifPresent(user.getAuthorities()::add);
            return user;
        };
        UnaryOperator<User> setUserState = user ->
                                               user.setLogin(userDTO.getLogin())
                                                   .setPassword(encryptedPassword)
                                                   .setFirstName(userDTO.getFirstName())
                                                   .setLastName(userDTO.getLastName())
                                                   .setEmail(userDTO.getEmail())
                                                   .setImageUrl(userDTO.getImageUrl())
                                                   .setLangKey(userDTO.getLangKey())
                                                   .setActivated(FALSE)
                                                   .setActivationKey(activationKey);
        return setUserState.andThen(findUserAuth)
                           .andThen(userRepository::save)
                           .andThen(this::clearUserCaches)
                           .andThen(logCreatedUser())
                           .apply(new User());
    }
    
    public User createUser(UserDTO userDTO) {
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        BiFunction<User, UserDTO, User> setUserAuthorities = this::setUserAuthorities;
        UnaryOperator<User> setUserState = usr ->
                                               usr.setLogin(userDTO.getLogin())
                                                  .setFirstName(userDTO.getFirstName())
                                                  .setLastName(userDTO.getLastName())
                                                  .setEmail(userDTO.getEmail())
                                                  .setImageUrl(userDTO.getImageUrl())
                                                  .setLangKey(userDTO.getLangKey())
                                                  .setPassword(encryptedPassword)
                                                  .setKeyDateResetDefault()
                                                  .setActivated(TRUE);
        return setUserAuthorities.andThen(setUserState)
                                 .andThen(userRepository::save)
                                 .andThen(this::clearUserCaches)
                                 .andThen(logCreatedUser())
                                 .apply(new User(), userDTO);
    }
    
    private UnaryOperator<User> logCreatedUser() {
        return usr -> {
            log.debug("Created Information for User: {}", usr);
            return usr;
        };
    }
    
    private User setUserAuthorities(final User user, final UserDTO userDTO) {
        Consumer<Set<String>> setUserAuthorities = strAuths -> {
            Set<Authority> authoritiesFounded = findAuthoritiesByIds(strAuths);
            user.setAuthorities(authoritiesFounded);
        };
        Optional.ofNullable(userDTO.getAuthorities())
                .ifPresent(setUserAuthorities);
        return user;
    }
    
    private Set<Authority> findAuthoritiesByIds(Set<String> strAuths) {
        return strAuths.stream()
                       .map(authorityRepository::findById)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(toSet());
    }
    
    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param email     email id of user
     * @param langKey   language key
     * @param imageUrl  image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        Consumer<User> setUser = user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
        };
        SecurityUtils.getCurrentUserLogin()
                     .flatMap(userRepository::findOneByLogin)
                     .ifPresent(setUser);
    }
    
    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        UnaryOperator<User> setUser = user -> {
            clearUserCaches(user);
            Set<String> strAuths = userDTO.getAuthorities();
            Set<Authority> authoritiesFounded = findAuthoritiesByIds(strAuths);
            user.setLogin(userDTO.getLogin())
                .setFirstName(userDTO.getFirstName())
                .setLastName(userDTO.getLastName())
                .setEmail(userDTO.getEmail())
                .setImageUrl(userDTO.getImageUrl())
                .setActivated(userDTO.isActivated())
                .setLangKey(userDTO.getLangKey())
                .removeAuthorities()
                .addAuthorities(authoritiesFounded);
            clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
            return user;
        };
        return userRepository.findById(userDTO.getId())
                             .map(setUser)
                             .map(UserDTO::new);
    }
    
    public void deleteUser(String login) {
        Consumer<User> delete = user -> {
            userRepository.delete(user);
            clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        };
        userRepository.findOneByLogin(login)
                      .ifPresent(delete);
    }
    
    public void changePassword(String currentClearTextPassword, String newPassword) {
        Consumer<User> setUserPasswd = user -> {
            String currentEncryptedPassword = user.getPassword();
            boolean invalidePassword =
                !passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword);
            
            if (invalidePassword) {
                throw new InvalidPasswordException();
            }
            
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            clearUserCaches(user);
            log.debug("Changed password for User: {}", user);
        };
        SecurityUtils.getCurrentUserLogin()
                     .flatMap(userRepository::findOneByLogin)
                     .ifPresent(setUserPasswd);
    }
    
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }
    
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }
    
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin()
                            .flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }
    
    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        Instant dayBeforeYesterday = Instant.now().minus(3, ChronoUnit.DAYS);
        List<User> users = userRepository
                               .findAllByActivatedIsFalseAndCreatedDateBefore(dayBeforeYesterday);
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
            clearUserCaches(user);
        }
    }
    
    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll()
                                  .stream()
                                  .map(Authority::getName)
                                  .collect(toList());
    }
    
    private User clearUserCaches(final User user) {
        String login = user.getLogin();
        String email = user.getEmail();
        Cache loginCache = cacheManager.getCache(USERS_BY_LOGIN_CACHE);
        Cache emailCache = cacheManager.getCache(USERS_BY_EMAIL_CACHE);
        Objects.requireNonNull(loginCache).evict(login);
        Objects.requireNonNull(emailCache).evict(email);
        return user;
    }
}
