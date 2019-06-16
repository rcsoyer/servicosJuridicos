package com.rcsoyer.servicosjuridicos.domain;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModalidadeTest {
    
    private Modalidade modalidade;
    
    @BeforeEach
    void setUp() {
        this.modalidade = new Modalidade();
    }
    
    @Test
    void setDescricao() {
        modalidade.setDescricao("       ");
        
        assertNull(modalidade.getDescricao());
    }
    
}
