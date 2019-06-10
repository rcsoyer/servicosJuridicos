package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.domain.enumeration.FeriasLicencaTipo.LICENCA;
import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.FeriasLicencaService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class FeriasLicencaResourceTest extends ApiConfigTest {
    
    private static final String URL_API_FERIAS_LICENCAS = "/api/ferias-licencas";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private FeriasLicencaService feriasLicencaService;
    
    @Autowired
    private AdvogadoService advogadoService;
    
    @Test
    void createFeriasLicenca() throws Exception {
        final FeriasLicencaDTO feriasLicenca = newFeriasLicenca();
        
        mockMvc.perform(
            post(URL_API_FERIAS_LICENCAS)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(feriasLicenca)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.dtInicio").value(feriasLicenca.getDtInicio().toString()))
               .andExpect(jsonPath("$.dtFim").value(feriasLicenca.getDtFim().toString()))
               .andExpect(jsonPath("$.tipo").value(feriasLicenca.getTipo().name()))
               .andExpect(jsonPath("$.advogadoId").value(feriasLicenca.getAdvogadoId().intValue()));
    }
    
    @Test
    void updateFeriasLicenca() throws Exception {
        final FeriasLicencaDTO persistedFeriasLicenca = feriasLicencaService.save(newFeriasLicenca());
        
        mockMvc.perform(
            put(URL_API_FERIAS_LICENCAS)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(persistedFeriasLicenca)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(persistedFeriasLicenca.getId()))
               .andExpect(jsonPath("$.dtInicio").value(persistedFeriasLicenca.getDtInicio().toString()))
               .andExpect(jsonPath("$.dtFim").value(persistedFeriasLicenca.getDtFim().toString()))
               .andExpect(jsonPath("$.tipo").value(persistedFeriasLicenca.getTipo().name()))
               .andExpect(jsonPath("$.advogadoId").value(persistedFeriasLicenca.getAdvogadoId().intValue()));
    }
    
    @Test
    void getFeriasLicenca() {
    }
    
    @Test
    void deleteFeriasLicenca() {
    }
    
    @Test
    void getFeriasLicencas() {
    }
    
    private FeriasLicencaDTO newFeriasLicenca() {
        final var advogado = advogadoService.save(new AdvogadoDTO()
                                                      .setCpf("77356862506")
                                                      .setNome("Matt Murdock")
                                                      .setRamal(32423));
        return new FeriasLicencaDTO()
                   .setAdvogadoId(advogado.getId())
                   .setDtInicio(LocalDate.now())
                   .setDtFim(LocalDate.now())
                   .setTipo(LICENCA);
    }
}
