package com.rcsoyer.servicosjuridicos.web.rest;

import static java.util.Objects.isNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.config.Constants;
import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.repository.UserRepository;
import com.rcsoyer.servicosjuridicos.security.AuthoritiesConstants;
import com.rcsoyer.servicosjuridicos.service.MailService;
import com.rcsoyer.servicosjuridicos.service.UserService;
import com.rcsoyer.servicosjuridicos.service.dto.UserDTO;
import com.rcsoyer.servicosjuridicos.web.rest.annotation.PreAuthorizeAdmin;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.errors.EmailAlreadyUsedException;
import com.rcsoyer.servicosjuridicos.web.rest.errors.LoginAlreadyUsedException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority, and send
 * everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join which would
 * be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all the time for nothing
 * (for performance reasons). This is the #1 goal: we should not impact our users' application because of this
 * use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests, but then all
 * authorities come from the cache, so in fact it's much better than doing an outer join (which will get lots of data
 * from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserResource {
    
    private static final String URL_LOGIN_REGEX = "/{login:" + Constants.LOGIN_REGEX + "}";
    
    private final UserService userService;
    private final MailService mailService;
    private final UserRepository userRepository;
    
    public UserResource(final UserService userService, final UserRepository userRepository,
                        final MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
        this.userRepository = userRepository;
    }
    
    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an mail with an activation link. The
     * user needs to be activated on creation.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request)
     * if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @Timed
    @PostMapping
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody final UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);
        if (isNull(userDTO.getId())) {
            throw BadRequestAlertException
                      .builder()
                      .defaultMessage("A new user cannot already have an ID")
                      .entityName("userManagement")
                      .errorKey("idexists")
                      .build();
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                                 .headers(HeaderUtil.createAlert("userManagement.created", newUser.getLogin()))
                                 .body(newUser);
        }
    }
    
    /**
     * PUT /users : Updates an existing User.
     *
     * @param request the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @Timed
    @PutMapping
    @PreAuthorizeAdmin
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody final UserDTO request) {
        log.debug("REST request to update User : {}", request);
        Predicate<Optional<User>> checkUserLoginExists =
            existingUser -> existingUser.isPresent() && (!existingUser.get().getId().equals(request.getId()));
        
        Optional<User> userWithSameEmail = userRepository.findOneByEmailIgnoreCase(request.getEmail());
        if (checkUserLoginExists.test(userWithSameEmail)) {
            throw new EmailAlreadyUsedException();
        }
        
        Optional<User> userWithSameLogin = userRepository.findOneByLogin(request.getLogin().toLowerCase());
        if (checkUserLoginExists.test(userWithSameLogin)) {
            throw new LoginAlreadyUsedException();
        }
        
        Optional<UserDTO> updatedUser = userService.updateUser(request);
        return ResponseUtil.wrapOrNotFound(updatedUser,
                                           HeaderUtil.createAlert("userManagement.updated", request.getLogin()));
    }
    
    /**
     * @return a string list of the all of the roles
     */
    @Timed
    @PreAuthorizeAdmin
    @GetMapping("authorities")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }
    
    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping(URL_LOGIN_REGEX)
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(userService.getUserWithAuthoritiesByLogin(login)
                                                      .map(UserDTO::new));
    }
    
    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping(URL_LOGIN_REGEX)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("userManagement.deleted", login)).build();
    }
}
