package com.rcsoyer.servicosjuridicos.integration;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.web.rest.AssuntoResource;
import com.rcsoyer.servicosjuridicos.web.rest.TestUtil;
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
    
    @Autowired
    private ObjectMapper objectMapper;
    
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
        var assunto = assuntoDto();
        
        mockMvc.perform(
            post("/api/assunto")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(assunto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").isNumber());
    }
    
    @Test
    void getAssuntos() throws Exception {
        var assunto = assuntoDto();
        
        mockMvc.perform(
            get("/api/assunto")
                .param("peso", assunto.getPeso().toString())
                .param("ativo", assunto.isAtivo().toString())
                .param("descricao", assunto.getDescricao()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$.[0].id", equalTo(assunto.getId().intValue())))
               .andExpect(jsonPath("$.[0].ativo", equalTo(assunto.isAtivo())))
               .andExpect(jsonPath("$.[0].descricao", equalTo(assunto.getDescricao())));
    }
    
    private AssuntoDTO assuntoDto() {
        return new AssuntoDTO().setPeso(1)
                               .setAtivo(Boolean.TRUE)
                               .setDescricao("assuntoDto 1");
    }
    
}
