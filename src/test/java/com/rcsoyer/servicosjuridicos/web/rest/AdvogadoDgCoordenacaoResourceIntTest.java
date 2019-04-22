package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.convertObjectToJsonBytes;
import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao;
import com.rcsoyer.servicosjuridicos.repository.AdvogadoDgCoordenacaoRepository;
import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoDgCoordenacaoMapper;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for the AdvogadoDgCoordenacaoResource REST controller.
 *
 * @see AdvogadoDgCoordenacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
public class AdvogadoDgCoordenacaoResourceIntTest {
    
    private static final Integer DEFAULT_DG_PESSOAL_INICIO = 1;
    private static final Integer UPDATED_DG_PESSOAL_INICIO = 2;
    private static final Integer DEFAULT_DG_PESSOAL_FIM = 1;
    private static final Integer UPDATED_DG_PESSOAL_FIM = 2;
    private static final Integer DEFAULT_DG_DUPLA = 1;
    private static final Integer UPDATED_DG_DUPLA = 2;
    
    private static final RangeDgCoordenacao DEFAULT_RANGE_DG_COORDENACAO = RangeDgCoordenacao.INCLUSIVE;
    private static final RangeDgCoordenacao UPDATED_RANGE_DG_COORDENACAO = RangeDgCoordenacao.EXCLUSIVE;
    
    @Autowired
    private AdvogadoDgCoordenacaoRepository advogadoDgCoordenacaoRepository;
    
    
    @Autowired
    private AdvogadoDgCoordenacaoMapper advogadoDgCoordenacaoMapper;
    
    
    @Autowired
    private AdvogadoDgCoordenacaoService advogadoDgCoordenacaoService;
    
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
    @Autowired
    private ExceptionTranslator exceptionTranslator;
    
    @Autowired
    private EntityManager em;
    
    private MockMvc restAdvogadoDgCoordenacaoMockMvc;
    
    private AdvogadoDgCoordenacao advogadoDgCoordenacao;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdvogadoDgCoordenacaoResource advogadoDgCoordenacaoResource = new AdvogadoDgCoordenacaoResource(
            advogadoDgCoordenacaoService);
        this.restAdvogadoDgCoordenacaoMockMvc = MockMvcBuilders.standaloneSetup(advogadoDgCoordenacaoResource)
                                                               .setCustomArgumentResolvers(pageableArgumentResolver)
                                                               .setControllerAdvice(exceptionTranslator)
                                                               .setConversionService(
                                                                   createFormattingConversionService())
                                                               .setMessageConverters(jacksonMessageConverter).build();
    }
    
    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it, if they test an entity which requires
     * the current entity.
     */
    public static AdvogadoDgCoordenacao createEntity(EntityManager em) {
        AdvogadoDgCoordenacao advogadoDgCoordenacao = new AdvogadoDgCoordenacao()
                                                          .setDgPessoalInicio(DEFAULT_DG_PESSOAL_INICIO)
                                                          .setDgPessoalFim(DEFAULT_DG_PESSOAL_FIM)
                                                          .setDgDupla(DEFAULT_DG_DUPLA)
                                                          .setRangeDgCoordenacao(DEFAULT_RANGE_DG_COORDENACAO);
        // Add required entity
        Advogado advogado = AdvogadoResourceIntTest.createEntity(em);
        em.persist(advogado);
        em.flush();
        advogadoDgCoordenacao.setAdvogado(advogado);
        // Add required entity
        CoordenacaoJuridica coordenacaoJuridica = CoordenacaoJuridicaResourceIntTest.createEntity(em);
        em.persist(coordenacaoJuridica);
        em.flush();
        advogadoDgCoordenacao.setCoordenacao(coordenacaoJuridica);
        return advogadoDgCoordenacao;
    }
    
    @Before
    public void initTest() {
        advogadoDgCoordenacao = createEntity(em);
    }
    
    @Test
    @Transactional
    public void createAdvogadoDgCoordenacao() throws Exception {
        int databaseSizeBeforeCreate = advogadoDgCoordenacaoRepository.findAll().size();
        
        // Create the AdvogadoDgCoordenacao
        AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO = advogadoDgCoordenacaoMapper.toDto(advogadoDgCoordenacao);
        restAdvogadoDgCoordenacaoMockMvc.perform(post("/api/advogado-dg-coordenacaos")
                                                     .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                     .content(
                                                         convertObjectToJsonBytes(advogadoDgCoordenacaoDTO)))
                                        .andExpect(status().isCreated());
        
        // Validate the AdvogadoDgCoordenacao in the database
        List<AdvogadoDgCoordenacao> advogadoDgCoordenacaoList = advogadoDgCoordenacaoRepository.findAll();
        assertThat(advogadoDgCoordenacaoList).hasSize(databaseSizeBeforeCreate + 1);
        AdvogadoDgCoordenacao testAdvogadoDgCoordenacao = advogadoDgCoordenacaoList
                                                              .get(advogadoDgCoordenacaoList.size() - 1);
        assertThat(testAdvogadoDgCoordenacao.getDgPessoalInicio()).isEqualTo(DEFAULT_DG_PESSOAL_INICIO);
        assertThat(testAdvogadoDgCoordenacao.getDgPessoalFim()).isEqualTo(DEFAULT_DG_PESSOAL_FIM);
        assertThat(testAdvogadoDgCoordenacao.getDgDupla()).isEqualTo(DEFAULT_DG_DUPLA);
        assertThat(testAdvogadoDgCoordenacao.getRangeDgCoordenacao()).isEqualTo(DEFAULT_RANGE_DG_COORDENACAO);
    }
    
    @Test
    @Transactional
    public void createAdvogadoDgCoordenacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advogadoDgCoordenacaoRepository.findAll().size();
        
        // Create the AdvogadoDgCoordenacao with an existing ID
        advogadoDgCoordenacao.setId(1L);
        AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO = advogadoDgCoordenacaoMapper.toDto(advogadoDgCoordenacao);
        
        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvogadoDgCoordenacaoMockMvc.perform(post("/api/advogado-dg-coordenacaos")
                                                     .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                     .content(
                                                         convertObjectToJsonBytes(advogadoDgCoordenacaoDTO)))
                                        .andExpect(status().isBadRequest());
        
        // Validate the AdvogadoDgCoordenacao in the database
        List<AdvogadoDgCoordenacao> advogadoDgCoordenacaoList = advogadoDgCoordenacaoRepository.findAll();
        assertThat(advogadoDgCoordenacaoList).hasSize(databaseSizeBeforeCreate);
    }
    
    @Test
    @Transactional
    public void checkDgPessoalInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = advogadoDgCoordenacaoRepository.findAll().size();
        // set the field null
        advogadoDgCoordenacao.setDgPessoalInicio(null);
        
        // Create the AdvogadoDgCoordenacao, which fails.
        AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO = advogadoDgCoordenacaoMapper.toDto(advogadoDgCoordenacao);
        
        restAdvogadoDgCoordenacaoMockMvc.perform(post("/api/advogado-dg-coordenacaos")
                                                     .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                     .content(
                                                         convertObjectToJsonBytes(advogadoDgCoordenacaoDTO)))
                                        .andExpect(status().isBadRequest());
        
        List<AdvogadoDgCoordenacao> advogadoDgCoordenacaoList = advogadoDgCoordenacaoRepository.findAll();
        assertThat(advogadoDgCoordenacaoList).hasSize(databaseSizeBeforeTest);
    }
    
    @Test
    @Transactional
    public void getAllAdvogadoDgCoordenacaos() throws Exception {
        // Initialize the database
        advogadoDgCoordenacaoRepository.saveAndFlush(advogadoDgCoordenacao);
        
        // Get all the advogadoDgCoordenacaoList
        restAdvogadoDgCoordenacaoMockMvc.perform(get("/api/advogado-dg-coordenacaos?sort=id,desc"))
                                        .andExpect(status().isOk())
                                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                                        .andExpect(jsonPath("$.[*].id")
                                                       .value(hasItem(advogadoDgCoordenacao.getId().intValue())))
                                        .andExpect(jsonPath("$.[*].dgPessoalInicio")
                                                       .value(hasItem(DEFAULT_DG_PESSOAL_INICIO)))
                                        .andExpect(jsonPath("$.[*].dgPessoalFim")
                                                       .value(hasItem(DEFAULT_DG_PESSOAL_FIM)))
                                        .andExpect(
                                            jsonPath("$.[*].dgDupla").value(hasItem(DEFAULT_DG_DUPLA)))
                                        .andExpect(jsonPath("$.[*].rangeDgCoordenacao")
                                                       .value(hasItem(DEFAULT_RANGE_DG_COORDENACAO.toString())));
    }
    
    
    @Test
    @Transactional
    public void getAdvogadoDgCoordenacao() throws Exception {
        // Initialize the database
        advogadoDgCoordenacaoRepository.saveAndFlush(advogadoDgCoordenacao);
        
        // Get the advogadoDgCoordenacao
        restAdvogadoDgCoordenacaoMockMvc
            .perform(get("/api/advogado-dg-coordenacaos/{id}", advogadoDgCoordenacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(advogadoDgCoordenacao.getId().intValue()))
            .andExpect(jsonPath("$.dgPessoalInicio").value(DEFAULT_DG_PESSOAL_INICIO.toString()))
            .andExpect(jsonPath("$.dgPessoalFim").value(DEFAULT_DG_PESSOAL_FIM.toString()))
            .andExpect(jsonPath("$.dgDupla").value(DEFAULT_DG_DUPLA.toString()))
            .andExpect(jsonPath("$.rangeDgCoordenacao").value(DEFAULT_RANGE_DG_COORDENACAO.toString()));
    }
    
    @Test
    @Transactional
    public void getNonExistingAdvogadoDgCoordenacao() throws Exception {
        // Get the advogadoDgCoordenacao
        restAdvogadoDgCoordenacaoMockMvc.perform(get("/api/advogado-dg-coordenacaos/{id}", Long.MAX_VALUE))
                                        .andExpect(status().isNotFound());
    }
    
    @Test
    @Transactional
    public void updateAdvogadoDgCoordenacao() throws Exception {
        // Initialize the database
        advogadoDgCoordenacaoRepository.saveAndFlush(advogadoDgCoordenacao);
        
        int databaseSizeBeforeUpdate = advogadoDgCoordenacaoRepository.findAll().size();
        
        // Update the advogadoDgCoordenacao
        AdvogadoDgCoordenacao updatedAdvogadoDgCoordenacao = advogadoDgCoordenacaoRepository
                                                                 .findById(advogadoDgCoordenacao.getId()).get();
        // Disconnect from session so that the updates on updatedAdvogadoDgCoordenacao are not directly saved in db
        em.detach(updatedAdvogadoDgCoordenacao);
        updatedAdvogadoDgCoordenacao
            .setDgPessoalInicio(UPDATED_DG_PESSOAL_INICIO)
            .setDgPessoalFim(UPDATED_DG_PESSOAL_FIM)
            .setDgDupla(UPDATED_DG_DUPLA)
            .setRangeDgCoordenacao(UPDATED_RANGE_DG_COORDENACAO);
        AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO = advogadoDgCoordenacaoMapper
                                                                .toDto(updatedAdvogadoDgCoordenacao);
        
        restAdvogadoDgCoordenacaoMockMvc.perform(put("/api/advogado-dg-coordenacaos")
                                                     .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                     .content(
                                                         convertObjectToJsonBytes(advogadoDgCoordenacaoDTO)))
                                        .andExpect(status().isOk());
        
        // Validate the AdvogadoDgCoordenacao in the database
        List<AdvogadoDgCoordenacao> advogadoDgCoordenacaoList = advogadoDgCoordenacaoRepository.findAll();
        assertThat(advogadoDgCoordenacaoList).hasSize(databaseSizeBeforeUpdate);
        AdvogadoDgCoordenacao testAdvogadoDgCoordenacao = advogadoDgCoordenacaoList
                                                              .get(advogadoDgCoordenacaoList.size() - 1);
        assertThat(testAdvogadoDgCoordenacao.getDgPessoalInicio()).isEqualTo(UPDATED_DG_PESSOAL_INICIO);
        assertThat(testAdvogadoDgCoordenacao.getDgPessoalFim()).isEqualTo(UPDATED_DG_PESSOAL_FIM);
        assertThat(testAdvogadoDgCoordenacao.getDgDupla()).isEqualTo(UPDATED_DG_DUPLA);
        assertThat(testAdvogadoDgCoordenacao.getRangeDgCoordenacao()).isEqualTo(UPDATED_RANGE_DG_COORDENACAO);
    }
    
    @Test
    @Transactional
    public void updateNonExistingAdvogadoDgCoordenacao() throws Exception {
        int databaseSizeBeforeUpdate = advogadoDgCoordenacaoRepository.findAll().size();
        
        // Create the AdvogadoDgCoordenacao
        AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO = advogadoDgCoordenacaoMapper.toDto(advogadoDgCoordenacao);
        
        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdvogadoDgCoordenacaoMockMvc.perform(put("/api/advogado-dg-coordenacaos")
                                                     .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                                     .content(
                                                         convertObjectToJsonBytes(advogadoDgCoordenacaoDTO)))
                                        .andExpect(status().isBadRequest());
        
        // Validate the AdvogadoDgCoordenacao in the database
        List<AdvogadoDgCoordenacao> advogadoDgCoordenacaoList = advogadoDgCoordenacaoRepository.findAll();
        assertThat(advogadoDgCoordenacaoList).hasSize(databaseSizeBeforeUpdate);
    }
    
    @Test
    @Transactional
    public void deleteAdvogadoDgCoordenacao() throws Exception {
        // Initialize the database
        advogadoDgCoordenacaoRepository.saveAndFlush(advogadoDgCoordenacao);
        
        int databaseSizeBeforeDelete = advogadoDgCoordenacaoRepository.findAll().size();
        
        // Get the advogadoDgCoordenacao
        restAdvogadoDgCoordenacaoMockMvc
            .perform(delete("/api/advogado-dg-coordenacaos/{id}", advogadoDgCoordenacao.getId())
                         .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
        
        // Validate the database is empty
        List<AdvogadoDgCoordenacao> advogadoDgCoordenacaoList = advogadoDgCoordenacaoRepository.findAll();
        assertThat(advogadoDgCoordenacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
    
    @Test
    @Transactional
    public void equalsVerifier() {
        AdvogadoDgCoordenacao advogadoDgCoordenacao1 = new AdvogadoDgCoordenacao();
        advogadoDgCoordenacao1.setId(1L);
        AdvogadoDgCoordenacao advogadoDgCoordenacao2 = new AdvogadoDgCoordenacao();
        advogadoDgCoordenacao2.setId(advogadoDgCoordenacao1.getId());
        assertThat(advogadoDgCoordenacao1).isEqualTo(advogadoDgCoordenacao2);
        advogadoDgCoordenacao2.setId(2L);
        assertThat(advogadoDgCoordenacao1).isNotEqualTo(advogadoDgCoordenacao2);
        advogadoDgCoordenacao1.setId(null);
        assertThat(advogadoDgCoordenacao1).isNotEqualTo(advogadoDgCoordenacao2);
    }
    
    @Test
    @Transactional
    public void dtoEqualsVerifier() {
        AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO1 = new AdvogadoDgCoordenacaoDTO();
        advogadoDgCoordenacaoDTO1.setId(1L);
        AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO2 = new AdvogadoDgCoordenacaoDTO();
        assertThat(advogadoDgCoordenacaoDTO1).isNotEqualTo(advogadoDgCoordenacaoDTO2);
        advogadoDgCoordenacaoDTO2.setId(advogadoDgCoordenacaoDTO1.getId());
        assertThat(advogadoDgCoordenacaoDTO1).isEqualTo(advogadoDgCoordenacaoDTO2);
        advogadoDgCoordenacaoDTO2.setId(2L);
        assertThat(advogadoDgCoordenacaoDTO1).isNotEqualTo(advogadoDgCoordenacaoDTO2);
        advogadoDgCoordenacaoDTO1.setId(null);
        assertThat(advogadoDgCoordenacaoDTO1).isNotEqualTo(advogadoDgCoordenacaoDTO2);
    }
    
    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(advogadoDgCoordenacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(advogadoDgCoordenacaoMapper.fromId(null)).isNull();
    }
}
