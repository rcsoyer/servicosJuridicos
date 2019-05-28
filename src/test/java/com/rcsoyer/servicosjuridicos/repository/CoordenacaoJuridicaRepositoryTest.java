package com.rcsoyer.servicosjuridicos.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@TestInstance(Lifecycle.PER_CLASS)
class CoordenacaoJuridicaRepositoryTest extends RepositoryConfigTest {
    
    @Autowired
    private CoordenacaoJuridicaRepository coordenacaoRepository;
    
    @Autowired
    private AssuntoRepository assuntoRepository;
    
    private Assunto assunto;
    
    @BeforeAll
    void setUp() {
        this.assunto = assuntoRepository.save(new Assunto()
                                                  .setPeso(5)
                                                  .setDescricao("We need to talk about this")
                                                  .setAtivo(Boolean.TRUE));
    }
    
    @Test
    void query() {
        final var coordenacao = coordenacaoRepository.save(new CoordenacaoJuridica()
                                                               .setCentena("012")
                                                               .setSigla("JLI")
                                                               .setNome("Justice League International")
                                                               .addAssunto(assunto));
        
        Page<CoordenacaoJuridica> pageResult = coordenacaoRepository.query(coordenacao, PageRequest.of(0, 10));
        
        assertThat(pageResult.getContent(), hasSize(1));
        
        CoordenacaoJuridica foundedCoordenacao = pageResult.getContent().get(0);
        
        assertEquals(coordenacao.getCentena(), foundedCoordenacao.getCentena());
        assertEquals(coordenacao.getSigla(), foundedCoordenacao.getSigla());
        assertEquals(coordenacao.getNome(), foundedCoordenacao.getNome());
        assertEquals(coordenacao.getAssuntos(), foundedCoordenacao.getAssuntos());
    }
    
    @Test
    void testSiglaUnique() {
        coordenacaoRepository.save(new CoordenacaoJuridica()
                                       .setCentena("012")
                                       .setSigla("JLI")
                                       .setNome("Justice League International")
                                       .addAssunto(assunto));
        
        assertThrows(DataIntegrityViolationException.class,
                     () -> coordenacaoRepository.saveAndFlush(new CoordenacaoJuridica()
                                                                  .setCentena("666")
                                                                  .setSigla("JLI")
                                                                  .setNome("No justice on these lands")
                                                                  .addAssunto(assunto)));
        
    }
    
    @Test
    void testNomeUnique() {
        coordenacaoRepository.save(new CoordenacaoJuridica()
                                       .setCentena("012")
                                       .setSigla("JL")
                                       .setNome("a bike")
                                       .addAssunto(assunto));
        
        assertThrows(DataIntegrityViolationException.class,
                     () -> coordenacaoRepository.saveAndFlush(new CoordenacaoJuridica()
                                                                  .setCentena("666")
                                                                  .setSigla("JLI")
                                                                  .setNome("a bike")
                                                                  .addAssunto(assunto)));
        
    }
}
