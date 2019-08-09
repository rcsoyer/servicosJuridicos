package com.rcsoyer.servicosjuridicos.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.repository.processo.ProcessoJudicialRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class ProcessoJudicialRepositoryTest extends RepositoryConfigTest {
    
    @Autowired
    private ProcessoJudicialRepository processoRepository;
    
    @Autowired
    private AdvogadoRepository advogadoRepository;
    
    @Autowired
    private ModalidadeRepository modalidadeRepository;
    
    @Autowired
    private AssuntoRepository assuntoRepository;
    
    private ProcessoJudicial persistedProcesso;
    
    @BeforeEach
    void setUp() {
        final var assunto = assuntoRepository.save(new Assunto()
                                                       .setAtivo(Boolean.TRUE)
                                                       .setDescricao("assunto de urgencia")
                                                       .setPeso(7));
        
        final var modalidade = this.modalidadeRepository.save(new Modalidade()
                                                                  .setDescricao("modalidade de curitiba"));
        
        final var advogado = advogadoRepository.save(new Advogado()
                                                         .setCpf("47633228342")
                                                         .setNome("Matt Murdock")
                                                         .setRamal(666));
        
        this.persistedProcesso = processoRepository.save(new ProcessoJudicial()
                                                             .setAdvogado(advogado)
                                                             .setAssunto(assunto)
                                                             .setModalidade(modalidade)
                                                             .setDtConclusao(LocalDateTime.now())
                                                             .setNumero("12345678901234567890")
                                                             .setPrazoFinal(LocalDateTime.MAX));
    }
    
    @Test
    void query() {
        final Page<ProcessoJudicial> result = processoRepository.query(persistedProcesso, PageRequest.of(0, 20));
        
        assertEquals(persistedProcesso, result.getContent().get(0));
    }
}
