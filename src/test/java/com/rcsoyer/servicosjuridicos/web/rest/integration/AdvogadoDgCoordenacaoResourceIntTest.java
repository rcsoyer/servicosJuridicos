package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao.INCLUSIVE;
import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.web.rest.AdvogadoDgCoordenacaoResource;
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
    private AdvogadoDgCoordenacaoService dgCoordenacaoService;
    
    @BeforeAll
    void setUp() {
        final var dgCoordenacaoResource = new AdvogadoDgCoordenacaoResource(dgCoordenacaoService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(dgCoordenacaoResource)
                                      .setCustomArgumentResolvers(pageableArgumentResolver)
                                      .setControllerAdvice(exceptionTranslator)
                                      .setMessageConverters(jacksonMessageConverter)
                                      .build();
    }
    
    @Test
    void create_ok() throws Exception {
        final var dto = dgCoordenacaoDTO()
                            .setAdvogado(1L)
                            .setCoordenacao(2L)
                            .setDgDupla(3)
                            .setDgPessoalFim(4)
                            .setDgPessoalInicio(5)
                            .setRangeDgCoordenacao(INCLUSIVE);
        
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
        final var dto = dgCoordenacaoDTO().setId(666L);
        
        mockMvc.perform(post(URL_DGCOORDENACAO_API)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isBadRequest());
    }
    
    private AdvogadoDgCoordenacaoDTO dgCoordenacaoDTO() {
        return new AdvogadoDgCoordenacaoDTO();
    }
}
