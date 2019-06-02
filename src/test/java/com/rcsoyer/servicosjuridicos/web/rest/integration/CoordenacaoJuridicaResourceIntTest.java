package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoCreateUpdateDto;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class CoordenacaoJuridicaResourceIntTest extends ApiConfigTest {
    
    private static final String URL_COORDENACAO_API = "/api/coordenacao-juridica";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private CoordenacaoJuridicaService coordenacaoService;
    
    @Autowired
    private AssuntoService assuntoService;
    
    private CoordenacaoCreateUpdateDto dto;
    
    @BeforeEach
    void setUp() {
        this.dto = newCoordenacaoJuridicaDto();
    }
    
    @Test
    void createCoordenacaoJuridica_ok() throws Exception {
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
    void createCoordenacaoJuridica_badRequestHasIdOnCreation() throws Exception {
        mockMvc.perform(post(URL_COORDENACAO_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonBytes(dto.setId(666L))))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("message").value("error.idexists"));
    }
    
    @Test
    void createCoordenacaoJuridica_missingMandatoryFields() throws Exception {
        mockMvc.perform(post(URL_COORDENACAO_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonBytes(new CoordenacaoCreateUpdateDto())))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("message").value("error.validation"));
    }
    
    @Test
    void updateCoordenacaoJuridica_ok() throws Exception {
        final var createdCoordenacao = coordenacaoService.save(dto)
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
    void updateCoordenacaoJuridica_hasNoIdOnUpdate() throws Exception {
        mockMvc.perform(put(URL_COORDENACAO_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("message").value("error.idnull"));
    }
    
    @Test
    void getCoordenacoes() throws Exception {
        final CoordenacaoCreateUpdateDto coordenacao1 = coordenacaoService.save(dto);
        
        // has the same Assuntos but does not match in the other filters
        coordenacaoService.save(new CoordenacaoCreateUpdateDto()
                                    .setNome("Second genesis")
                                    .setSigla("SCO")
                                    .setCentena("770")
                                    .setAssuntos(coordenacao1.getAssuntos()));
        
        final List<Long> assuntosIds = coordenacao1.getAssuntos()
                                                   .stream()
                                                   .map(AssuntoDTO::getId)
                                                   .collect(toList());
        
        mockMvc.perform(
            get(URL_COORDENACAO_API)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .param("nome", coordenacao1.getNome())
                .param("sigla", coordenacao1.getSigla())
                .param("centena", coordenacao1.getCentena())
                .param("assuntos", assuntosIds.get(0).toString())
                .param("assuntos", assuntosIds.get(1).toString())
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$.[0].id", equalTo(coordenacao1.getId().intValue())))
               .andExpect(jsonPath("$.[0].nome", equalTo(coordenacao1.getNome())))
               .andExpect(jsonPath("$.[0].sigla", equalTo(coordenacao1.getSigla())))
               .andExpect(jsonPath("$.[0].centena", equalTo(coordenacao1.getCentena())))
               .andExpect(jsonPath("$.[0].assuntos", hasSize(2)))
               .andExpect(jsonPath("$.[0].assuntos.[0].id").isNumber())
               .andExpect(jsonPath("$.[0].assuntos.[0].descricao").isNotEmpty())
               .andExpect(jsonPath("$.[0].assuntos.[0].ativo").isBoolean())
               .andExpect(jsonPath("$.[0].assuntos.[0].peso").isNumber())
               .andExpect(jsonPath("$.[0].assuntos.[1].id").isNumber())
               .andExpect(jsonPath("$.[0].assuntos.[1].descricao").isNotEmpty())
               .andExpect(jsonPath("$.[0].assuntos.[1].ativo").isBoolean())
               .andExpect(jsonPath("$.[0].assuntos.[1].peso").isNumber());
    }
    
    @Test
    void getCoordenacaoJuridica_ok() throws Exception {
        final CoordenacaoCreateUpdateDto coordenacao = coordenacaoService.save(dto);
        
        mockMvc.perform(
            get(URL_COORDENACAO_API + "/{id}", coordenacao.getId())
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", equalTo(coordenacao.getId().intValue())))
               .andExpect(jsonPath("$.nome", equalTo(coordenacao.getNome())))
               .andExpect(jsonPath("$.sigla", equalTo(coordenacao.getSigla())))
               .andExpect(jsonPath("$.centena", equalTo(coordenacao.getCentena())))
               .andExpect(jsonPath("$.assuntos", hasSize(2)))
               .andExpect(jsonPath("$.assuntos.[0].id").isNumber())
               .andExpect(jsonPath("$.assuntos.[0].descricao").isNotEmpty())
               .andExpect(jsonPath("$.assuntos.[0].ativo").isBoolean())
               .andExpect(jsonPath("$.assuntos.[0].peso").isNumber())
               .andExpect(jsonPath("$.assuntos.[1].id").isNumber())
               .andExpect(jsonPath("$.assuntos.[1].descricao").isNotEmpty())
               .andExpect(jsonPath("$.assuntos.[1].ativo").isBoolean())
               .andExpect(jsonPath("$.assuntos.[1].peso").isNumber());
    }
    
    @Test
    void getCoordenacaoJuridica_notFound() throws Exception {
        mockMvc.perform(
            get(URL_COORDENACAO_API + "/{id}", 9999999)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }
    
    @Test
    void getCoordenacaoJuridica_idValueBadRequest() throws Exception {
        mockMvc.perform(
            get(URL_COORDENACAO_API + "/{id}", -1L)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void deleteCoordenacaoJuridica_ok() throws Exception {
        final CoordenacaoCreateUpdateDto coordenacao = coordenacaoService.save(dto);
        
        mockMvc.perform(
            delete(URL_COORDENACAO_API + "/{id}", coordenacao.getId())
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }
    
    @Test
    void deleteCoordenacaoJuridica_isBadRequest() throws Exception {
        mockMvc.perform(
            delete(URL_COORDENACAO_API + "/{id}", -99999)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
    
    private CoordenacaoCreateUpdateDto newCoordenacaoJuridicaDto() {
        final var assuntoDTO1 = assuntoService.save(new AssuntoDTO()
                                                        .setAtivo(Boolean.TRUE)
                                                        .setDescricao("assunto 1")
                                                        .setPeso(1));
        final var assuntoDTO2 = assuntoService.save(new AssuntoDTO()
                                                        .setAtivo(Boolean.FALSE)
                                                        .setDescricao("assunto 2")
                                                        .setPeso(2));
        return new CoordenacaoCreateUpdateDto()
                   .setSigla("ICLPOY")
                   .setNome("Institute Cannabinistico Logistic OFF ya")
                   .setCentena("421")
                   .setAssuntos(Set.of(assuntoDTO1, assuntoDTO2));
    }
}
