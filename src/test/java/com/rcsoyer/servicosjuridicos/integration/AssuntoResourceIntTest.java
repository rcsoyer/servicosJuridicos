package com.rcsoyer.servicosjuridicos.integration;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.repository.AssuntoRepository;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.web.rest.AssuntoResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AssuntoResourceIntTest extends AppConfigTest {
    
    @Autowired
    private AssuntoRepository assuntoRepository;
    
    @Autowired
    private AssuntoService assuntoService;
    
    private MockMvc mockMvc;
    
    @BeforeEach
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
        var savedAssunto = assuntoRepository.save(new Assunto().setPeso(1)
                                                               .setAtivo(Boolean.TRUE)
                                                               .setDescricao("assunto 1"));
        var dto = new AssuntoDTO().setPeso(1)
                                  .setAtivo(Boolean.TRUE)
                                  .setDescricao("assunto 1");
        
        mockMvc.perform(get("/api/assunto").param("peso", dto.getPeso().toString()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.[0].id", equalTo(savedAssunto.getId().intValue())));
        
    }
    
}
