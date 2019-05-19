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
        final var advogado = new Advogado().setId(1L)
                                           .setCpf("04287724634");
        final var dgCoordenacao = new AdvogadoDgCoordenacao().setId(1L)
                                                             .setAdvogado(advogado);
        final var coordenacao = new CoordenacaoJuridica().setId(1L)
                                                         .setSigla("FUK")
                                                         .setNome("Jorel da casa El")
                                                         .addDgAdvogado(dgCoordenacao);
        
        coordenacao.removeDgAdvogado(dgCoordenacao);
        
        assertThat(coordenacao.getDgAdvogados(), not(hasItem(dgCoordenacao)));
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
