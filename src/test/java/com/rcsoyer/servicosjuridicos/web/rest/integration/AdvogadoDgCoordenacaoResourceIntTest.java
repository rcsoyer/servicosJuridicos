package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.RangeDgCoordenacao.INCLUSIVE;
import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.RangeDgCoordenacao;
import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoCreateUpdateDto;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class AdvogadoDgCoordenacaoResourceIntTest extends ApiConfigTest {
    
    private static final String URL_DGCOORDENACAO_API = "/api/advogado-dg-coordenacao";
    
    private AdvogadoDTO advogado;
    private CoordenacaoCreateUpdateDto coordenacaoJuridica;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private AssuntoService assuntoService;
    
    @Autowired
    private CoordenacaoJuridicaService coordenacaoJuridicaService;
    
    @Autowired
    private AdvogadoService advogadoService;
    
    @Autowired
    private AdvogadoDgCoordenacaoService dgCoordenacaoService;
    
    
    @BeforeEach
    void setUp() {
        this.advogado = advogadoService.save(new AdvogadoDTO().setCpf("18645099535")
                                                              .setNome("Nelson Davis")
                                                              .setRamal(345));
        
        final var assunto = assuntoService.save(new AssuntoDTO()
                                                    .setDescricao("the restaurant at the end of the universe")
                                                    .setAtivo(Boolean.TRUE)
                                                    .setPeso(4));
        
        this.coordenacaoJuridica = coordenacaoJuridicaService.save(new CoordenacaoCreateUpdateDto()
                                                                       .setAssuntos(Set.of(assunto))
                                                                       .setCentena("532")
                                                                       .setNome("Coordenacao que nao precisamos")
                                                                       .setSigla("FUK"));
    }
    
    @Test
    void create_ok() throws Exception {
        final var dto = newDgCoordenacaoDTO();
        
        mockMvc.perform(post(URL_DGCOORDENACAO_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
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
                            .with(user(TEST_USER_ID))
                            .with(csrf())
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
                            .with(user(TEST_USER_ID))
                            .with(csrf())
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
    
    @Test
    void create_withoutId() throws Exception {
        final var dto = newDgCoordenacaoDTO();
        
        mockMvc.perform(put(URL_DGCOORDENACAO_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void getAdvogadoDgCoordenacao_ok() throws Exception {
        final var dto = dgCoordenacaoService.save(newDgCoordenacaoDTO());
        
        mockMvc.perform(get(URL_DGCOORDENACAO_API + "/{id}", dto.getId())
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(dto.getId()));
    }
    
    @Test
    void getAdvogadoDgCoordenacao_notFound() throws Exception {
        mockMvc.perform(get(URL_DGCOORDENACAO_API + "/{id}", 666L)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isNotFound());
    }
    
    @Test
    void deleteAdvogadoDgCoordenacao() throws Exception {
        final var dto = dgCoordenacaoService.save(newDgCoordenacaoDTO());
        
        mockMvc.perform(delete(URL_DGCOORDENACAO_API + "/{id}", dto.getId())
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isOk());
    }
    
    @Test
    void getByParams() throws Exception {
        final var dto = dgCoordenacaoService.save(newDgCoordenacaoDTO());
        dgCoordenacaoService.save(newDgCoordenacaoDTO2());
        
        mockMvc.perform(get(URL_DGCOORDENACAO_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .param("advogado", dto.getAdvogado().toString())
                            .param("coordenacao", dto.getCoordenacao().toString())
                            .param("dgDupla", dto.getDgDupla().toString())
                            .param("dgPessoalInicio", dto.getDgPessoalInicio().toString())
                            .param("dgPessoalFim", dto.getDgPessoalFim().toString())
                            .param("rangeDgCoordenacao", dto.getRangeDgCoordenacao().name())
                            .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$.[0].id").value(dto.getId()))
               .andExpect(jsonPath("$.[0].advogado").value(dto.getAdvogado()))
               .andExpect(jsonPath("$.[0].coordenacao").value(dto.getCoordenacao()))
               .andExpect(jsonPath("$.[0].dgDupla").value(dto.getDgDupla()))
               .andExpect(jsonPath("$.[0].dgPessoalInicio").value(dto.getDgPessoalInicio()))
               .andExpect(jsonPath("$.[0].rangeDgCoordenacao").value(dto.getRangeDgCoordenacao().name()));
    }
    
    private AdvogadoDgCoordenacaoDTO newDgCoordenacaoDTO() {
        return new AdvogadoDgCoordenacaoDTO().setAdvogado(advogado.getId())
                                             .setCoordenacao(coordenacaoJuridica.getId())
                                             .setDgDupla(3)
                                             .setDgPessoalFim(4)
                                             .setDgPessoalInicio(5)
                                             .setRangeDgCoordenacao(INCLUSIVE);
    }
    
    private AdvogadoDgCoordenacaoDTO newDgCoordenacaoDTO2() {
        final var advogado = advogadoService.save(new AdvogadoDTO().setCpf("73291542100")
                                                                   .setNome("Charles the mingus")
                                                                   .setRamal(666));
        
        final var assunto = assuntoService.save(new AssuntoDTO()
                                                    .setDescricao("good bye and thank you for the fish")
                                                    .setAtivo(Boolean.FALSE)
                                                    .setPeso(5));
        
        final var coordenacaoJuridica = coordenacaoJuridicaService.save(new CoordenacaoCreateUpdateDto()
                                                                            .setAssuntos(Set.of(assunto))
                                                                            .setCentena("008")
                                                                            .setNome("Coordenacao na via láctea")
                                                                            .setSigla("LAC"));
        
        return new AdvogadoDgCoordenacaoDTO().setAdvogado(advogado.getId())
                                             .setCoordenacao(coordenacaoJuridica.getId())
                                             .setDgDupla(7)
                                             .setDgPessoalFim(0)
                                             .setDgPessoalInicio(1)
                                             .setRangeDgCoordenacao(INCLUSIVE);
    }
}
