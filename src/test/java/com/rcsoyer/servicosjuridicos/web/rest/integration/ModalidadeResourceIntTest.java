package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.service.ModalidadeService;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

class ModalidadeResourceIntTest extends ApiConfigTest {
    
    private static final String URL_MODALIDADE_API = "/api/modalidade";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ModalidadeService service;
    
    @Test
    void createModalidade_ok() throws Exception {
        final ModalidadeDTO modalidade = newModalidade();
        
        mockMvc.perform(post(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(modalidade)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").isNotEmpty())
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.descricao").value(modalidade.getDescricao()));
    }
    
    @Test
    void createModalidade_invalidWithIdAndNoDescription() throws Exception {
        final var modalidade = new ModalidadeDTO().setId(666L);
        
        mockMvc.perform(post(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(modalidade)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
               .andExpect(jsonPath("$.message").value("error.validation"))
               .andExpect(jsonPath("$.title").value("Method argument not valid"))
               .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("id", "descricao")))
               .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("Null", "NotBlank")));
    }
    
    @Test
    void createModalidade_invalidWithIdAndDescriptionMoreThanMax() throws Exception {
        final var modalidade = new ModalidadeDTO()
                                   .setId(1L)
                                   .setDescricao("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        
        mockMvc.perform(post(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(modalidade)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
               .andExpect(jsonPath("$.message").value("error.validation"))
               .andExpect(jsonPath("$.title").value("Method argument not valid"))
               .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("id", "descricao")))
               .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("Null", "Size")));
    }
    
    @Test
    void updateModalidade_ok() throws Exception {
        final ModalidadeDTO modalidade = newPersistedModalidade();
        
        mockMvc.perform(put(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(modalidade)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(modalidade.getId()))
               .andExpect(jsonPath("$.descricao").value(modalidade.getDescricao()));
    }
    
    @Test
    void updateModalidade_invalidNoIdAndNoDescricacao() throws Exception {
        final var modalidade = new ModalidadeDTO();
        
        mockMvc.perform(put(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(modalidade)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
               .andExpect(jsonPath("$.message").value("error.validation"))
               .andExpect(jsonPath("$.title").value("Method argument not valid"))
               .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("id", "descricao")))
               .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("NotNull", "NotBlank")));
    }
    
    @Test
    void updateModalidade_invalidIdLessThan1AndDescricaoMoreThan60() throws Exception {
        final var modalidade = new ModalidadeDTO()
                                   .setId(-1L)
                                   .setDescricao("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        
        mockMvc.perform(put(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(modalidade)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
               .andExpect(jsonPath("$.message").value("error.validation"))
               .andExpect(jsonPath("$.title").value("Method argument not valid"))
               .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("id", "descricao")))
               .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("Min", "Size")));
    }
    
    @Test
    void getModalidades_matchingParams() throws Exception {
        final ModalidadeDTO modalidade1 = service.save(new ModalidadeDTO().setDescricao("modalidade 1"));
        final ModalidadeDTO modalidade2 = service.save(new ModalidadeDTO().setDescricao("modalidade 2"));
        
        mockMvc.perform(get(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .param("descricao", "modalidade"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$.[*].id", containsInAnyOrder(modalidade1.getId().intValue(),
                                                                  modalidade2.getId().intValue())))
               .andExpect(jsonPath("$.[*].descricao",
                                   containsInAnyOrder(modalidade1.getDescricao(), modalidade2.getDescricao())));
    }
    
    @Test
    void getModalidades_noParams() throws Exception {
        final ModalidadeDTO modalidade1 = service.save(new ModalidadeDTO().setDescricao("algo de novo no horizonte"));
        final ModalidadeDTO modalidade2 = service.save(new ModalidadeDTO().setDescricao("fumar faz mal a saúde"));
        final ModalidadeDTO modalidade3 = service.save(new ModalidadeDTO().setDescricao("ervas naturais elevem"));
        
        mockMvc.perform(get(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(3)))
               .andExpect(jsonPath("$.[*].id", containsInAnyOrder(modalidade1.getId().intValue(),
                                                                  modalidade2.getId().intValue(),
                                                                  modalidade3.getId().intValue())))
               .andExpect(jsonPath("$.[*].descricao",
                                   containsInAnyOrder(modalidade1.getDescricao(),
                                                      modalidade2.getDescricao(),
                                                      modalidade3.getDescricao())));
    }
    
    @Test
    void getModalidade_found() throws Exception {
        final ModalidadeDTO modalidade = newPersistedModalidade();
        
        mockMvc.perform(get(URL_MODALIDADE_API + "/{id}", modalidade.getId())
                            .with(user(TEST_USER_ID))
                            .with(csrf()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(modalidade.getId().intValue()))
               .andExpect(jsonPath("$.descricao").value(modalidade.getDescricao()));
    }
    
    @Test
    void getModalidade_notFound() throws Exception {
        mockMvc.perform(get(URL_MODALIDADE_API + "/{id}", 666L)
                            .with(user(TEST_USER_ID))
                            .with(csrf()))
               .andExpect(status().isNotFound());
    }
    
    @Test
    void getModalidade_invalidId() throws Exception {
        mockMvc.perform(get(URL_MODALIDADE_API + "/{id}", -666L)
                            .with(user(TEST_USER_ID))
                            .with(csrf()))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.violations", hasSize(1)))
               .andExpect(jsonPath("$.message").value("error.validation"))
               .andExpect(jsonPath("$.title").value("Constraint Violation"))
               .andExpect(jsonPath("$.violations[0].field").value("getModalidade.id"))
               .andExpect(jsonPath("$.violations[*].message").value("must be greater than or equal to 1"));
    }
    
    @Test
    void deleteModalidade() {
    }
    
    private ModalidadeDTO newModalidade() {
        return new ModalidadeDTO()
                   .setDescricao("modalidade 1");
    }
    
    private ModalidadeDTO newPersistedModalidade() {
        return service.save(newModalidade());
    }
    
}
