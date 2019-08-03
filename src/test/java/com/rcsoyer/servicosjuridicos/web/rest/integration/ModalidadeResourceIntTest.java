package com.rcsoyer.servicosjuridicos.web.rest.integration;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.service.ModalidadeService;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class ModalidadeResourceIntTest extends ApiConfigTest {
    
    private static final String URL_MODALIDADE_API = "/api/modalidade";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ModalidadeService service;
    
    private ModalidadeDTO persistedModalidade;
    
    @BeforeEach
    void setUp() {
        this.persistedModalidade = service.save(newModalidade());
    }
    
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
    void createModalidade_invalidParams() throws Exception {
        final var modalidade = new ModalidadeDTO().setId(666L);
        
        mockMvc.perform(post(URL_MODALIDADE_API)
                            .with(user(TEST_USER_ID))
                            .with(csrf())
                            .contentType(APPLICATION_JSON_UTF8)
                            .content(convertObjectToJsonBytes(modalidade)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.title").value("Method argument not valid"))
               .andExpect(jsonPath("$.fieldErrors[0].field").value("id"))
               .andExpect(jsonPath("$.fieldErrors[0].message").value("Null"))
               .andExpect(jsonPath("$.fieldErrors[1].field").value("descricao"))
               .andExpect(jsonPath("$.fieldErrors[1].message").value("NotBlank"));
    }
    
    @Test
    void updateModalidade() {
    }
    
    @Test
    void getModalidades() {
    }
    
    @Test
    void getModalidade() {
    }
    
    @Test
    void deleteModalidade() {
    }
    
    private ModalidadeDTO newModalidade() {
        return new ModalidadeDTO()
                   .setDescricao("modalidade 1");
    }
    
}
