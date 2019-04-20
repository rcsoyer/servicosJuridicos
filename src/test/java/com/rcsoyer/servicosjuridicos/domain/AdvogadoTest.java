package com.rcsoyer.servicosjuridicos.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdvogadoTest {
    
    private Advogado advogado;
    
    @BeforeEach
    void setUp() {
        this.advogado = new Advogado();
    }
    
    @Test
    void setProcessos_noNull() {
        advogado.setProcessos(null);
        assertNotNull(advogado.getProcessos());
        assertTrue(advogado.getProcessos().isEmpty());
    }
    
    @Test
    void setProcessos_notTheSameList() {
        ProcessoJudicial processoJudicial1 = new ProcessoJudicial().setId(1L);
        var processsos = new HashSet<ProcessoJudicial>(3);
        processsos.add(processoJudicial1);
        processsos.add(new ProcessoJudicial().setId(2L));
        processsos.add(new ProcessoJudicial().setId(3L));
        advogado.setProcessos(processsos);
        processsos.remove(processoJudicial1);
        assertFalse(processsos.containsAll(advogado.getProcessos()));
    }
    
    @Test
    void setFeriasLicencas_notNull() {
        advogado.setFeriasLicencas(null);
        assertNotNull(advogado.getFeriasLicencas());
        assertTrue(advogado.getFeriasLicencas().isEmpty());
    }
    
    @Test
    void setFeriasLicencas_notTheSameList() {
        FeriasLicenca feriasLicenca1 = new FeriasLicenca().setId(1L);
        var feriasLicencas = new HashSet<FeriasLicenca>(3);
        feriasLicencas.add(feriasLicenca1);
        feriasLicencas.add(new FeriasLicenca().setId(2L));
        feriasLicencas.add(new FeriasLicenca().setId(3L));
        advogado.setFeriasLicencas(feriasLicencas);
        feriasLicencas.remove(feriasLicenca1);
        assertFalse(feriasLicencas.containsAll(advogado.getFeriasLicencas()));
    }
    
    @Test
    void setDgCoordenacoes_notNull() {
        advogado.setDgCoordenacoes(null);
        assertNotNull(advogado.getDgCoordenacoes());
        assertTrue(advogado.getDgCoordenacoes().isEmpty());
    }
    
    @Test
    void setDgCoordenacoes_notTheSameList() {
        var dgCoordenacao1 = new AdvogadoDgCoordenacao().setId(1L);
        var dgCoordenacoes = new HashSet<AdvogadoDgCoordenacao>(3);
        dgCoordenacoes.add(dgCoordenacao1);
        dgCoordenacoes.add(new AdvogadoDgCoordenacao().setId(2L));
        dgCoordenacoes.add(new AdvogadoDgCoordenacao().setId(3L));
        advogado.setDgCoordenacoes(dgCoordenacoes);
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
    void getProcessos() {
        assertThrows(UnsupportedOperationException.class,
                     () -> advogado.getProcessos().remove(new ProcessoJudicial().setId(1L)));
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
    void addProcesso() {
    }
    
    @Test
    void removeProcesso() {
    }
    
    @Test
    void addFeriasLicenca() {
    }
    
    @Test
    void removeFeriasLicenca() {
    }
    
    @Test
    void addDgCoordenacao() {
    }
    
    @Test
    void removeDgCoordenacao() {
    }
}
