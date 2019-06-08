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
    private FeriasLicencaRepository repository;
    
    @Autowired
    private AdvogadoRepository advogadoRepository;
    
    private LocalDate dtFim = LocalDate.ofEpochDay(32342342342L);
    
    @Test
    void query() {
        final var savedFeriasLicenca = savedFeriasLicenca();
        final var ferias = new FeriasLicenca().setTipo(FERIAS).setDtFim(dtFim);
        
        Page<FeriasLicenca> feriasLicencasBD = repository.query(ferias, PageRequest.of(0, 10));
        
        assertTrue(feriasLicencasBD.hasContent());
        
        FeriasLicenca feriasLicencaFounded = feriasLicencasBD.getContent().get(0);
        
        var actualAdvogado = feriasLicencaFounded.getAdvogado();
        
        assertEquals(actualAdvogado, savedFeriasLicenca.getAdvogado());
        assertEquals(ferias.getDtFim(), feriasLicencaFounded.getDtFim());
        assertEquals(ferias.getTipo(), feriasLicencaFounded.getTipo());
    }
    
    private FeriasLicenca savedFeriasLicenca() {
        final var advogado = advogadoRepository.save(new Advogado()
                                                         .setNome("Matt Murdock")
                                                         .setCpf("64831122203"));
        this.dtFim = LocalDate.ofEpochDay(32342342342L);
        return repository.save(new FeriasLicenca()
                                   .setTipo(FERIAS)
                                   .setDtInicio(LocalDate.now())
                                   .setDtFim(dtFim)
                                   .setAdvogado(advogado));
    }
}
