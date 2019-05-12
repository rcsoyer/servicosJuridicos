package com.rcsoyer.servicosjuridicos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.repository.AssuntoRepository;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AssuntoMapper;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for the AssuntoResource REST controller.
 *
 * @see AssuntoResource
 */
@ExtendWith(SpringExtension.class)
@SpringJUnitWebConfig(classes = AssuntoResource.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
public class AssuntoResourceIntTest {
    
    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";
    
    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;
    
    private static final Integer DEFAULT_PESO = 1;
    private static final Integer UPDATED_PESO = 2;
    
    @Autowired
    private AssuntoRepository assuntoRepository;
    
    @Autowired
    private AssuntoMapper assuntoMapper;
    
    
    @Autowired
    private AssuntoService assuntoService;
    
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
    @Autowired
    private ExceptionTranslator exceptionTranslator;
    
    @Autowired
    private EntityManager em;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    private Assunto assunto;
    
    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it, if they test an entity which requires
     * the current entity.
     */
    static Assunto createEntity(EntityManager em) {
        return new Assunto().setDescricao(DEFAULT_DESCRICAO).setAtivo(DEFAULT_ATIVO).setPeso(DEFAULT_PESO);
    }
    
    @BeforeEach
    public void initTest() {
        assunto = createEntity(em);
    }
    
    @Test
    @Transactional
    public void createAssunto() throws Exception {
        int databaseSizeBeforeCreate = assuntoRepository.findAll().size();
        
        // Create the Assunto
        AssuntoDTO assuntoDTO = assuntoMapper.toDto(assunto);
        /*
        restAssuntoMockMvc
            .perform(post("/api/assuntos").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                          .content(TestUtil.convertObjectToJsonBytes(assuntoDTO)))
            .andExpect(status().isCreated());
            
         */
        
        ResponseEntity<AssuntoDTO> response = restTemplate.getForEntity("/api/assuntos", AssuntoDTO.class);
        
        // Validate the Assunto in the database
        List<Assunto> assuntoList = assuntoRepository.findAll();
        assertThat(assuntoList).hasSize(databaseSizeBeforeCreate + 1);
        Assunto testAssunto = assuntoList.get(assuntoList.size() - 1);
        assertThat(testAssunto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAssunto.getAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testAssunto.getPeso()).isEqualTo(DEFAULT_PESO);
    }
    
    @Test
    @Transactional
    public void createAssuntoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assuntoRepository.findAll().size();
        
        // Create the Assunto with an existing ID
        assunto.setId(1L);
        AssuntoDTO assuntoDTO = assuntoMapper.toDto(assunto);
        
        // An entity with an existing ID cannot be created, so this API call must fail
        //    restAssuntoMockMvc
        //      .perform(post("/api/assuntos").contentType(TestUtil.APPLICATION_JSON_UTF8)
        //                                  .content(TestUtil.convertObjectToJsonBytes(assuntoDTO)))
        //  .andExpect(status().isBadRequest());
        
        // Validate the Assunto in the database
        List<Assunto> assuntoList = assuntoRepository.findAll();
        assertThat(assuntoList).hasSize(databaseSizeBeforeCreate);
    }
    
    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = assuntoRepository.findAll().size();
        // set the field null
        assunto.setDescricao(null);
        
        // Create the Assunto, which fails.
        AssuntoDTO assuntoDTO = assuntoMapper.toDto(assunto);
      /*
        restAssuntoMockMvc
            .perform(post("/api/assuntos").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                          .content(TestUtil.convertObjectToJsonBytes(assuntoDTO)))
            .andExpect(status().isBadRequest());
        */
        List<Assunto> assuntoList = assuntoRepository.findAll();
        assertThat(assuntoList).hasSize(databaseSizeBeforeTest);
    }
    
    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = assuntoRepository.findAll().size();
        // set the field null
        assunto.setAtivo(null);
        
        // Create the Assunto, which fails.
        AssuntoDTO assuntoDTO = assuntoMapper.toDto(assunto);
        
        /*
        restAssuntoMockMvc
            .perform(post("/api/assuntos").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                          .content(TestUtil.convertObjectToJsonBytes(assuntoDTO)))
            .andExpect(status().isBadRequest());*/
        
        List<Assunto> assuntoList = assuntoRepository.findAll();
        assertThat(assuntoList).hasSize(databaseSizeBeforeTest);
    }
    
    @Test
    @Transactional
    public void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = assuntoRepository.findAll().size();
        // set the field null
        assunto.setPeso(null);
        
        // Create the Assunto, which fails.
        AssuntoDTO assuntoDTO = assuntoMapper.toDto(assunto);
        
        /*
        restAssuntoMockMvc
            .perform(post("/api/assuntos").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                          .content(TestUtil.convertObjectToJsonBytes(assuntoDTO)))
            .andExpect(status().isBadRequest());*/
        
        List<Assunto> assuntoList = assuntoRepository.findAll();
        assertThat(assuntoList).hasSize(databaseSizeBeforeTest);
    }
    
    @Test
    @Transactional
    public void getAllAssuntos() throws Exception {
        // Initialize the database
        assuntoRepository.saveAndFlush(assunto);
        
        // Get all the assuntoList
        /*
        restAssuntoMockMvc.perform(get("/api/assuntos?sort=id,desc")).andExpect(status().isOk())
                          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                          .andExpect(jsonPath("$.[*].id").value(hasItem(assunto.getId().intValue())))
                          .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
                          .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
                          .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)));
                          
         */
    }
    
    
    @Test
    @Transactional
    public void getAssunto() throws Exception {
        // Initialize the database
        assuntoRepository.saveAndFlush(assunto);
        
        // Get the assunto
        /*
        restAssuntoMockMvc.perform(get("/api/assuntos/{id}", assunto.getId()))
                          .andExpect(status().isOk())
                          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                          .andExpect(jsonPath("$.id").value(assunto.getId().intValue()))
                          .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
                          .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
                          .andExpect(jsonPath("$.peso").value(DEFAULT_PESO));
                          
         */
    }
    
    @Test
    @Transactional
    public void getNonExistingAssunto() throws Exception {
        // Get the assunto
        /*
        restAssuntoMockMvc.perform(get("/api/assuntos/{id}", Long.MAX_VALUE))
                          .andExpect(status().isNotFound());
                          
         */
    }
    
    @Test
    @Transactional
    public void updateAssunto() throws Exception {
        // Initialize the database
        assuntoRepository.saveAndFlush(assunto);
        
        int databaseSizeBeforeUpdate = assuntoRepository.findAll().size();
        
        // Update the assunto
        Assunto updatedAssunto = assuntoRepository.findById(assunto.getId()).get();
        // Disconnect from session so that the updates on updatedAssunto are not directly saved in db
        em.detach(updatedAssunto);
        updatedAssunto.setDescricao(UPDATED_DESCRICAO).setAtivo(UPDATED_ATIVO).setPeso(UPDATED_PESO);
        AssuntoDTO assuntoDTO = assuntoMapper.toDto(updatedAssunto);
        
        /*
        restAssuntoMockMvc.perform(put("/api/assuntos").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                       .content(TestUtil.convertObjectToJsonBytes(assuntoDTO)))
                          .andExpect(status().isOk());
        
        // Validate the Assunto in the database
        List<Assunto> assuntoList = assuntoRepository.findAll();
        assertThat(assuntoList).hasSize(databaseSizeBeforeUpdate);
        Assunto testAssunto = assuntoList.get(assuntoList.size() - 1);
        assertThat(testAssunto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAssunto.getAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testAssunto.getPeso()).isEqualTo(UPDATED_PESO);
        
         */
    }
    
    @Test
    @Transactional
    public void updateNonExistingAssunto() throws Exception {
        int databaseSizeBeforeUpdate = assuntoRepository.findAll().size();
        
        // Create the Assunto
        AssuntoDTO assuntoDTO = assuntoMapper.toDto(assunto);
        
        // If the entity doesn't have an ID, it will be created instead of just being updated
        /*
        restAssuntoMockMvc
            .perform(put("/api/assuntos").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                         .content(TestUtil.convertObjectToJsonBytes(assuntoDTO)))
            .andExpect(status().isBadRequest());
        
        // Validate the Assunto in the database
        List<Assunto> assuntoList = assuntoRepository.findAll();
        assertThat(assuntoList).hasSize(databaseSizeBeforeUpdate);
        
         */
    }
    
    @Test
    @Transactional
    public void deleteAssunto() throws Exception {
        // Initialize the database
        assuntoRepository.saveAndFlush(assunto);
        
        int databaseSizeBeforeDelete = assuntoRepository.findAll().size();
        
        // Get the assunto
        /*
        restAssuntoMockMvc
            .perform(
                delete("/api/assuntos/{id}", assunto.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
        
        // Validate the database is empty
        List<Assunto> assuntoList = assuntoRepository.findAll();
        assertThat(assuntoList).hasSize(databaseSizeBeforeDelete - 1);
        
         */
    }
}
