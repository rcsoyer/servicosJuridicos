package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.createFormattingConversionService;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.repository.coordenacao.CoordenacaoJuridicaRepository;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.CoordenacaoJuridicaMapper;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for the CoordenacaoJuridicaResource REST controller.
 *
 * @see CoordenacaoJuridicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
public class CoordenacaoJuridicaResourceIntTest {
    
    private static final String DEFAULT_SIGLA = "AAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBB";
    
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";
    
    private static final String DEFAULT_CENTENA = "AAA";
    private static final String UPDATED_CENTENA = "BBB";
    
    @Autowired
    private CoordenacaoJuridicaRepository coordenacaoJuridicaRepository;
    
    @Autowired
    private CoordenacaoJuridicaMapper coordenacaoJuridicaMapper;
    
    @Mock
    private CoordenacaoJuridicaService coordenacaoJuridicaServiceMock;
    
    @Autowired
    private CoordenacaoJuridicaService coordenacaoJuridicaService;
    
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
    @Autowired
    private ExceptionTranslator exceptionTranslator;
    
    @Autowired
    private EntityManager em;
    
    private MockMvc restCoordenacaoJuridicaMockMvc;
    
    private CoordenacaoJuridica coordenacaoJuridica;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoordenacaoJuridicaResource coordenacaoJuridicaResource =
            new CoordenacaoJuridicaResource(coordenacaoJuridicaService);
        this.restCoordenacaoJuridicaMockMvc =
            MockMvcBuilders.standaloneSetup(coordenacaoJuridicaResource)
                           .setCustomArgumentResolvers(pageableArgumentResolver)
                           .setControllerAdvice(exceptionTranslator)
                           .setConversionService(createFormattingConversionService())
                           .setMessageConverters(jacksonMessageConverter).build();
    }
    
    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it, if they test an entity which requires
     * the current entity.
     */
    public static CoordenacaoJuridica createEntity(EntityManager em) {
        CoordenacaoJuridica coordenacaoJuridica =
            new CoordenacaoJuridica().setSigla(DEFAULT_SIGLA).setNome(DEFAULT_NOME).setCentena(DEFAULT_CENTENA);
        // Add required entity
        Assunto assunto = AssuntoResourceIntTest.createEntity(em);
        em.persist(assunto);
        em.flush();
        coordenacaoJuridica.getAssuntos().add(assunto);
        return coordenacaoJuridica;
    }
    
    @Before
    public void initTest() {
        coordenacaoJuridica = createEntity(em);
    }
    
    @Test
    @Transactional
    public void createCoordenacaoJuridica() throws Exception {
        int databaseSizeBeforeCreate = coordenacaoJuridicaRepository.findAll().size();
        
        // Create the CoordenacaoJuridica
        CoordenacaoJuridicaDTO coordenacaoJuridicaDTO =
            coordenacaoJuridicaMapper.toDto(coordenacaoJuridica);
        restCoordenacaoJuridicaMockMvc
            .perform(post("/api/coordenacao-juridicas").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                       .content(
                                                           TestUtil.convertObjectToJsonBytes(coordenacaoJuridicaDTO)))
            .andExpect(status().isCreated());
        
        // Validate the CoordenacaoJuridica in the database
        List<CoordenacaoJuridica> coordenacaoJuridicaList = coordenacaoJuridicaRepository.findAll();
        assertThat(coordenacaoJuridicaList).hasSize(databaseSizeBeforeCreate + 1);
        CoordenacaoJuridica testCoordenacaoJuridica =
            coordenacaoJuridicaList.get(coordenacaoJuridicaList.size() - 1);
        assertThat(testCoordenacaoJuridica.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testCoordenacaoJuridica.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCoordenacaoJuridica.getCentena()).isEqualTo(DEFAULT_CENTENA);
    }
    
    @Test
    @Transactional
    public void createCoordenacaoJuridicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordenacaoJuridicaRepository.findAll().size();
        
        // Create the CoordenacaoJuridica with an existing ID
        coordenacaoJuridica.setId(1L);
        CoordenacaoJuridicaDTO coordenacaoJuridicaDTO =
            coordenacaoJuridicaMapper.toDto(coordenacaoJuridica);
        
        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordenacaoJuridicaMockMvc
            .perform(post("/api/coordenacao-juridicas").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                       .content(
                                                           TestUtil.convertObjectToJsonBytes(coordenacaoJuridicaDTO)))
            .andExpect(status().isBadRequest());
        
        // Validate the CoordenacaoJuridica in the database
        List<CoordenacaoJuridica> coordenacaoJuridicaList = coordenacaoJuridicaRepository.findAll();
        assertThat(coordenacaoJuridicaList).hasSize(databaseSizeBeforeCreate);
    }
    
    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenacaoJuridicaRepository.findAll().size();
        // set the field null
        coordenacaoJuridica.setSigla(null);
        
        // Create the CoordenacaoJuridica, which fails.
        CoordenacaoJuridicaDTO coordenacaoJuridicaDTO =
            coordenacaoJuridicaMapper.toDto(coordenacaoJuridica);
        
        restCoordenacaoJuridicaMockMvc
            .perform(post("/api/coordenacao-juridicas").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                       .content(
                                                           TestUtil.convertObjectToJsonBytes(coordenacaoJuridicaDTO)))
            .andExpect(status().isBadRequest());
        
        List<CoordenacaoJuridica> coordenacaoJuridicaList = coordenacaoJuridicaRepository.findAll();
        assertThat(coordenacaoJuridicaList).hasSize(databaseSizeBeforeTest);
    }
    
    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenacaoJuridicaRepository.findAll().size();
        // set the field null
        coordenacaoJuridica.setNome(null);
        
        // Create the CoordenacaoJuridica, which fails.
        CoordenacaoJuridicaDTO coordenacaoJuridicaDTO =
            coordenacaoJuridicaMapper.toDto(coordenacaoJuridica);
        
        restCoordenacaoJuridicaMockMvc
            .perform(post("/api/coordenacao-juridicas").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                       .content(
                                                           TestUtil.convertObjectToJsonBytes(coordenacaoJuridicaDTO)))
            .andExpect(status().isBadRequest());
        
        List<CoordenacaoJuridica> coordenacaoJuridicaList = coordenacaoJuridicaRepository.findAll();
        assertThat(coordenacaoJuridicaList).hasSize(databaseSizeBeforeTest);
    }
    
    @Test
    @Transactional
    public void getAllCoordenacaoJuridicas() throws Exception {
        // Initialize the database
        coordenacaoJuridicaRepository.saveAndFlush(coordenacaoJuridica);
        
        // Get all the coordenacaoJuridicaList
        restCoordenacaoJuridicaMockMvc.perform(get("/api/coordenacao-juridicas?sort=id,desc"))
                                      .andExpect(status().isOk())
                                      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                                      .andExpect(
                                          jsonPath("$.[*].id").value(hasItem(coordenacaoJuridica.getId().intValue())))
                                      .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
                                      .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
                                      .andExpect(jsonPath("$.[*].centena").value(hasItem(DEFAULT_CENTENA)));
    }
    
    public void getAllCoordenacaoJuridicasWithEagerRelationshipsIsEnabled() throws Exception {
        CoordenacaoJuridicaResource coordenacaoJuridicaResource =
            new CoordenacaoJuridicaResource(coordenacaoJuridicaServiceMock);
        when(coordenacaoJuridicaServiceMock.findAllWithEagerRelationships(any(Pageable.class)))
            .thenReturn(new PageImpl<>(emptyList()));
        
        MockMvc restCoordenacaoJuridicaMockMvc =
            MockMvcBuilders.standaloneSetup(coordenacaoJuridicaResource)
                           .setCustomArgumentResolvers(pageableArgumentResolver)
                           .setControllerAdvice(exceptionTranslator)
                           .setConversionService(createFormattingConversionService())
                           .setMessageConverters(jacksonMessageConverter).build();
        
        restCoordenacaoJuridicaMockMvc.perform(get("/api/coordenacao-juridicas?eagerload=true"))
                                      .andExpect(status().isOk());
        
        verify(coordenacaoJuridicaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }
    
    public void getAllCoordenacaoJuridicasWithEagerRelationshipsIsNotEnabled() throws Exception {
        CoordenacaoJuridicaResource coordenacaoJuridicaResource =
            new CoordenacaoJuridicaResource(coordenacaoJuridicaServiceMock);
        when(coordenacaoJuridicaServiceMock.findAllWithEagerRelationships(any()))
            .thenReturn(new PageImpl<>(emptyList()));
        MockMvc restCoordenacaoJuridicaMockMvc =
            MockMvcBuilders.standaloneSetup(coordenacaoJuridicaResource)
                           .setCustomArgumentResolvers(pageableArgumentResolver)
                           .setControllerAdvice(exceptionTranslator)
                           .setConversionService(createFormattingConversionService())
                           .setMessageConverters(jacksonMessageConverter).build();
        
        restCoordenacaoJuridicaMockMvc.perform(get("/api/coordenacao-juridicas?eagerload=true"))
                                      .andExpect(status().isOk());
        
        verify(coordenacaoJuridicaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }
    
    @Test
    @Transactional
    public void getCoordenacaoJuridica() throws Exception {
        // Initialize the database
        coordenacaoJuridicaRepository.saveAndFlush(coordenacaoJuridica);
        
        // Get the coordenacaoJuridica
        restCoordenacaoJuridicaMockMvc
            .perform(get("/api/coordenacao-juridicas/{id}", coordenacaoJuridica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordenacaoJuridica.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.centena").value(DEFAULT_CENTENA));
    }
    
    @Test
    @Transactional
    public void getNonExistingCoordenacaoJuridica() throws Exception {
        // Get the coordenacaoJuridica
        restCoordenacaoJuridicaMockMvc.perform(get("/api/coordenacao-juridicas/{id}", Long.MAX_VALUE))
                                      .andExpect(status().isNotFound());
    }
    
    @Test
    @Transactional
    public void updateCoordenacaoJuridica() throws Exception {
        // Initialize the database
        coordenacaoJuridicaRepository.saveAndFlush(coordenacaoJuridica);
        
        int databaseSizeBeforeUpdate = coordenacaoJuridicaRepository.findAll().size();
        
        // Update the coordenacaoJuridica
        CoordenacaoJuridica updatedCoordenacaoJuridica =
            coordenacaoJuridicaRepository.findById(coordenacaoJuridica.getId()).get();
        // Disconnect from session so that the updates on updatedCoordenacaoJuridica are not directly
        // saved in db
        em.detach(updatedCoordenacaoJuridica);
        updatedCoordenacaoJuridica.setSigla(UPDATED_SIGLA).setNome(UPDATED_NOME).setCentena(UPDATED_CENTENA);
        CoordenacaoJuridicaDTO coordenacaoJuridicaDTO =
            coordenacaoJuridicaMapper.toDto(updatedCoordenacaoJuridica);
        
        restCoordenacaoJuridicaMockMvc
            .perform(put("/api/coordenacao-juridicas").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                      .content(
                                                          TestUtil.convertObjectToJsonBytes(coordenacaoJuridicaDTO)))
            .andExpect(status().isOk());
        
        // Validate the CoordenacaoJuridica in the database
        List<CoordenacaoJuridica> coordenacaoJuridicaList = coordenacaoJuridicaRepository.findAll();
        assertThat(coordenacaoJuridicaList).hasSize(databaseSizeBeforeUpdate);
        CoordenacaoJuridica testCoordenacaoJuridica =
            coordenacaoJuridicaList.get(coordenacaoJuridicaList.size() - 1);
        assertThat(testCoordenacaoJuridica.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testCoordenacaoJuridica.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCoordenacaoJuridica.getCentena()).isEqualTo(UPDATED_CENTENA);
    }
    
    @Test
    @Transactional
    public void updateNonExistingCoordenacaoJuridica() throws Exception {
        int databaseSizeBeforeUpdate = coordenacaoJuridicaRepository.findAll().size();
        
        // Create the CoordenacaoJuridica
        CoordenacaoJuridicaDTO coordenacaoJuridicaDTO =
            coordenacaoJuridicaMapper.toDto(coordenacaoJuridica);
        
        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoordenacaoJuridicaMockMvc
            .perform(put("/api/coordenacao-juridicas").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                      .content(
                                                          TestUtil.convertObjectToJsonBytes(coordenacaoJuridicaDTO)))
            .andExpect(status().isBadRequest());
        
        // Validate the CoordenacaoJuridica in the database
        List<CoordenacaoJuridica> coordenacaoJuridicaList = coordenacaoJuridicaRepository.findAll();
        assertThat(coordenacaoJuridicaList).hasSize(databaseSizeBeforeUpdate);
    }
    
    @Test
    @Transactional
    public void deleteCoordenacaoJuridica() throws Exception {
        // Initialize the database
        coordenacaoJuridicaRepository.saveAndFlush(coordenacaoJuridica);
        
        int databaseSizeBeforeDelete = coordenacaoJuridicaRepository.findAll().size();
        
        // Get the coordenacaoJuridica
        restCoordenacaoJuridicaMockMvc
            .perform(delete("/api/coordenacao-juridicas/{id}", coordenacaoJuridica.getId())
                         .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
        
        // Validate the database is empty
        List<CoordenacaoJuridica> coordenacaoJuridicaList = coordenacaoJuridicaRepository.findAll();
        assertThat(coordenacaoJuridicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
    
    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoordenacaoJuridica.class);
        CoordenacaoJuridica coordenacaoJuridica1 = new CoordenacaoJuridica();
        coordenacaoJuridica1.setId(1L);
        CoordenacaoJuridica coordenacaoJuridica2 = new CoordenacaoJuridica();
        coordenacaoJuridica2.setId(coordenacaoJuridica1.getId());
        assertThat(coordenacaoJuridica1).isEqualTo(coordenacaoJuridica2);
        coordenacaoJuridica2.setId(2L);
        assertThat(coordenacaoJuridica1).isNotEqualTo(coordenacaoJuridica2);
        coordenacaoJuridica1.setId(null);
        assertThat(coordenacaoJuridica1).isNotEqualTo(coordenacaoJuridica2);
    }
    
    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoordenacaoJuridicaDTO.class);
        CoordenacaoJuridicaDTO coordenacaoJuridicaDTO1 = new CoordenacaoJuridicaDTO();
        coordenacaoJuridicaDTO1.setId(1L);
        CoordenacaoJuridicaDTO coordenacaoJuridicaDTO2 = new CoordenacaoJuridicaDTO();
        assertThat(coordenacaoJuridicaDTO1).isNotEqualTo(coordenacaoJuridicaDTO2);
        coordenacaoJuridicaDTO2.setId(coordenacaoJuridicaDTO1.getId());
        assertThat(coordenacaoJuridicaDTO1).isEqualTo(coordenacaoJuridicaDTO2);
        coordenacaoJuridicaDTO2.setId(2L);
        assertThat(coordenacaoJuridicaDTO1).isNotEqualTo(coordenacaoJuridicaDTO2);
        coordenacaoJuridicaDTO1.setId(null);
        assertThat(coordenacaoJuridicaDTO1).isNotEqualTo(coordenacaoJuridicaDTO2);
    }
    
    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coordenacaoJuridicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coordenacaoJuridicaMapper.fromId(null)).isNull();
    }
}
