package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.web.rest.AdvogadoResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestInstance(Lifecycle.PER_CLASS)
class AdvogadoResourceIntTest extends ApiConfigTest {
    
    private static final String URL_ADVOGADO_API = "/api/advogado";
    
    private MockMvc mockMvc;
    
    @Autowired
    private AdvogadoService advogadoService;
    
    @BeforeAll
    void setUp() {
        final var advogadoResource = new AdvogadoResource(advogadoService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(advogadoResource)
                                      .setCustomArgumentResolvers(pageableArgumentResolver)
                                      .setControllerAdvice(exceptionTranslator)
                                      .setMessageConverters(jacksonMessageConverter)
                                      .build();
    }
    
    @Test
    void create_ok() throws Exception {
        final var dto = advogadoDto1();
        
        mockMvc.perform(post(URL_ADVOGADO_API)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").isNotEmpty())
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.nome").value(dto.getNome()))
               .andExpect(jsonPath("$.cpf").value(dto.getCpf()))
               .andExpect(jsonPath("$.ramal").value(dto.getRamal()));
    }
    
    @Test
    void create_whithId() throws Exception {
        final var dto = advogadoDto1().setId(666L);
        
        mockMvc.perform(post(URL_ADVOGADO_API)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void update_ok() throws Exception {
        final var dto = advogadoService.save(advogadoDto1());
        
        mockMvc.perform(put(URL_ADVOGADO_API)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(dto.getId()))
               .andExpect(jsonPath("$.nome").value(dto.getNome()))
               .andExpect(jsonPath("$.cpf").value(dto.getCpf()))
               .andExpect(jsonPath("$.ramal").value(dto.getRamal()));
    }
    
    @Test
    void update_whithoutId() throws Exception {
        mockMvc.perform(put(URL_ADVOGADO_API)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(advogadoDto1())))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void getAdvogados_matchingParams() throws Exception {
        final var advogado1 = advogadoService.save(advogadoDto1());
        advogadoService.save(advogadoDto2());
        
        mockMvc.perform(
            get(URL_ADVOGADO_API)
                .param("cpf", advogado1.getCpf())
                .param("nome", advogado1.getNome())
                .param("ramal", advogado1.getRamal().toString()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$.[0].id", equalTo(advogado1.getId().intValue())))
               .andExpect(jsonPath("$.[0].cpf", equalTo(advogado1.getCpf())))
               .andExpect(jsonPath("$.[0].nome", equalTo(advogado1.getNome())))
               .andExpect(jsonPath("$.[0].ramal", equalTo(advogado1.getRamal())));
    }
    
    @Test
    void getAssuntos_noParams() throws Exception {
        var advogado1 = advogadoService.save(advogadoDto1());
        var advogado2 = advogadoService.save(advogadoDto2());
        
        mockMvc.perform(
            get(URL_ADVOGADO_API))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$.[0].id", equalTo(advogado1.getId().intValue())))
               .andExpect(jsonPath("$.[0].cpf", equalTo(advogado1.getCpf())))
               .andExpect(jsonPath("$.[0].nome", equalTo(advogado1.getNome())))
               .andExpect(jsonPath("$.[0].ramal", equalTo(advogado1.getRamal())))
               .andExpect(jsonPath("$.[1].id", equalTo(advogado2.getId().intValue())))
               .andExpect(jsonPath("$.[1].cpf", equalTo(advogado2.getCpf())))
               .andExpect(jsonPath("$.[1].nome", equalTo(advogado2.getNome())))
               .andExpect(jsonPath("$.[1].ramal", equalTo(advogado2.getRamal())));
    }
    
    @Test
    void getAdvogado_found() throws Exception {
        var advogado = advogadoService.save(advogadoDto1());
        
        mockMvc.perform(
            get(URL_ADVOGADO_API + "/{id}", advogado.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(advogado.getId().intValue()))
               .andExpect(jsonPath("$.cpf").value(advogado.getCpf()))
               .andExpect(jsonPath("$.nome").value(advogado.getNome()))
               .andExpect(jsonPath("$.ramal").value(advogado.getRamal()));
    }
    
    @Test
    void getAdvogado_notFound() throws Exception {
        final var unknownAdvogado = advogadoDto1().setId(777L);
        
        mockMvc.perform(
            get(URL_ADVOGADO_API + "/{id}", unknownAdvogado.getId()))
               .andExpect(status().isNotFound());
    }
    
    @Test
    void deleteAdvogado() throws Exception {
        var advogado = advogadoService.save(advogadoDto1());
        
        mockMvc.perform(
            delete(URL_ADVOGADO_API + "/{id}", advogado.getId()))
               .andExpect(status().isOk());
    }
    
    private AdvogadoDTO advogadoDto1() {
        return new AdvogadoDTO().setCpf("62135757900")
                                .setNome("Dare the devil")
                                .setRamal(5325);
    }
    
    private AdvogadoDTO advogadoDto2() {
        return new AdvogadoDTO().setCpf("12582132578")
                                .setNome("Kevin Lomax")
                                .setRamal(666);
    }
}
