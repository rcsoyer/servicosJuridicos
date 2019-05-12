package com.rcsoyer.servicosjuridicos.integration;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=validate")
abstract class ApiConfigTest {
    
    @Autowired
    protected ExceptionTranslator exceptionTranslator;
    
    @Autowired
    protected MappingJackson2HttpMessageConverter jacksonMessageConverter;
    
    @Autowired
    protected PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
}
