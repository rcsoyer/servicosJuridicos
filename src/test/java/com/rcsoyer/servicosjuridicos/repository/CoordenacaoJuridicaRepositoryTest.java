package com.rcsoyer.servicosjuridicos.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

class CoordenacaoJuridicaRepositoryTest extends RepositoryConfigTest {
    
    @Autowired
    private CoordenacaoJuridicaRepository coordenacaoRepository;
    
    @Test
    void query() {
    }
}
