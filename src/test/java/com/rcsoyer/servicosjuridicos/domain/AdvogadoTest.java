package com.rcsoyer.servicosjuridicos.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.domain.feriaslicenca.FeriasLicenca;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdvogadoTest {

    private Advogado advogado;

    @BeforeEach
    void setUp() {
        this.advogado = new Advogado().setId(1L);
    }

    @Test
    void setFeriasLicencas_notTheSameList() {
        var feriasLicenca1 = new FeriasLicenca().setId(1L);
        var feriasLicencas = new HashSet<FeriasLicenca>(3);
        feriasLicencas.add(feriasLicenca1);
        feriasLicencas.add(new FeriasLicenca().setId(2L));
        feriasLicencas.add(new FeriasLicenca().setId(3L));

        feriasLicencas.forEach(advogado::addFeriasLicenca);

        feriasLicencas.remove(feriasLicenca1);

        assertFalse(feriasLicencas.containsAll(advogado.getFeriasLicencas()));
    }

    @Test
    void setDgCoordenacoes_notTheSameList() {
        var dgCoordenacao1 = new AdvogadoDgCoordenacao().setId(1L);
        var dgCoordenacoes = new HashSet<AdvogadoDgCoordenacao>(3);
        dgCoordenacoes.add(dgCoordenacao1);
        dgCoordenacoes.add(new AdvogadoDgCoordenacao().setId(2L));
        dgCoordenacoes.add(new AdvogadoDgCoordenacao().setId(3L));

        dgCoordenacoes.forEach(advogado::addDgCoordenacao);

        dgCoordenacoes.remove(dgCoordenacao1);

        assertFalse(dgCoordenacoes.containsAll(advogado.getDgCoordenacoes()));
    }

    @Test
    void setNome() {
        advogado.setNome("          ");

        assertNull(advogado.getNome());
    }

    @Test
    void setCpf() {
        advogado.setCpf("     ");

        assertNull(advogado.getCpf());
    }

    @Test
    void getFeriasLicencas() {
        assertThrows(UnsupportedOperationException.class,
                     () -> advogado.getFeriasLicencas().remove(new FeriasLicenca().setId(1L)));
    }

    @Test
    void getDgCoordenacoes() {
        assertThrows(UnsupportedOperationException.class,
                     () -> advogado.getDgCoordenacoes().remove(new AdvogadoDgCoordenacao().setId(1L)));
    }

    @Test
    void addFeriasLicenca() {
        var feriasLicenca = new FeriasLicenca().setId(1L);
        advogado.addFeriasLicenca(feriasLicenca);

        assertTrue(advogado.getFeriasLicencas().contains(feriasLicenca));
        assertEquals(advogado, feriasLicenca.getAdvogado());
    }

    @Test
    void removeFeriasLicenca() {
        var feriasLicenca = new FeriasLicenca().setId(1L);
        advogado.addFeriasLicenca(feriasLicenca);
        advogado.removeFeriasLicenca(feriasLicenca);

        assertFalse(advogado.getFeriasLicencas().contains(feriasLicenca));
        assertNull(feriasLicenca.getAdvogado());
    }

    @Test
    void addDgCoordenacao() {
        var dgCoordenacao = new AdvogadoDgCoordenacao().setId(1L);
        advogado.addDgCoordenacao(dgCoordenacao);

        assertTrue(advogado.getDgCoordenacoes().contains(dgCoordenacao));
        assertEquals(advogado, dgCoordenacao.getAdvogado());
    }

    @Test
    void removeDgCoordenacao() {
        var dgCoordenacao = new AdvogadoDgCoordenacao().setId(1L)
                                                       .setAdvogado(new Advogado().setId(1L))
                                                       .setCoordenacao(new CoordenacaoJuridica().setId(1L));
        advogado.addDgCoordenacao(dgCoordenacao);
        advogado.removeDgCoordenacao(dgCoordenacao);

        assertFalse(advogado.getDgCoordenacoes().contains(dgCoordenacao));
        assertNull(dgCoordenacao.getAdvogado());
    }
}
