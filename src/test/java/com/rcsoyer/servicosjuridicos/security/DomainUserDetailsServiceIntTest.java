package com.rcsoyer.servicosjuridicos.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.repository.UserRepository;
import java.util.Locale;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for userDetailsService.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
@Transactional
public class DomainUserDetailsServiceIntTest {
    
    private static final String USER_ONE_LOGIN = "test-user-one";
    private static final String USER_ONE_EMAIL = "test-user-one@localhost";
    private static final String USER_TWO_LOGIN = "test-user-two";
    private static final String USER_TWO_EMAIL = "test-user-two@localhost";
    private static final String USER_THREE_LOGIN = "test-user-three";
    private static final String USER_THREE_EMAIL = "test-user-three@localhost";
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Before
    public void init() {
        User userOne = new User();
        userOne.setLogin(USER_ONE_LOGIN);
        userOne.setPassword(RandomStringUtils.random(60));
        userOne.setActivated(true);
        userOne.setEmail(USER_ONE_EMAIL);
        userOne.setFirstName("userOne");
        userOne.setLastName("doe");
        userOne.setLangKey("en");
        userRepository.save(userOne);
        
        User userTwo = new User();
        userTwo.setLogin(USER_TWO_LOGIN);
        userTwo.setPassword(RandomStringUtils.random(60));
        userTwo.setActivated(true);
        userTwo.setEmail(USER_TWO_EMAIL);
        userTwo.setFirstName("userTwo");
        userTwo.setLastName("doe");
        userTwo.setLangKey("en");
        userRepository.save(userTwo);
        
        User userThree = new User();
        userThree.setLogin(USER_THREE_LOGIN);
        userThree.setPassword(RandomStringUtils.random(60));
        userThree.setActivated(false);
        userThree.setEmail(USER_THREE_EMAIL);
        userThree.setFirstName("userThree");
        userThree.setLastName("doe");
        userThree.setLangKey("en");
        userRepository.save(userThree);
    }
    
    @Test
    @Transactional
    public void assertThatUserCanBeFoundByLogin() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_ONE_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }
    
    @Test
    @Transactional
    public void assertThatUserCanBeFoundByLoginIgnoreCase() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_ONE_LOGIN.toUpperCase(Locale.ENGLISH));
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }
    
    @Test
    @Transactional
    public void assertThatUserCanBeFoundByEmail() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_TWO_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_LOGIN);
    }
    
    @Test(expected = UsernameNotFoundException.class)
    @Transactional
    public void assertThatUserCanNotBeFoundByEmailIgnoreCase() {
        userDetailsService.loadUserByUsername(USER_TWO_EMAIL.toUpperCase(Locale.ENGLISH));
    }
    
    @Test
    @Transactional
    public void assertThatEmailIsPrioritizedOverLogin() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_ONE_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }
    
    @Test(expected = UserNotActivatedException.class)
    @Transactional
    public void assertThatUserNotActivatedExceptionIsThrownForNotActivatedUsers() {
        userDetailsService.loadUserByUsername(USER_THREE_LOGIN);
    }
    
}
