package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.ModalidadeService;
import com.rcsoyer.servicosjuridicos.web.rest.integration.ApiConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class ModalidadeResourceIntTest extends ApiConfigTest {
    
    private static final String URL_MODALIDADE_API = "/api/modalidade";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ModalidadeService service;
    
    @BeforeEach
    void setUp() {
    }
    
    @Test
    void createModalidade() {
    }
    
    @Test
    void updateModalidade() {
    }
    
    @Test
    void getModalidades() {
    }
    
    @Test
    void getModalidade() {
    }
    
    @Test
    void deleteModalidade() {
    }
}
