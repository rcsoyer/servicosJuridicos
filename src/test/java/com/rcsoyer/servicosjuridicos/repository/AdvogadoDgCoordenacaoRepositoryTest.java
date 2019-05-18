package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao.INCLUSIVE;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.repository.coordenacao.CoordenacaoJuridicaRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@TestInstance(Lifecycle.PER_CLASS)
class AdvogadoDgCoordenacaoRepositoryTest extends RepositoryConfigTest {
    
    private Assunto assunto;
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
        this.assunto = new Assunto().setPeso(2).setDescricao("magic and dark entries").setAtivo(true);
        this.advogado = new Advogado().setCpf("74726571583").setNome("Matt Murdock");
        this.coordenacaoJuridica = new CoordenacaoJuridica().setNome("Seven thrones of hell")
                                                            .setSigla("FLOYD")
                                                            .setCentena("424")
                                                            .setAssuntos(singleton(assunto));
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
        Page<AdvogadoDgCoordenacao> result = dgCoordenacaoRepository.query(dgCoordenacao, PageRequest.of(0, 10));
        List<AdvogadoDgCoordenacao> content = result.getContent();
        
        assertTrue(result.hasContent());
        assertEquals(dgCoordenacao, content.get(0));
    }
    
    @Test
    void countByDgDuplaEquals() {
        int numberOfAdvogadosWithDgDupla = dgCoordenacaoRepository.countByDgDuplaEquals(dgCoordenacao.getDgDupla());
        
        assertEquals(1, numberOfAdvogadosWithDgDupla);
    }
}
