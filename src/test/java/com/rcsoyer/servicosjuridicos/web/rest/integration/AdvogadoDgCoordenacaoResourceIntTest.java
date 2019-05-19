package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao.INCLUSIVE;
import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao;
import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import com.rcsoyer.servicosjuridicos.web.rest.AdvogadoDgCoordenacaoResource;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestInstance(Lifecycle.PER_CLASS)
class AdvogadoDgCoordenacaoResourceIntTest extends ApiConfigTest {
    
    private static final String URL_DGCOORDENACAO_API = "/api/advogado-dg-coordenacao";
    
    private MockMvc mockMvc;
    
    @Autowired
    private AssuntoService assuntoService;
    
    @Autowired
    private CoordenacaoJuridicaService coordenacaoJuridicaService;
    
    @Autowired
    private AdvogadoService advogadoService;
    
    @Autowired
    private AdvogadoDgCoordenacaoService dgCoordenacaoService;
    
    private AdvogadoDTO advogado;
    private CoordenacaoJuridicaDTO coordenacaoJuridica;
    
    @BeforeAll
    void setUp() {
        final var dgCoordenacaoResource = new AdvogadoDgCoordenacaoResource(dgCoordenacaoService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(dgCoordenacaoResource)
                                      .setCustomArgumentResolvers(pageableArgumentResolver)
                                      .setControllerAdvice(exceptionTranslator)
                                      .setMessageConverters(jacksonMessageConverter)
                                      .build();
        
        this.advogado = advogadoService.save(new AdvogadoDTO().setCpf("18645099535")
                                                              .setNome("Nelson Davis")
                                                              .setRamal(345));
        
        final var assunto = assuntoService.save(new AssuntoDTO()
                                                    .setDescricao("the restaurant at the end of the universe")
                                                    .setAtivo(Boolean.TRUE)
                                                    .setPeso(4));
        
        this.coordenacaoJuridica = coordenacaoJuridicaService.save(new CoordenacaoJuridicaDTO()
                                                                       .setAssuntos(Set.of(assunto))
                                                                       .setCentena("532")
                                                                       .setNome("Coordenacao que nao precisamos")
                                                                       .setSigla("FUK"));
        
    }
    
    @Test
    void create_ok() throws Exception {
        final var dto = newDgCoordenacaoDTO();
        
        mockMvc.perform(post(URL_DGCOORDENACAO_API)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").isNotEmpty())
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.advogado").value(dto.getAdvogado()))
               .andExpect(jsonPath("$.coordenacao").value(dto.getCoordenacao()))
               .andExpect(jsonPath("$.dgDupla").value(dto.getDgDupla()))
               .andExpect(jsonPath("$.dgPessoalInicio").value(dto.getDgPessoalInicio()))
               .andExpect(jsonPath("$.rangeDgCoordenacao").value(dto.getRangeDgCoordenacao().name()));
    }
    
    @Test
    void create_whithId() throws Exception {
        final var dto = newDgCoordenacaoDTO().setId(666L);
        
        mockMvc.perform(post(URL_DGCOORDENACAO_API)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void upate_ok() throws Exception {
        var dto = newDgCoordenacaoDTO();
        
        dto = dgCoordenacaoService.save(dto)
                                  .setRangeDgCoordenacao(RangeDgCoordenacao.EXCLUSIVE)
                                  .setDgPessoalInicio(9);
        
        mockMvc.perform(put(URL_DGCOORDENACAO_API)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").isNotEmpty())
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.advogado").value(dto.getAdvogado()))
               .andExpect(jsonPath("$.coordenacao").value(dto.getCoordenacao()))
               .andExpect(jsonPath("$.dgDupla").value(dto.getDgDupla()))
               .andExpect(jsonPath("$.dgPessoalInicio").value(dto.getDgPessoalInicio()))
               .andExpect(jsonPath("$.rangeDgCoordenacao").value(dto.getRangeDgCoordenacao().name()));
    }
    
    private AdvogadoDgCoordenacaoDTO newDgCoordenacaoDTO() {
        return new AdvogadoDgCoordenacaoDTO().setAdvogado(advogado.getId())
                                             .setCoordenacao(coordenacaoJuridica.getId())
                                             .setDgDupla(3)
                                             .setDgPessoalFim(4)
                                             .setDgPessoalInicio(5)
                                             .setRangeDgCoordenacao(INCLUSIVE);
    }
}
