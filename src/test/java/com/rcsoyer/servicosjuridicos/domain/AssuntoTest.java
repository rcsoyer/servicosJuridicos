package com.rcsoyer.servicosjuridicos.domain;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AssuntoTest {
    
    @Test
    void setDescricao() {
        var assunto = new Assunto().setDescricao("       ");
        assertNull(assunto.getDescricao());
    }
}
