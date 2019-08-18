package com.rcsoyer.servicosjuridicos.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
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
    
}
