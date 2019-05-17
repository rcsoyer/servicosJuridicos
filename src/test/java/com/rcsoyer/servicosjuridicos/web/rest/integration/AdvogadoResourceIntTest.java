package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    
    @Autowired
    private AdvogadoService advogadoService;
    
    private MockMvc mockMvc;
    
    @BeforeAll
    void setUp() {
        var advogadoResource = new AdvogadoResource(advogadoService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(advogadoResource)
                                      .setCustomArgumentResolvers(pageableArgumentResolver)
                                      .setControllerAdvice(exceptionTranslator)
                                      .setMessageConverters(jacksonMessageConverter)
                                      .build();
    }
    
    @Test
    void create() throws Exception {
        var dto = new AdvogadoDTO().setCpf("62135757900")
                                   .setNome("Dare the devil")
                                   .setRamal(5325);
        mockMvc.perform(post("/api/advogado")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(dto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").isNotEmpty())
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.nome").value(dto.getNome()))
               .andExpect(jsonPath("$.cpf").value(dto.getCpf()))
               .andExpect(jsonPath("$.ramal").value(dto.getRamal()));
    }
}
