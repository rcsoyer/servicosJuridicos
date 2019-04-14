package com.rcsoyer.servicosjuridicos.repository.advogado;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = ServicosJuridicosApp.class)
@ExtendWith(SpringExtension.class)
class AdvogadoRepositoryTest {
    
    @Autowired
    private AdvogadoRepository repository;
    
    private Advogado advogado;
    
    @BeforeEach
    void setUp() {
        advogado = new Advogado().setId(1L)
                                 .setCpf("98734512365")
                                 .setNome("Bruce Banner")
                                 .setRamal(3242);
        advogado = repository.save(advogado);
    }
    
    @Test
    void query() {
        var pageable = PageRequest.of(0, 10);
        var result = repository.query(advogado, pageable);
        assertEquals(result.getContent().get(0), advogado);
    }
}
