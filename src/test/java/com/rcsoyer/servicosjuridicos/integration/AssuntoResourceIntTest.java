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
        var assunto = assuntoDto1();
        
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
        var assunto = assuntoDto1().setId(666L);
        
        mockMvc.perform(
            post("/api/assunto")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(assunto)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateAssunto_ok() throws Exception {
        var assunto = assuntoService.save(assuntoDto1());
        
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
        var assunto = assuntoDto1();
        
        mockMvc.perform(
            put("/api/assunto")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(assunto)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void getAssuntos_matchingParams() throws Exception {
        var assunto1 = assuntoService.save(assuntoDto1());
        assuntoService.save(assuntoDto2());
        
        mockMvc.perform(
            get("/api/assunto")
                .param("peso", assunto1.getPeso().toString())
                .param("ativo", assunto1.isAtivo().toString())
                .param("descricao", assunto1.getDescricao()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$.[0].id", equalTo(assunto1.getId().intValue())))
               .andExpect(jsonPath("$.[0].ativo", equalTo(assunto1.isAtivo())))
               .andExpect(jsonPath("$.[0].descricao", equalTo(assunto1.getDescricao())))
               .andExpect(jsonPath("$.[0].peso", equalTo(assunto1.getPeso())));
    }
    
    @Test
    void getAssuntos_noParams() throws Exception {
        var assunto1 = assuntoService.save(assuntoDto1());
        var assunto2 = assuntoService.save(assuntoDto2());
        
        mockMvc.perform(
            get("/api/assunto"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$.[0].id", equalTo(assunto1.getId().intValue())))
               .andExpect(jsonPath("$.[0].ativo", equalTo(assunto1.isAtivo())))
               .andExpect(jsonPath("$.[0].descricao", equalTo(assunto1.getDescricao())))
               .andExpect(jsonPath("$.[0].peso", equalTo(assunto1.getPeso())))
               .andExpect(jsonPath("$.[1].id", equalTo(assunto2.getId().intValue())))
               .andExpect(jsonPath("$.[1].ativo", equalTo(assunto2.isAtivo())))
               .andExpect(jsonPath("$.[1].descricao", equalTo(assunto2.getDescricao())))
               .andExpect(jsonPath("$.[1].peso", equalTo(assunto2.getPeso())));
    }
    
    @Test
    void getAssunto_found() throws Exception {
        var assunto = assuntoService.save(assuntoDto1());
        
        mockMvc.perform(
            get("/api/assunto/{id}", assunto.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", equalTo(assunto.getId().intValue())));
    }
    
    @Test
    void getAssunto_notFound() throws Exception {
        mockMvc.perform(
            get("/api/assunto/{id}", 666L))
               .andExpect(status().isNotFound());
    }
    
    private AssuntoDTO assuntoDto1() {
        return new AssuntoDTO().setPeso(1)
                               .setAtivo(Boolean.TRUE)
                               .setDescricao("assuntoDto 1");
    }
    
    private AssuntoDTO assuntoDto2() {
        return new AssuntoDTO().setPeso(2)
                               .setDescricao("assunto 2")
                               .setAtivo(Boolean.FALSE);
    }
    
}
