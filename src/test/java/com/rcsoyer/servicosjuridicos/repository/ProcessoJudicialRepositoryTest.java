package com.rcsoyer.servicosjuridicos.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.repository.processo.ProcessoJudicialRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
                                                       .setPeso(2));
        
        final var modalidade = this.modalidadeRepository.save(new Modalidade()
                                                                  .setDescricao("modalidade de curitiba"));
        
        final var advogado = advogadoRepository.save(new Advogado()
                                                         .setCpf("47633228342")
                                                         .setNome("Matt Murdock")
                                                         .setRamal(666));
        
        final var now = LocalDateTime.now();
        this.persistedProcesso = processoRepository.save(new ProcessoJudicial()
                                                             .setAdvogado(advogado)
                                                             .setAssunto(assunto)
                                                             .setModalidade(modalidade)
                                                             .setDtInicio(now)
                                                             .setDtAtribuicao(now)
                                                             .setDtConclusao(LocalDateTime.MIN)
                                                             .setNumero("12345678901234567890")
                                                             .setPrazoFinal(LocalDateTime.MAX));
    }
    
    @Test
    void query() {
        final List<ProcessoJudicial> result = processoRepository.query(persistedProcesso, PageRequest.of(0, 20))
                                                                .getContent();
        
        assertThat(result)
            .hasSize(1);
        assertThat(result.get(0))
            .isEqualTo(persistedProcesso);
    }
}
