package com.rcsoyer.servicosjuridicos.web.rest;

import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;

import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.repository.FeriasLicencaRepository;
import com.rcsoyer.servicosjuridicos.service.FeriasLicencaService;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.FeriasLicencaMapper;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.rcsoyer.servicosjuridicos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rcsoyer.servicosjuridicos.domain.enumeration.FeriasLicensaTipo;
/**
 * Test class for the FeriasLicencaResource REST controller.
 *
 * @see FeriasLicencaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
public class FeriasLicencaResourceIntTest {

    private static final LocalDate DEFAULT_DT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DT_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_FIM = LocalDate.now(ZoneId.systemDefault());

    private static final FeriasLicensaTipo DEFAULT_TIPO = FeriasLicensaTipo.FERIAS;
    private static final FeriasLicensaTipo UPDATED_TIPO = FeriasLicensaTipo.LICENSA;

    @Autowired
    private FeriasLicencaRepository feriasLicencaRepository;


    @Autowired
    private FeriasLicencaMapper feriasLicencaMapper;
    

    @Autowired
    private FeriasLicencaService feriasLicencaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFeriasLicencaMockMvc;

    private FeriasLicenca feriasLicenca;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeriasLicencaResource feriasLicencaResource = new FeriasLicencaResource(feriasLicencaService);
        this.restFeriasLicencaMockMvc = MockMvcBuilders.standaloneSetup(feriasLicencaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeriasLicenca createEntity(EntityManager em) {
        FeriasLicenca feriasLicenca = new FeriasLicenca()
            .setDtInicio(DEFAULT_DT_INICIO)
            .setDtFim(DEFAULT_DT_FIM)
            .setTipo(DEFAULT_TIPO);
        // Add required entity
        Advogado advogado = AdvogadoResourceIntTest.createEntity(em);
        em.persist(advogado);
        em.flush();
        feriasLicenca.setAdvogado(advogado);
        return feriasLicenca;
    }

    @Before
    public void initTest() {
        feriasLicenca = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeriasLicenca() throws Exception {
        int databaseSizeBeforeCreate = feriasLicencaRepository.findAll().size();

        // Create the FeriasLicenca
        FeriasLicencaDTO feriasLicencaDTO = feriasLicencaMapper.toDto(feriasLicenca);
        restFeriasLicencaMockMvc.perform(post("/api/ferias-licencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feriasLicencaDTO)))
            .andExpect(status().isCreated());

        // Validate the FeriasLicenca in the database
        List<FeriasLicenca> feriasLicencaList = feriasLicencaRepository.findAll();
        assertThat(feriasLicencaList).hasSize(databaseSizeBeforeCreate + 1);
        FeriasLicenca testFeriasLicenca = feriasLicencaList.get(feriasLicencaList.size() - 1);
        assertThat(testFeriasLicenca.getDtInicio()).isEqualTo(DEFAULT_DT_INICIO);
        assertThat(testFeriasLicenca.getDtFim()).isEqualTo(DEFAULT_DT_FIM);
        assertThat(testFeriasLicenca.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createFeriasLicencaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feriasLicencaRepository.findAll().size();

        // Create the FeriasLicenca with an existing ID
        feriasLicenca.setId(1L);
        FeriasLicencaDTO feriasLicencaDTO = feriasLicencaMapper.toDto(feriasLicenca);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeriasLicencaMockMvc.perform(post("/api/ferias-licencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feriasLicencaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeriasLicenca in the database
        List<FeriasLicenca> feriasLicencaList = feriasLicencaRepository.findAll();
        assertThat(feriasLicencaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDtInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = feriasLicencaRepository.findAll().size();
        // set the field null
        feriasLicenca.setDtInicio(null);

        // Create the FeriasLicenca, which fails.
        FeriasLicencaDTO feriasLicencaDTO = feriasLicencaMapper.toDto(feriasLicenca);

        restFeriasLicencaMockMvc.perform(post("/api/ferias-licencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feriasLicencaDTO)))
            .andExpect(status().isBadRequest());

        List<FeriasLicenca> feriasLicencaList = feriasLicencaRepository.findAll();
        assertThat(feriasLicencaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDtFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = feriasLicencaRepository.findAll().size();
        // set the field null
        feriasLicenca.setDtFim(null);

        // Create the FeriasLicenca, which fails.
        FeriasLicencaDTO feriasLicencaDTO = feriasLicencaMapper.toDto(feriasLicenca);

        restFeriasLicencaMockMvc.perform(post("/api/ferias-licencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feriasLicencaDTO)))
            .andExpect(status().isBadRequest());

        List<FeriasLicenca> feriasLicencaList = feriasLicencaRepository.findAll();
        assertThat(feriasLicencaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = feriasLicencaRepository.findAll().size();
        // set the field null
        feriasLicenca.setTipo(null);

        // Create the FeriasLicenca, which fails.
        FeriasLicencaDTO feriasLicencaDTO = feriasLicencaMapper.toDto(feriasLicenca);

        restFeriasLicencaMockMvc.perform(post("/api/ferias-licencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feriasLicencaDTO)))
            .andExpect(status().isBadRequest());

        List<FeriasLicenca> feriasLicencaList = feriasLicencaRepository.findAll();
        assertThat(feriasLicencaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeriasLicencas() throws Exception {
        // Initialize the database
        feriasLicencaRepository.saveAndFlush(feriasLicenca);

        // Get all the feriasLicencaList
        restFeriasLicencaMockMvc.perform(get("/api/ferias-licencas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feriasLicenca.getId().intValue())))
            .andExpect(jsonPath("$.[*].dtInicio").value(hasItem(DEFAULT_DT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dtFim").value(hasItem(DEFAULT_DT_FIM.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    

    @Test
    @Transactional
    public void getFeriasLicenca() throws Exception {
        // Initialize the database
        feriasLicencaRepository.saveAndFlush(feriasLicenca);

        // Get the feriasLicenca
        restFeriasLicencaMockMvc.perform(get("/api/ferias-licencas/{id}", feriasLicenca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feriasLicenca.getId().intValue()))
            .andExpect(jsonPath("$.dtInicio").value(DEFAULT_DT_INICIO.toString()))
            .andExpect(jsonPath("$.dtFim").value(DEFAULT_DT_FIM.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFeriasLicenca() throws Exception {
        // Get the feriasLicenca
        restFeriasLicencaMockMvc.perform(get("/api/ferias-licencas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeriasLicenca() throws Exception {
        // Initialize the database
        feriasLicencaRepository.saveAndFlush(feriasLicenca);

        int databaseSizeBeforeUpdate = feriasLicencaRepository.findAll().size();

        // Update the feriasLicenca
        FeriasLicenca updatedFeriasLicenca = feriasLicencaRepository.findById(feriasLicenca.getId()).get();
        // Disconnect from session so that the updates on updatedFeriasLicenca are not directly saved in db
        em.detach(updatedFeriasLicenca);
        updatedFeriasLicenca
            .setDtInicio(UPDATED_DT_INICIO)
            .setDtFim(UPDATED_DT_FIM)
            .setTipo(UPDATED_TIPO);
        FeriasLicencaDTO feriasLicencaDTO = feriasLicencaMapper.toDto(updatedFeriasLicenca);

        restFeriasLicencaMockMvc.perform(put("/api/ferias-licencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feriasLicencaDTO)))
            .andExpect(status().isOk());

        // Validate the FeriasLicenca in the database
        List<FeriasLicenca> feriasLicencaList = feriasLicencaRepository.findAll();
        assertThat(feriasLicencaList).hasSize(databaseSizeBeforeUpdate);
        FeriasLicenca testFeriasLicenca = feriasLicencaList.get(feriasLicencaList.size() - 1);
        assertThat(testFeriasLicenca.getDtInicio()).isEqualTo(UPDATED_DT_INICIO);
        assertThat(testFeriasLicenca.getDtFim()).isEqualTo(UPDATED_DT_FIM);
        assertThat(testFeriasLicenca.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingFeriasLicenca() throws Exception {
        int databaseSizeBeforeUpdate = feriasLicencaRepository.findAll().size();

        // Create the FeriasLicenca
        FeriasLicencaDTO feriasLicencaDTO = feriasLicencaMapper.toDto(feriasLicenca);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFeriasLicencaMockMvc.perform(put("/api/ferias-licencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feriasLicencaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeriasLicenca in the database
        List<FeriasLicenca> feriasLicencaList = feriasLicencaRepository.findAll();
        assertThat(feriasLicencaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeriasLicenca() throws Exception {
        // Initialize the database
        feriasLicencaRepository.saveAndFlush(feriasLicenca);

        int databaseSizeBeforeDelete = feriasLicencaRepository.findAll().size();

        // Get the feriasLicenca
        restFeriasLicencaMockMvc.perform(delete("/api/ferias-licencas/{id}", feriasLicenca.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FeriasLicenca> feriasLicencaList = feriasLicencaRepository.findAll();
        assertThat(feriasLicencaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeriasLicenca.class);
        FeriasLicenca feriasLicenca1 = new FeriasLicenca();
        feriasLicenca1.setId(1L);
        FeriasLicenca feriasLicenca2 = new FeriasLicenca();
        feriasLicenca2.setId(feriasLicenca1.getId());
        assertThat(feriasLicenca1).isEqualTo(feriasLicenca2);
        feriasLicenca2.setId(2L);
        assertThat(feriasLicenca1).isNotEqualTo(feriasLicenca2);
        feriasLicenca1.setId(null);
        assertThat(feriasLicenca1).isNotEqualTo(feriasLicenca2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeriasLicencaDTO.class);
        FeriasLicencaDTO feriasLicencaDTO1 = new FeriasLicencaDTO();
        feriasLicencaDTO1.setId(1L);
        FeriasLicencaDTO feriasLicencaDTO2 = new FeriasLicencaDTO();
        assertThat(feriasLicencaDTO1).isNotEqualTo(feriasLicencaDTO2);
        feriasLicencaDTO2.setId(feriasLicencaDTO1.getId());
        assertThat(feriasLicencaDTO1).isEqualTo(feriasLicencaDTO2);
        feriasLicencaDTO2.setId(2L);
        assertThat(feriasLicencaDTO1).isNotEqualTo(feriasLicencaDTO2);
        feriasLicencaDTO1.setId(null);
        assertThat(feriasLicencaDTO1).isNotEqualTo(feriasLicencaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(feriasLicencaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(feriasLicencaMapper.fromId(null)).isNull();
    }
}
