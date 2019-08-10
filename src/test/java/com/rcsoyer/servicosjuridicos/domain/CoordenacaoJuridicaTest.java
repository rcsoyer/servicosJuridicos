package com.rcsoyer.servicosjuridicos.domain;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordenacaoJuridicaTest {

    private CoordenacaoJuridica coordenacao;

    @BeforeEach
    void setUp() {
        this.coordenacao = new CoordenacaoJuridica().setId(1L);
    }

    @Test
    void setCentena() {
        coordenacao.setCentena(" ");

        assertNull(coordenacao.getCentena());
    }

    @Test
    void setSigla_trimToNull() {
        coordenacao.setSigla(" ");

        assertNull(coordenacao.getSigla());
    }

    @Test
    void setSigla_toTrimUpperCase() {
        coordenacao.setSigla("abc ");

        assertEquals(coordenacao.getSigla(), "ABC");
    }

    @Test
    void setNome() {
        coordenacao.setNome(" ");

        assertNull(coordenacao.getNome());
    }

    @Test
    void addDgAdvogado() {
        var advogadoDgCoordenacao = addGetAdvogadoDgCoordenacao();
        var actualAdvogado = coordenacao.getDgAdvogados().stream().findFirst().get();

        assertEquals(advogadoDgCoordenacao, actualAdvogado);
        assertEquals(advogadoDgCoordenacao.getCoordenacao(), coordenacao);
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
        var actualAssunto = coordenacao.getAssuntos().stream().findFirst().get();

        assertEquals(assunto, actualAssunto);
    }

    @Test
    void removeAssunto() {
        var assunto = addGetAssunto();
        coordenacao.removeAssunto(assunto);

        assertThat(coordenacao.getAssuntos(), not(hasItem(assunto)));
    }

    @Test
    void getAssuntos() {
        final var assuntos = new ArrayList<Assunto>(3);
        final var assunto1 = new Assunto().setId(1L);
        assuntos.add(assunto1);
        assuntos.add(new Assunto().setId(2L));
        assuntos.add(new Assunto().setId(3L));

        assuntos.forEach(coordenacao::addAssunto);

        assertThrows(UnsupportedOperationException.class, () -> coordenacao.getAssuntos().remove(assunto1));

        assuntos.remove(assunto1);
        assertFalse(assuntos.containsAll(coordenacao.getAssuntos()));
    }

    @Test
    void getDgAdvogados() {
        final var advogadosDgCoordenacoes = new ArrayList<AdvogadoDgCoordenacao>(3);
        final var advogadoDgCoordenacao1 = new AdvogadoDgCoordenacao().setId(1L);
        advogadosDgCoordenacoes.add(advogadoDgCoordenacao1);
        advogadosDgCoordenacoes.add(new AdvogadoDgCoordenacao().setId(2L));
        advogadosDgCoordenacoes.add(new AdvogadoDgCoordenacao().setId(3L));

        advogadosDgCoordenacoes.forEach(coordenacao::addDgAdvogado);

        assertThrows(UnsupportedOperationException.class,
                     () -> coordenacao.getDgAdvogados().remove(advogadoDgCoordenacao1));

        advogadosDgCoordenacoes.remove(advogadoDgCoordenacao1);
        assertFalse(advogadosDgCoordenacoes.containsAll(coordenacao.getDgAdvogados()));
    }

    @Test
    void testCentenaPattern() {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var coordenacao = new CoordenacaoJuridica().setCentena("00")
                                                   .setSigla("42")
                                                   .setNome("Doom")
                                                   .addAssunto(new Assunto().setId(1L));

        Set<ConstraintViolation<CoordenacaoJuridica>> violations = validator.validate(coordenacao);

        assertThat(violations, hasSize(1));
    }

    @Test
    void addAssuntos() {
        final var assunto1 = new Assunto().setId(1L).setDescricao("Assunto 1");
        final var assunto2 = new Assunto().setId(2L).setDescricao("Assunto 2");
        Set<Assunto> assuntos = Set.of(assunto1, assunto2);

        coordenacao.addAssuntos(assuntos);

        assertTrue(coordenacao.getAssuntos().containsAll(assuntos));
    }

    private AdvogadoDgCoordenacao addGetAdvogadoDgCoordenacao() {
        var advogadoDgCoordenacao = new AdvogadoDgCoordenacao().setId(1L);
        coordenacao.addDgAdvogado(advogadoDgCoordenacao);
        return advogadoDgCoordenacao;
    }

    private Assunto addGetAssunto() {
        var assunto = new Assunto().setId(1L);
        coordenacao.addAssunto(assunto);
        return assunto;
    }
}
