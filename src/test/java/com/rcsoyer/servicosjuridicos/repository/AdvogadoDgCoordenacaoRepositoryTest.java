package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.RangeDgCoordenacao.INCLUSIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;

@TestInstance(Lifecycle.PER_CLASS)
class AdvogadoDgCoordenacaoRepositoryTest extends RepositoryConfigTest {
    
    private Advogado advogado;
    private AdvogadoDgCoordenacao dgCoordenacao;
    private CoordenacaoJuridica coordenacaoJuridica;
    
    @Autowired
    private AssuntoRepository assuntoRepository;
    
    @Autowired
    private AdvogadoRepository advogadoRepository;
    
    @Autowired
    private AdvogadoDgCoordenacaoRepository dgCoordenacaoRepository;
    
    @Autowired
    private CoordenacaoJuridicaRepository coordenacaoJuridicaRepository;
    
    @BeforeAll
    void setUp() {
        final var assunto = new Assunto().setPeso(2)
                                         .setDescricao("magic and dark entries")
                                         .setAtivo(true);
        this.advogado = new Advogado().setCpf("74726571583")
                                      .setNome("Matt Murdock");
        this.coordenacaoJuridica = new CoordenacaoJuridica().setNome("Seven thrones of hell")
                                                            .setSigla("FLOYD")
                                                            .setCentena("424")
                                                            .addAssunto(assunto);
        this.dgCoordenacao = new AdvogadoDgCoordenacao().setAdvogado(advogado)
                                                        .setCoordenacao(coordenacaoJuridica)
                                                        .setDgPessoalInicio(2)
                                                        .setDgPessoalFim(3)
                                                        .setDgDupla(4)
                                                        .setRangeDgCoordenacao(INCLUSIVE);
        assuntoRepository.save(assunto);
        advogadoRepository.save(advogado);
        coordenacaoJuridicaRepository.save(coordenacaoJuridica);
        dgCoordenacaoRepository.save(dgCoordenacao);
    }
    
    @Test
    void query() {
        final var page = dgCoordenacaoRepository.query(dgCoordenacao, PageRequest.of(0, 10));
        List<AdvogadoDgCoordenacao> content = page.getContent();
        
        assertTrue(page.hasContent());
        assertEquals(dgCoordenacao, content.get(0));
    }
    
    @Test
    void countByDgDuplaEquals() {
        int numberOfAdvogadosWithDgDupla = dgCoordenacaoRepository.countByDgDuplaEquals(dgCoordenacao.getDgDupla());
        
        assertEquals(1, numberOfAdvogadosWithDgDupla);
    }
    
    /**
     * Tests the constraint the defines the in the table advogado_dg_coordenacao can only be one tuple with one given
     * dg_pessoal_inicio, dg_pessoal_fim, coordenacao_id
     */
    @Test
    void validateConstraint_uniqueDgInicioDgFimCoordenacao() {
        var dgCoordenacao2 = new AdvogadoDgCoordenacao().setAdvogado(advogado)
                                                        .setCoordenacao(coordenacaoJuridica)
                                                        .setDgPessoalInicio(2)
                                                        .setDgPessoalFim(3)
                                                        .setDgDupla(4)
                                                        .setRangeDgCoordenacao(INCLUSIVE);
        
        assertThrows(DataIntegrityViolationException.class, () -> dgCoordenacaoRepository.saveAndFlush(dgCoordenacao2));
    }
    
    /**
     * Tests the constraint the defines the in the table advogado_dg_coordenacao can only be one tuple with one given
     * advogado_id, coordenacao_id
     */
    @Test
    void validateConstraint_uniqueAdvogadoCoordenacao() {
        final var dgCoordenacao = new AdvogadoDgCoordenacao().setAdvogado(advogado)
                                                             .setCoordenacao(coordenacaoJuridica)
                                                             .setDgPessoalInicio(8)
                                                             .setDgPessoalFim(9)
                                                             .setDgDupla(4)
                                                             .setRangeDgCoordenacao(INCLUSIVE);
        
        assertThrows(DataIntegrityViolationException.class, () -> dgCoordenacaoRepository.saveAndFlush(dgCoordenacao));
    }
}
