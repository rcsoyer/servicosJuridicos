package com.rcsoyer.servicosjuridicos.integration;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.repository.AssuntoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
class AssuntoResourceIntTest {
    
    @Autowired
    private AssuntoRepository assuntoRepository;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void createAssunto() {
        assuntoRepository.save(new Assunto().setPeso(1).setAtivo(Boolean.TRUE).setDescricao("assunto 1"));
    }
    
}
