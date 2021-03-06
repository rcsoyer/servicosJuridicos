package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.security.AuthoritiesConstants.USER;
import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.repository.UserRepository;
import com.rcsoyer.servicosjuridicos.security.AuthoritiesConstants;
import com.rcsoyer.servicosjuridicos.service.MailService;
import com.rcsoyer.servicosjuridicos.service.UserService;
import com.rcsoyer.servicosjuridicos.web.rest.TestUtil;
import com.rcsoyer.servicosjuridicos.web.rest.UserResource;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;
import com.rcsoyer.servicosjuridicos.web.rest.vm.ManagedUserVM;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
public class UserResourceIntTest {
    
    private static final String DEFAULT_LOGIN = "johndoe";
    private static final String UPDATED_LOGIN = "jhipster";
    
    private static final String DEFAULT_PASSWORD = "passjohndoe";
    private static final String UPDATED_PASSWORD = "passjhipster";
    
    private static final String DEFAULT_EMAIL = "johndoe@localhost";
    private static final String UPDATED_EMAIL = "jhipster@localhost";
    
    private static final String DEFAULT_FIRSTNAME = "john";
    private static final String UPDATED_FIRSTNAME = "jhipsterFirstName";
    
    private static final String DEFAULT_LASTNAME = "doe";
    private static final String UPDATED_LASTNAME = "jhipsterLastName";
    
    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";
    private static final String UPDATED_IMAGEURL = "http://placehold.it/40x40";
    
    private static final String DEFAULT_LANGKEY = "en";
    private static final String UPDATED_LANGKEY = "fr";
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
    @Autowired
    private ExceptionTranslator exceptionTranslator;
    
    @Autowired
    private EntityManager em;
    
    @Autowired
    private CacheManager cacheManager;
    
    private MockMvc restUserMockMvc;
    
    private User user;
    
    @Before
    public void setup() {
        cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).clear();
        cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).clear();
        UserResource userResource = new UserResource(userService, userRepository, mailService);
        
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
                                              .setCustomArgumentResolvers(pageableArgumentResolver)
                                              .setControllerAdvice(exceptionTranslator)
                                              .setMessageConverters(jacksonMessageConverter)
                                              .build();
    }
    
    /**
     * Create a User.
     *
     * This is a static method, as tests for other entities might also need it, if they test an entity which has a
     * required relationship to the User entity.
     */
    public static User newUser() {
        return new User()
                   .setLogin(DEFAULT_LOGIN + RandomStringUtils.randomAlphabetic(5))
                   .setPassword(RandomStringUtils.random(60))
                   .setActivated(true)
                   .setEmail(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL)
                   .setFirstName(DEFAULT_FIRSTNAME)
                   .setLastName(DEFAULT_LASTNAME)
                   .setImageUrl(DEFAULT_IMAGEURL)
                   .setLangKey(DEFAULT_LANGKEY);
    }
    
    @Before
    public void initTest() {
        user = newUser()
                   .setLogin(DEFAULT_LOGIN)
                   .setEmail(DEFAULT_EMAIL);
    }
    
    @Test
    @Transactional
    public void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();
        
        // Create the User
        final ManagedUserVM managedUserVM = new ManagedUserVM()
                                                .setLogin(DEFAULT_LOGIN)
                                                .setPassword(DEFAULT_PASSWORD)
                                                .setFirstName(DEFAULT_FIRSTNAME)
                                                .setLastName(DEFAULT_LASTNAME)
                                                .setEmail(DEFAULT_EMAIL)
                                                .setActivated(true)
                                                .setImageUrl(DEFAULT_IMAGEURL)
                                                .setLangKey(DEFAULT_LANGKEY)
                                                .setAuthorities(singleton(USER));
        
        restUserMockMvc.perform(post("/api/users")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(convertObjectToJsonBytes(managedUserVM)))
                       .andExpect(status().isCreated());
        
        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUser.getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testUser.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUser.getImageUrl()).isEqualTo(DEFAULT_IMAGEURL);
        assertThat(testUser.getLangKey()).isEqualTo(DEFAULT_LANGKEY);
    }
    
    @Test
    @Transactional
    public void createUserWithExistingLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeCreate = userRepository.findAll().size();
        
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin(DEFAULT_LOGIN);// this login should already be used
        managedUserVM.setPassword(DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DEFAULT_LASTNAME);
        managedUserVM.setEmail("anothermail@localhost");
        managedUserVM.setActivated(true);
        managedUserVM.setImageUrl(DEFAULT_IMAGEURL);
        managedUserVM.setLangKey(DEFAULT_LANGKEY);
        managedUserVM.setAuthorities(singleton(USER));
        
        // Create the User
        restUserMockMvc.perform(post("/api/users")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(convertObjectToJsonBytes(managedUserVM)))
                       .andExpect(status().isBadRequest());
        
        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }
    
    @Test
    @Transactional
    public void createUserWithExistingEmail() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeCreate = userRepository.findAll().size();
        
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin("anotherlogin");
        managedUserVM.setPassword(DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DEFAULT_LASTNAME);
        managedUserVM.setEmail(DEFAULT_EMAIL);// this email should already be used
        managedUserVM.setActivated(true);
        managedUserVM.setImageUrl(DEFAULT_IMAGEURL);
        managedUserVM.setLangKey(DEFAULT_LANGKEY);
        managedUserVM.setAuthorities(singleton(USER));
        
        // Create the User
        restUserMockMvc.perform(post("/api/users")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(convertObjectToJsonBytes(managedUserVM)))
                       .andExpect(status().isBadRequest());
        
        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }
    
    @Test
    @Transactional
    public void getUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        
        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)
                               .get(user.getLogin()))
            .isNull();
        
        // Get the user
        restUserMockMvc.perform(get("/api/users/{login}", user.getLogin()))
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                       .andExpect(jsonPath("$.login").value(user.getLogin()))
                       .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRSTNAME))
                       .andExpect(jsonPath("$.lastName").value(DEFAULT_LASTNAME))
                       .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
                       .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGEURL))
                       .andExpect(jsonPath("$.langKey").value(DEFAULT_LANGKEY));
        
        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNotNull();
    }
    
    @Test
    @Transactional
    public void getNonExistingUser() throws Exception {
        restUserMockMvc.perform(get("/api/users/unknown"))
                       .andExpect(status().isNotFound());
    }
    
    @Test
    @Transactional
    public void updateUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();
        
        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();
        
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin(updatedUser.getLogin());
        managedUserVM.setPassword(UPDATED_PASSWORD);
        managedUserVM.setFirstName(UPDATED_FIRSTNAME);
        managedUserVM.setLastName(UPDATED_LASTNAME);
        managedUserVM.setEmail(UPDATED_EMAIL);
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(UPDATED_IMAGEURL);
        managedUserVM.setLangKey(UPDATED_LANGKEY);
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(singleton(USER));
        
        restUserMockMvc.perform(put("/api/users")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(convertObjectToJsonBytes(managedUserVM)))
                       .andExpect(status().isOk());
        
        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testUser.getLastName()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUser.getImageUrl()).isEqualTo(UPDATED_IMAGEURL);
        assertThat(testUser.getLangKey()).isEqualTo(UPDATED_LANGKEY);
    }
    
    @Test
    @Transactional
    public void updateUserLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();
        
        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();
        
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin(UPDATED_LOGIN);
        managedUserVM.setPassword(UPDATED_PASSWORD);
        managedUserVM.setFirstName(UPDATED_FIRSTNAME);
        managedUserVM.setLastName(UPDATED_LASTNAME);
        managedUserVM.setEmail(UPDATED_EMAIL);
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(UPDATED_IMAGEURL);
        managedUserVM.setLangKey(UPDATED_LANGKEY);
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(singleton(USER));
        
        restUserMockMvc.perform(put("/api/users")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(convertObjectToJsonBytes(managedUserVM)))
                       .andExpect(status().isOk());
        
        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testUser.getLastName()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUser.getImageUrl()).isEqualTo(UPDATED_IMAGEURL);
        assertThat(testUser.getLangKey()).isEqualTo(UPDATED_LANGKEY);
    }
    
    @Test
    @Transactional
    public void updateUserExistingEmail() throws Exception {
        // Initialize the database with 2 users
        userRepository.saveAndFlush(user);
        
        User anotherUser = new User();
        anotherUser.setLogin("jhipster");
        anotherUser.setPassword(RandomStringUtils.random(60));
        anotherUser.setActivated(true);
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setFirstName("java");
        anotherUser.setLastName("hipster");
        anotherUser.setImageUrl("");
        anotherUser.setLangKey("en");
        userRepository.saveAndFlush(anotherUser);
        
        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();
        
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin(updatedUser.getLogin());
        managedUserVM.setPassword(updatedUser.getPassword());
        managedUserVM.setFirstName(updatedUser.getFirstName());
        managedUserVM.setLastName(updatedUser.getLastName());
        managedUserVM.setEmail("jhipster@localhost");// this email should already be used by anotherUser
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(updatedUser.getImageUrl());
        managedUserVM.setLangKey(updatedUser.getLangKey());
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(singleton(USER));
        
        restUserMockMvc.perform(put("/api/users")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(convertObjectToJsonBytes(managedUserVM)))
                       .andExpect(status().isBadRequest());
    }
    
    @Test
    @Transactional
    public void updateUserExistingLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        
        User anotherUser = new User();
        anotherUser.setLogin("jhipster");
        anotherUser.setPassword(RandomStringUtils.random(60));
        anotherUser.setActivated(true);
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setFirstName("java");
        anotherUser.setLastName("hipster");
        anotherUser.setImageUrl("");
        anotherUser.setLangKey("en");
        userRepository.saveAndFlush(anotherUser);
        
        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();
        
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin("jhipster");// this login should already be used by anotherUser
        managedUserVM.setPassword(updatedUser.getPassword());
        managedUserVM.setFirstName(updatedUser.getFirstName());
        managedUserVM.setLastName(updatedUser.getLastName());
        managedUserVM.setEmail(updatedUser.getEmail());
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(updatedUser.getImageUrl());
        managedUserVM.setLangKey(updatedUser.getLangKey());
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(singleton(USER));
        
        restUserMockMvc.perform(put("/api/users")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(convertObjectToJsonBytes(managedUserVM)))
                       .andExpect(status().isBadRequest());
    }
    
    @Test
    @Transactional
    public void deleteUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeDelete = userRepository.findAll().size();
        
        // Delete the user
        restUserMockMvc.perform(delete("/api/users/{login}", user.getLogin())
                                    .accept(TestUtil.APPLICATION_JSON_UTF8))
                       .andExpect(status().isOk());
        
        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNull();
        
        // Validate the database is empty
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }
    
    @Test
    @Transactional
    public void getAllAuthorities() throws Exception {
        restUserMockMvc.perform(get("/api/users/authorities")
                                    .accept(TestUtil.APPLICATION_JSON_UTF8)
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8))
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                       .andExpect(jsonPath("$").isArray())
                       .andExpect(jsonPath("$").value(hasItems(USER, AuthoritiesConstants.ADMIN)));
    }
    
}
