package com.rcsoyer.servicosjuridicos.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
class AssuntoRepositoryTest {
    
    @Autowired
    private AssuntoRepository repository;
    
    @Test
    void findByAssunto() {
        repository.save(new Assunto().setDescricao("one thousand generations living here now")
                                     .setAtivo(Boolean.TRUE)
                                     .setPeso(1));
        
        Assunto queryParams = new Assunto().setDescricao("one thousand generations living here now")
                                           .setAtivo(Boolean.TRUE)
                                           .setPeso(1);
        Page<Assunto> pageResult = repository.findByAssunto(queryParams, PageRequest.of(0, 10));
        Assunto onlyResult = pageResult.getContent().get(0);
        
        assertEquals(queryParams.getDescricao(), onlyResult.getDescricao());
        assertEquals(queryParams.getAtivo(), onlyResult.getAtivo());
        assertEquals(queryParams.getPeso(), onlyResult.getPeso());
    }
}
