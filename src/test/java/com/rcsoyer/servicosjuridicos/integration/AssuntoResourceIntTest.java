package com.rcsoyer.servicosjuridicos.integration;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestInstance(Lifecycle.PER_CLASS)
class AssuntoResourceIntTest extends ApiConfigTest {
    
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
    void createAssunto_ok() throws Exception {
        var assunto = assuntoDto();
        
        mockMvc.perform(
            post("/api/assunto")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(assunto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.ativo", equalTo(assunto.isAtivo())))
               .andExpect(jsonPath("$.descricao", equalTo(assunto.getDescricao())))
               .andExpect(jsonPath("$.peso", equalTo(assunto.getPeso())));
    }
    
    @Test
    void createAssunto_alreadyHaveID() throws Exception {
        var assunto = assuntoDto().setId(666L);
        
        mockMvc.perform(
            post("/api/assunto")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(assunto)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateAssunto_ok() throws Exception {
        var assunto = assuntoService.save(assuntoDto());
        
        mockMvc.perform(
            put("/api/assunto")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(assunto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", equalTo(assunto.getId().intValue())))
               .andExpect(jsonPath("$.ativo", equalTo(assunto.isAtivo())))
               .andExpect(jsonPath("$.descricao", equalTo(assunto.getDescricao())))
               .andExpect(jsonPath("$.peso", equalTo(assunto.getPeso())));
    }
    
    @Test
    void updateAssunto_dontHaveID() throws Exception {
        var assunto = assuntoDto();
        
        mockMvc.perform(
            put("/api/assunto")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(assunto)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void getAssuntos() throws Exception {
        var assunto = assuntoService.save(assuntoDto());
        
        mockMvc.perform(
            get("/api/assunto"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$.[0].id", equalTo(assunto.getId().intValue())))
               .andExpect(jsonPath("$.[0].ativo", equalTo(assunto.isAtivo())))
               .andExpect(jsonPath("$.[0].descricao", equalTo(assunto.getDescricao())))
               .andExpect(jsonPath("$.[0].peso", equalTo(assunto.getPeso())));
    }
    
    private AssuntoDTO assuntoDto() {
        return new AssuntoDTO().setPeso(1)
                               .setAtivo(Boolean.TRUE)
                               .setDescricao("assuntoDto 1");
    }
    
}
