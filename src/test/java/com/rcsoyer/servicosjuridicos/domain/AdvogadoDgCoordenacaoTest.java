package com.rcsoyer.servicosjuridicos.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class AdvogadoDgCoordenacaoTest {
    
    @Test
    void equals_byIdAdvogadoCoordenacao() {
        final var dgCoordenacao1 = new AdvogadoDgCoordenacao()
                                       .setId(1L)
                                       .setAdvogado(new Advogado().setId(1L))
                                       .setCoordenacao(new CoordenacaoJuridica().setId(1L));
        
        final var dgCoordenacao2 = new AdvogadoDgCoordenacao()
                                       .setId(1L)
                                       .setAdvogado(new Advogado().setId(1L))
                                       .setCoordenacao(new CoordenacaoJuridica().setId(1L));
        
        assertEquals(dgCoordenacao1, dgCoordenacao2);
    }
    
    @Test
    void equals_notEquals_differentAdvogadoSameCoordenacao() {
        final var coordenacao = new CoordenacaoJuridica().setId(1L);
        
        final var dgCoordenacao1 = new AdvogadoDgCoordenacao()
                                       .setAdvogado(new Advogado().setId(1L))
                                       .setCoordenacao(coordenacao);
        
        final var dgCoordenacao2 = new AdvogadoDgCoordenacao()
                                       .setAdvogado(new Advogado().setId(2L))
                                       .setCoordenacao(coordenacao);
        
        assertNotEquals(dgCoordenacao1, dgCoordenacao2);
    }
    
    @Test
    void equals_notEquals_differentCoordenacaoSameAdvogado() {
        final var advogado = new Advogado().setId(1L);
        
        final var dgCoordenacao1 = new AdvogadoDgCoordenacao()
                                       .setAdvogado(advogado)
                                       .setCoordenacao(new CoordenacaoJuridica().setId(1L));
        
        final var dgCoordenacao2 = new AdvogadoDgCoordenacao()
                                       .setAdvogado(advogado)
                                       .setCoordenacao(new CoordenacaoJuridica().setId(2L));
        
        assertNotEquals(dgCoordenacao1, dgCoordenacao2);
    }
    
}
