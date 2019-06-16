package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.domain.enumeration.FeriasLicencaTipo.LICENCA;
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
    void createFeriasLicenca_ok() throws Exception {
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
    void createFeriasLicenca_badRequestWithoutMandatoryFields() throws Exception {
        final var feriasLicenca = new FeriasLicencaDTO();
        
        mockMvc.perform(
            post(URL_API_FERIAS_LICENCAS)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(feriasLicenca)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void createFeriasLicenca_badRequestHasId() throws Exception {
        final FeriasLicencaDTO feriasLicenca = newFeriasLicenca().setId(1L);
        
        mockMvc.perform(
            post(URL_API_FERIAS_LICENCAS)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(feriasLicenca)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateFeriasLicenca_ok() throws Exception {
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
    void updateFeriasLicenca_badRequestWithoutMandatoryFields() throws Exception {
        final var feriasLicenca = new FeriasLicencaDTO();
        
        mockMvc.perform(
            put(URL_API_FERIAS_LICENCAS)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(feriasLicenca)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateFeriasLicenca_badRequestWithoutId() throws Exception {
        final FeriasLicencaDTO feriasLicenca = newFeriasLicenca();
        
        mockMvc.perform(
            put(URL_API_FERIAS_LICENCAS)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(feriasLicenca)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorKey").value("idnull"))
               .andExpect(jsonPath("$.entityName").value("feriasLicenca"))
               .andExpect(jsonPath("$.title").value("If don't have an ID there's nothing to update"))
               .andExpect(jsonPath("$.message").value("error.idnull"));
    }
    
    @Test
    void getFeriasLicenca_ok() throws Exception {
        final FeriasLicencaDTO feriasLicenca = feriasLicencaService.save(newFeriasLicenca());
        
        mockMvc.perform(
            get(URL_API_FERIAS_LICENCAS + "/{id}", feriasLicenca.getId())
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(feriasLicenca.getId()))
               .andExpect(jsonPath("$.dtInicio").value(feriasLicenca.getDtInicio().toString()))
               .andExpect(jsonPath("$.dtFim").value(feriasLicenca.getDtFim().toString()))
               .andExpect(jsonPath("$.tipo").value(feriasLicenca.getTipo().name()))
               .andExpect(jsonPath("$.advogadoId").value(feriasLicenca.getAdvogadoId()));
    }
    
    @Test
    void getFeriasLicenca_notFound() throws Exception {
        mockMvc.perform(
            get(URL_API_FERIAS_LICENCAS + "/{id}", 99999L)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isNotFound());
    }
    
    @Test
    void getFeriasLicenca_badRequest() throws Exception {
        mockMvc.perform(
            get(URL_API_FERIAS_LICENCAS + "/{id}", -666L)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void deleteFeriasLicenca_ok() throws Exception {
        final FeriasLicencaDTO feriasLicenca = feriasLicencaService.save(newFeriasLicenca());
        
        mockMvc.perform(
            delete(URL_API_FERIAS_LICENCAS + "/{id}", feriasLicenca.getId())
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isOk());
    }
    
    @Test
    void deleteFeriasLicenca_badRequest() throws Exception {
        mockMvc.perform(
            delete(URL_API_FERIAS_LICENCAS + "/{id}", -666L)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void getFeriasLicencas() throws Exception {
        final FeriasLicencaDTO feriasLicenca = feriasLicencaService.save(newFeriasLicenca());
        
        mockMvc.perform(
            get(URL_API_FERIAS_LICENCAS)
                .with(user(TEST_USER_ID))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("dtInicio", feriasLicenca.getDtInicio().toString())
                .param("dtFim", feriasLicenca.getDtFim().toString())
                .param("tipo", feriasLicenca.getTipo().name())
                .param("advogadoId", feriasLicenca.getAdvogadoId().toString()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$.[0].id").value(feriasLicenca.getId()))
               .andExpect(jsonPath("$.[0].dtInicio").value(feriasLicenca.getDtInicio().toString()))
               .andExpect(jsonPath("$.[0].dtFim").value(feriasLicenca.getDtFim().toString()))
               .andExpect(jsonPath("$.[0].tipo").value(feriasLicenca.getTipo().name()))
               .andExpect(jsonPath("$.[0].advogadoId").value(feriasLicenca.getAdvogadoId()));
    }
    
    private FeriasLicencaDTO newFeriasLicenca() {
        final AdvogadoDTO advogado = advogadoService.save(new AdvogadoDTO()
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
