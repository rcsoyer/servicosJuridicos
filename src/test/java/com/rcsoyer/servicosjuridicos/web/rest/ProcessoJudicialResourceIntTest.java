package com.rcsoyer.servicosjuridicos.web.rest;

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
import java.time.LocalDate;
import java.time.ZoneId;
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
import com.rcsoyer.servicosjuridicos.ServicosJuridicosApp;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.repository.processo.ProcessoJudicialRepository;
import com.rcsoyer.servicosjuridicos.service.ProcessoJudicialService;
import com.rcsoyer.servicosjuridicos.service.dto.ProcessoJudicialDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.ProcessoJudicialMapper;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ProcessoJudicialResource REST controller.
 *
 * @see ProcessoJudicialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
public class ProcessoJudicialResourceIntTest {

  private static final String DEFAULT_NUMERO = "AAAAAAAAAAAAAAAAAAAA";
  private static final String UPDATED_NUMERO = "BBBBBBBBBBBBBBBBBBBB";

  private static final LocalDate DEFAULT_PRAZO_FINAL = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_PRAZO_FINAL = LocalDate.now(ZoneId.systemDefault());

  private static final LocalDate DEFAULT_DT_ATRIBUICAO = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_DT_ATRIBUICAO = LocalDate.now(ZoneId.systemDefault());

  private static final LocalDate DEFAULT_DT_INICIO = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_DT_INICIO = LocalDate.now(ZoneId.systemDefault());

  private static final LocalDate DEFAULT_DT_CONCLUSAO = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_DT_CONCLUSAO = LocalDate.now(ZoneId.systemDefault());

  @Autowired
  private ProcessoJudicialRepository processoJudicialRepository;


  @Autowired
  private ProcessoJudicialMapper processoJudicialMapper;


  @Autowired
  private ProcessoJudicialService processoJudicialService;

  @Autowired
  private MappingJackson2HttpMessageConverter jacksonMessageConverter;

  @Autowired
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

  @Autowired
  private ExceptionTranslator exceptionTranslator;

  @Autowired
  private EntityManager em;

  private MockMvc restProcessoJudicialMockMvc;

  private ProcessoJudicial processoJudicial;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    final ProcessoJudicialResource processoJudicialResource =
        new ProcessoJudicialResource(processoJudicialService);
    this.restProcessoJudicialMockMvc = MockMvcBuilders.standaloneSetup(processoJudicialResource)
        .setCustomArgumentResolvers(pageableArgumentResolver)
        .setControllerAdvice(exceptionTranslator)
        .setConversionService(createFormattingConversionService())
        .setMessageConverters(jacksonMessageConverter).build();
  }

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it, if they test an entity
   * which requires the current entity.
   */
  public static ProcessoJudicial createEntity(EntityManager em) {
    ProcessoJudicial processoJudicial = new ProcessoJudicial().setNumero(DEFAULT_NUMERO)
        .setPrazoFinal(DEFAULT_PRAZO_FINAL).setDtAtribuicao(DEFAULT_DT_ATRIBUICAO)
        .setDtInicio(DEFAULT_DT_INICIO).setDtConclusao(DEFAULT_DT_CONCLUSAO);
    // Add required entity
    Assunto assunto = AssuntoResourceIntTest.createEntity(em);
    em.persist(assunto);
    em.flush();
    processoJudicial.setAssunto(assunto);
    // Add required entity
    Modalidade modalidade = ModalidadeResourceIntTest.createEntity(em);
    em.persist(modalidade);
    em.flush();
    processoJudicial.setModalidade(modalidade);
    // Add required entity
    Advogado advogado = AdvogadoResourceIntTest.createEntity(em);
    em.persist(advogado);
    em.flush();
    processoJudicial.setAdvogado(advogado);
    return processoJudicial;
  }

  @Before
  public void initTest() {
    processoJudicial = createEntity(em);
  }

  @Test
  @Transactional
  public void createProcessoJudicial() throws Exception {
    int databaseSizeBeforeCreate = processoJudicialRepository.findAll().size();

    // Create the ProcessoJudicial
    ProcessoJudicialDTO processoJudicialDTO = processoJudicialMapper.toDto(processoJudicial);
    restProcessoJudicialMockMvc
        .perform(post("/api/processo-judicials").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoJudicialDTO)))
        .andExpect(status().isCreated());

    // Validate the ProcessoJudicial in the database
    List<ProcessoJudicial> processoJudicialList = processoJudicialRepository.findAll();
    assertThat(processoJudicialList).hasSize(databaseSizeBeforeCreate + 1);
    ProcessoJudicial testProcessoJudicial =
        processoJudicialList.get(processoJudicialList.size() - 1);
    assertThat(testProcessoJudicial.getNumero()).isEqualTo(DEFAULT_NUMERO);
    assertThat(testProcessoJudicial.getPrazoFinal()).isEqualTo(DEFAULT_PRAZO_FINAL);
    assertThat(testProcessoJudicial.getDtAtribuicao()).isEqualTo(DEFAULT_DT_ATRIBUICAO);
    assertThat(testProcessoJudicial.getDtInicio()).isEqualTo(DEFAULT_DT_INICIO);
    assertThat(testProcessoJudicial.getDtConclusao()).isEqualTo(DEFAULT_DT_CONCLUSAO);
  }

  @Test
  @Transactional
  public void createProcessoJudicialWithExistingId() throws Exception {
    int databaseSizeBeforeCreate = processoJudicialRepository.findAll().size();

    // Create the ProcessoJudicial with an existing ID
    processoJudicial.setId(1L);
    ProcessoJudicialDTO processoJudicialDTO = processoJudicialMapper.toDto(processoJudicial);

    // An entity with an existing ID cannot be created, so this API call must fail
    restProcessoJudicialMockMvc
        .perform(post("/api/processo-judicials").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoJudicialDTO)))
        .andExpect(status().isBadRequest());

    // Validate the ProcessoJudicial in the database
    List<ProcessoJudicial> processoJudicialList = processoJudicialRepository.findAll();
    assertThat(processoJudicialList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  public void checkNumeroIsRequired() throws Exception {
    int databaseSizeBeforeTest = processoJudicialRepository.findAll().size();
    // set the field null
    processoJudicial.setNumero(null);

    // Create the ProcessoJudicial, which fails.
    ProcessoJudicialDTO processoJudicialDTO = processoJudicialMapper.toDto(processoJudicial);

    restProcessoJudicialMockMvc
        .perform(post("/api/processo-judicials").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoJudicialDTO)))
        .andExpect(status().isBadRequest());

    List<ProcessoJudicial> processoJudicialList = processoJudicialRepository.findAll();
    assertThat(processoJudicialList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void checkPrazoFinalIsRequired() throws Exception {
    int databaseSizeBeforeTest = processoJudicialRepository.findAll().size();
    // set the field null
    processoJudicial.setPrazoFinal(null);

    // Create the ProcessoJudicial, which fails.
    ProcessoJudicialDTO processoJudicialDTO = processoJudicialMapper.toDto(processoJudicial);

    restProcessoJudicialMockMvc
        .perform(post("/api/processo-judicials").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoJudicialDTO)))
        .andExpect(status().isBadRequest());

    List<ProcessoJudicial> processoJudicialList = processoJudicialRepository.findAll();
    assertThat(processoJudicialList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void checkDtAtribuicaoIsRequired() throws Exception {
    int databaseSizeBeforeTest = processoJudicialRepository.findAll().size();
    // set the field null
    processoJudicial.setDtAtribuicao(null);

    // Create the ProcessoJudicial, which fails.
    ProcessoJudicialDTO processoJudicialDTO = processoJudicialMapper.toDto(processoJudicial);

    restProcessoJudicialMockMvc
        .perform(post("/api/processo-judicials").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoJudicialDTO)))
        .andExpect(status().isBadRequest());

    List<ProcessoJudicial> processoJudicialList = processoJudicialRepository.findAll();
    assertThat(processoJudicialList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void getAllProcessoJudicials() throws Exception {
    // Initialize the database
    processoJudicialRepository.saveAndFlush(processoJudicial);

    // Get all the processoJudicialList
    restProcessoJudicialMockMvc.perform(get("/api/processo-judicials?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.[*].id").value(hasItem(processoJudicial.getId().intValue())))
        .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
        .andExpect(jsonPath("$.[*].prazoFinal").value(hasItem(DEFAULT_PRAZO_FINAL.toString())))
        .andExpect(jsonPath("$.[*].dtAtribuicao").value(hasItem(DEFAULT_DT_ATRIBUICAO.toString())))
        .andExpect(jsonPath("$.[*].dtInicio").value(hasItem(DEFAULT_DT_INICIO.toString())))
        .andExpect(jsonPath("$.[*].dtConclusao").value(hasItem(DEFAULT_DT_CONCLUSAO.toString())));
  }


  @Test
  @Transactional
  public void getProcessoJudicial() throws Exception {
    // Initialize the database
    processoJudicialRepository.saveAndFlush(processoJudicial);

    // Get the processoJudicial
    restProcessoJudicialMockMvc
        .perform(get("/api/processo-judicials/{id}", processoJudicial.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.id").value(processoJudicial.getId().intValue()))
        .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
        .andExpect(jsonPath("$.prazoFinal").value(DEFAULT_PRAZO_FINAL.toString()))
        .andExpect(jsonPath("$.dtAtribuicao").value(DEFAULT_DT_ATRIBUICAO.toString()))
        .andExpect(jsonPath("$.dtInicio").value(DEFAULT_DT_INICIO.toString()))
        .andExpect(jsonPath("$.dtConclusao").value(DEFAULT_DT_CONCLUSAO.toString()));
  }

  @Test
  @Transactional
  public void getNonExistingProcessoJudicial() throws Exception {
    // Get the processoJudicial
    restProcessoJudicialMockMvc.perform(get("/api/processo-judicials/{id}", Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void updateProcessoJudicial() throws Exception {
    // Initialize the database
    processoJudicialRepository.saveAndFlush(processoJudicial);

    int databaseSizeBeforeUpdate = processoJudicialRepository.findAll().size();

    // Update the processoJudicial
    ProcessoJudicial updatedProcessoJudicial =
        processoJudicialRepository.findById(processoJudicial.getId()).get();
    // Disconnect from session so that the updates on updatedProcessoJudicial are not directly saved
    // in db
    em.detach(updatedProcessoJudicial);
    updatedProcessoJudicial.setNumero(UPDATED_NUMERO).setPrazoFinal(UPDATED_PRAZO_FINAL)
        .setDtAtribuicao(UPDATED_DT_ATRIBUICAO).setDtInicio(UPDATED_DT_INICIO)
        .setDtConclusao(UPDATED_DT_CONCLUSAO);
    ProcessoJudicialDTO processoJudicialDTO = processoJudicialMapper.toDto(updatedProcessoJudicial);

    restProcessoJudicialMockMvc
        .perform(put("/api/processo-judicials").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoJudicialDTO)))
        .andExpect(status().isOk());

    // Validate the ProcessoJudicial in the database
    List<ProcessoJudicial> processoJudicialList = processoJudicialRepository.findAll();
    assertThat(processoJudicialList).hasSize(databaseSizeBeforeUpdate);
    ProcessoJudicial testProcessoJudicial =
        processoJudicialList.get(processoJudicialList.size() - 1);
    assertThat(testProcessoJudicial.getNumero()).isEqualTo(UPDATED_NUMERO);
    assertThat(testProcessoJudicial.getPrazoFinal()).isEqualTo(UPDATED_PRAZO_FINAL);
    assertThat(testProcessoJudicial.getDtAtribuicao()).isEqualTo(UPDATED_DT_ATRIBUICAO);
    assertThat(testProcessoJudicial.getDtInicio()).isEqualTo(UPDATED_DT_INICIO);
    assertThat(testProcessoJudicial.getDtConclusao()).isEqualTo(UPDATED_DT_CONCLUSAO);
  }

  @Test
  @Transactional
  public void updateNonExistingProcessoJudicial() throws Exception {
    int databaseSizeBeforeUpdate = processoJudicialRepository.findAll().size();

    // Create the ProcessoJudicial
    ProcessoJudicialDTO processoJudicialDTO = processoJudicialMapper.toDto(processoJudicial);

    // If the entity doesn't have an ID, it will be created instead of just being updated
    restProcessoJudicialMockMvc
        .perform(put("/api/processo-judicials").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoJudicialDTO)))
        .andExpect(status().isBadRequest());

    // Validate the ProcessoJudicial in the database
    List<ProcessoJudicial> processoJudicialList = processoJudicialRepository.findAll();
    assertThat(processoJudicialList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  public void deleteProcessoJudicial() throws Exception {
    // Initialize the database
    processoJudicialRepository.saveAndFlush(processoJudicial);

    int databaseSizeBeforeDelete = processoJudicialRepository.findAll().size();

    // Get the processoJudicial
    restProcessoJudicialMockMvc
        .perform(delete("/api/processo-judicials/{id}", processoJudicial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());

    // Validate the database is empty
    List<ProcessoJudicial> processoJudicialList = processoJudicialRepository.findAll();
    assertThat(processoJudicialList).hasSize(databaseSizeBeforeDelete - 1);
  }

  @Test
  @Transactional
  public void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProcessoJudicial.class);
    ProcessoJudicial processoJudicial1 = new ProcessoJudicial();
    processoJudicial1.setId(1L);
    ProcessoJudicial processoJudicial2 = new ProcessoJudicial();
    processoJudicial2.setId(processoJudicial1.getId());
    assertThat(processoJudicial1).isEqualTo(processoJudicial2);
    processoJudicial2.setId(2L);
    assertThat(processoJudicial1).isNotEqualTo(processoJudicial2);
    processoJudicial1.setId(null);
    assertThat(processoJudicial1).isNotEqualTo(processoJudicial2);
  }

  @Test
  @Transactional
  public void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProcessoJudicialDTO.class);
    ProcessoJudicialDTO processoJudicialDTO1 = new ProcessoJudicialDTO();
    processoJudicialDTO1.setId(1L);
    ProcessoJudicialDTO processoJudicialDTO2 = new ProcessoJudicialDTO();
    assertThat(processoJudicialDTO1).isNotEqualTo(processoJudicialDTO2);
    processoJudicialDTO2.setId(processoJudicialDTO1.getId());
    assertThat(processoJudicialDTO1).isEqualTo(processoJudicialDTO2);
    processoJudicialDTO2.setId(2L);
    assertThat(processoJudicialDTO1).isNotEqualTo(processoJudicialDTO2);
    processoJudicialDTO1.setId(null);
    assertThat(processoJudicialDTO1).isNotEqualTo(processoJudicialDTO2);
  }

  @Test
  @Transactional
  public void testEntityFromId() {
    assertThat(processoJudicialMapper.fromId(42L).getId()).isEqualTo(42);
    assertThat(processoJudicialMapper.fromId(null)).isNull();
  }
}
