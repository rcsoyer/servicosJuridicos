package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

class CoordenacaoJuridicaResourceTest extends ApiConfigTest {
    
    private final static String TEST_USER_ID = "user-id-123";
    private static final String URL_COORDENACAO_API = "/api/coordenacao-juridica";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private CoordenacaoJuridicaService coordenacaoService;
    
    @Autowired
    private AssuntoService assuntoService;
    
    @Test
    void createCoordenacaoJuridica() throws Exception {
        final var dto = coordenacaoJuridicaDto();
     /*  final var dto = new CoordenacaoJuridicaDTO()
                            .setSigla("ICLPOY")
                            .setNome("Institute Cannabinistico Logistic OFF ya")
                            .setCentena("421")
                            .setAssuntos(Set.of(new AssuntoDTO().setId(1L),
                                                new AssuntoDTO().setId(2L)));
                                                
      */
        
        mockMvc.perform(post(URL_COORDENACAO_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").isNotEmpty())
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.nome").value(dto.getNome()))
               .andExpect(jsonPath("$.sigla").value(dto.getSigla()))
               .andExpect(jsonPath("$.centena").value(dto.getCentena()))
               .andExpect(jsonPath("$.assuntos", hasSize(2)));
    }
    
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void updateCoordenacaoJuridica() throws Exception {
        final var createdCoordenacao = coordenacaoService.save(coordenacaoJuridicaDto())
                                                         .setCentena("765")
                                                         .setNome("Just another coordenacao");
        mockMvc.perform(put(URL_COORDENACAO_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonBytes(createdCoordenacao)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(createdCoordenacao.getId()))
               .andExpect(jsonPath("$.nome").value(createdCoordenacao.getNome()))
               .andExpect(jsonPath("$.sigla").value(createdCoordenacao.getSigla()))
               .andExpect(jsonPath("$.centena").value(createdCoordenacao.getCentena()))
               .andExpect(jsonPath("$.assuntos", hasSize(2)));
    }
    
    @Test
    void getCoordenacoes() {
    }
    
    @Test
    void getCoordenacaoJuridica() {
    }
    
    @Test
    void deleteCoordenacaoJuridica() {
    }
    
    private CoordenacaoJuridicaDTO coordenacaoJuridicaDto() {
        final var assuntoDTO1 = assuntoService.save(new AssuntoDTO()
                                                        .setAtivo(Boolean.TRUE)
                                                        .setDescricao("assunto 1")
                                                        .setPeso(1));
        final var assuntoDTO2 = assuntoService.save(new AssuntoDTO()
                                                        .setAtivo(Boolean.FALSE)
                                                        .setDescricao("assunto 2")
                                                        .setPeso(2));
        return new CoordenacaoJuridicaDTO()
                   .setSigla("ICLPOY")
                   .setNome("Institute Cannabinistico Logistic OFF ya")
                   .setCentena("421")
                   .setAssuntos(Set.of(assuntoDTO1, assuntoDTO2));
    }
}
