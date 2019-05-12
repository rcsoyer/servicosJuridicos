package com.rcsoyer.servicosjuridicos.integration;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.web.rest.AssuntoResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestInstance(Lifecycle.PER_CLASS)
class AssuntoResourceIntTest extends AppConfigTest {
    
    @Autowired
    private AssuntoService assuntoService;
    
    private MockMvc mockMvc;
    
    @BeforeAll
    void setUp() {
        var assuntoResource = new AssuntoResource(assuntoService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(assuntoResource)
                                      .setCustomArgumentResolvers(pageableArgumentResolver)
                                      .setControllerAdvice(exceptionTranslator)
                                      .setMessageConverters(jacksonMessageConverter)
                                      .build();
    }
    
    @Test
    void createAssunto() throws Exception {
        var savedAssunto = assuntoService.save(new AssuntoDTO().setPeso(1)
                                                               .setAtivo(Boolean.TRUE)
                                                               .setDescricao("assunto 1"));
        
        mockMvc.perform(get("/api/assunto").param("peso", savedAssunto.getPeso().toString()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.[0].id", equalTo(savedAssunto.getId().intValue())));
        
    }
    
}
