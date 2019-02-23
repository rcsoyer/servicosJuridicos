package com.rcsoyer.servicosjuridicos.domain;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordenacaoJuridicaTest {
    
    private CoordenacaoJuridica model;
    
    @BeforeEach
    void setUp() {
        this.model = new CoordenacaoJuridica().setId(1L);
    }
    
    @Test
    void setCentena() {
        model.setCentena(" ");
        assertNull(model.getCentena());
    }
    
    @Test
    void setSigla_trimToNull() {
        model.setSigla(" ");
        assertNull(model.getSigla());
    }
    
    @Test
    void setSigla_toTrimUpperCase() {
        model.setSigla("abc ");
        assertEquals(model.getSigla(), "ABC");
    }
    
    @Test
    void setNome() {
        model.setNome(" ");
        assertNull(model.getNome());
    }
    
    @Test
    void addDgAdvogado() {
        var advogadoDgCoordenacao = addGetAdvogadoDgCoordenacao();
        var actualAdvogado = model.getDgAdvogados().stream().findFirst().get();
        assertEquals(advogadoDgCoordenacao, actualAdvogado);
        assertEquals(advogadoDgCoordenacao.getCoordenacao(), model);
    }
    
    @Test
    void removeDgAdvogado() {
        var advogadoDgCoordenacao = addGetAdvogadoDgCoordenacao();
        model.removeDgAdvogado(advogadoDgCoordenacao);
        assertThat(model.getDgAdvogados(), not(hasItem(advogadoDgCoordenacao)));
        assertNull(advogadoDgCoordenacao.getCoordenacao());
    }
    
    @Test
    void addAssunto() {
        var assunto = addGetAssunto();
        var actualAssunto = model.getAssuntos().stream().findFirst().get();
        assertEquals(assunto, actualAssunto);
    }
    
    @Test
    void removeAssunto() {
        var assunto = addGetAssunto();
        model.removeAssunto(assunto);
        assertThat(model.getAssuntos(), not(hasItem(assunto)));
    }
    
    private AdvogadoDgCoordenacao addGetAdvogadoDgCoordenacao() {
        var advogadoDgCoordenacao = new AdvogadoDgCoordenacao().setId(1L);
        model.addDgAdvogado(advogadoDgCoordenacao);
        return advogadoDgCoordenacao;
    }
    
    private Assunto addGetAssunto() {
        var assunto = new Assunto().setId(1L);
        model.addAssunto(assunto);
        return assunto;
    }
}
