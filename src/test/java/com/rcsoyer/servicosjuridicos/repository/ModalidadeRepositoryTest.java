package com.rcsoyer.servicosjuridicos.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class ModalidadeRepositoryTest extends RepositoryConfigTest {
    
    @Autowired
    private ModalidadeRepository repository;
    
    private Modalidade modalidade;
    
    @BeforeEach
    void setUp() {
        this.modalidade = repository.save(new Modalidade()
                                              .setDescricao("Modalidade de nado"));
    }
    
    @Test
    void query() {
        final Page<Modalidade> result = repository.query(modalidade, PageRequest.of(0, 20));
        
        assertThat(result).isNotEmpty();
        assertThat(result).containsExactly(modalidade);
    }
    
}
