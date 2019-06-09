package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.domain.enumeration.FeriasLicencaTipo.FERIAS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class FeriasLicencaRepositoryTest extends RepositoryConfigTest {
    
    @Autowired
    private FeriasLicencaRepository feriasLicencaRepository;
    
    @Autowired
    private AdvogadoRepository advogadoRepository;
    
    @Test
    void query() {
        final var savedFeriasLicenca = savedFeriasLicenca();
        final var searchParams = new FeriasLicenca()
                                     .setTipo(savedFeriasLicenca.getTipo())
                                     .setAdvogado(savedFeriasLicenca.getAdvogado())
                                     .setDtInicio(savedFeriasLicenca.getDtInicio())
                                     .setDtFim(savedFeriasLicenca.getDtFim());
        
        Page<FeriasLicenca> feriasLicencasBD = feriasLicencaRepository.query(searchParams, PageRequest.of(0, 10));
        
        assertTrue(feriasLicencasBD.hasContent());
        
        FeriasLicenca feriasLicencaFounded = feriasLicencasBD.getContent().get(0);
        
        assertEquals(searchParams.getAdvogado(), feriasLicencaFounded.getAdvogado());
        assertEquals(searchParams.getDtFim(), feriasLicencaFounded.getDtFim());
        assertEquals(searchParams.getTipo(), feriasLicencaFounded.getTipo());
        assertEquals(searchParams.getDtInicio(), feriasLicencaFounded.getDtInicio());
    }
    
    private FeriasLicenca savedFeriasLicenca() {
        final var advogado = advogadoRepository.save(new Advogado()
                                                         .setNome("Matt Murdock")
                                                         .setCpf("64831122203"));
        return feriasLicencaRepository.save(new FeriasLicenca()
                                                .setTipo(FERIAS)
                                                .setDtInicio(LocalDate.now())
                                                .setDtFim(LocalDate.ofEpochDay(32342342342L))
                                                .setAdvogado(advogado));
    }
}
