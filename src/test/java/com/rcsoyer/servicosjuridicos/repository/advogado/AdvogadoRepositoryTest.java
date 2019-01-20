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
public class AdvogadoRepositoryTest {
    
    @Autowired
    private AdvogadoRepository repository;
    
    @BeforeEach
    public void setUp() {
        var advogado = new Advogado().setId(1L)
            .setCpf("98734512365")
            .setNome("Bruce Banner")
            .setRamal(3242);
        repository.save(advogado);
    }
    
    @Test
    public void query() {
        var advogado = new Advogado();
        var pageable = PageRequest.of(1, 2);
        var result = repository.query(advogado, pageable);
        assertEquals(result.getContent().get(0), advogado);
    }
}
