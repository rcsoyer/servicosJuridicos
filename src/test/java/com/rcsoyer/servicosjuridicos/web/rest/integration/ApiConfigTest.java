package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ServicosJuridicosApp.class, webEnvironment = RANDOM_PORT)
abstract class ApiConfigTest {
    
    final static String TEST_USER_ID = "user-id-123";
    
}
