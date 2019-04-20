package com.rcsoyer.servicosjuridicos.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
class AdvogadoRepositoryTest {
    
    private Advogado advogado;
    
    @Autowired
    private AdvogadoRepository advogadoRepository;
    
    @BeforeEach
    void setUp() {
        this.advogado = new Advogado().setNome("Matt Murdock")
                                      .setCpf("88372354103");
    }
    
    @Test
    void query() {
        advogadoRepository.save(advogado);
        Page<Advogado> advogados = advogadoRepository.query(advogado, PageRequest.of(0, 10));
        Advogado advFromDB = advogados.getContent().get(0);
        assertEquals(advogado, advFromDB);
        assertEquals(advogado.getCpf(), advFromDB.getCpf());
    }
}
