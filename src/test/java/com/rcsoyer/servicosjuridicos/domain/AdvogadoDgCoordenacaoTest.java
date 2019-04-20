package com.rcsoyer.servicosjuridicos.domain;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdvogadoDgCoordenacaoTest {
    
    private AdvogadoDgCoordenacao dgCoordenacao;
    
    @BeforeEach
    void setUp() {
        this.dgCoordenacao = new AdvogadoDgCoordenacao();
    }
    
    @Test
    void setDgPessoalInicio() {
        dgCoordenacao.setDgPessoalInicio("      ");
        assertNull(dgCoordenacao.getDgPessoalInicio());
    }
    
    @Test
    void setDgPessoalFim() {
        dgCoordenacao.setDgPessoalFim("      ");
        assertNull(dgCoordenacao.getDgPessoalFim());
    }
    
    @Test
    void setDgDupla() {
        dgCoordenacao.setDgDupla("      ");
        assertNull(dgCoordenacao.getDgDupla());
    }
}
