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
import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.repository.modalidade.ModalidadeRepository;
import com.rcsoyer.servicosjuridicos.service.ModalidadeService;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.ModalidadeMapper;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ModalidadeResource REST controller.
 *
 * @see ModalidadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
public class ModalidadeResourceIntTest {

  private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
  private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

  @Autowired
  private ModalidadeRepository modalidadeRepository;


  @Autowired
  private ModalidadeMapper modalidadeMapper;


  @Autowired
  private ModalidadeService modalidadeService;

  @Autowired
  private MappingJackson2HttpMessageConverter jacksonMessageConverter;

  @Autowired
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

  @Autowired
  private ExceptionTranslator exceptionTranslator;

  @Autowired
  private EntityManager em;

  private MockMvc restModalidadeMockMvc;

  private Modalidade modalidade;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    final ModalidadeResource modalidadeResource = new ModalidadeResource(modalidadeService);
    this.restModalidadeMockMvc = MockMvcBuilders.standaloneSetup(modalidadeResource)
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
  public static Modalidade createEntity(EntityManager em) {
    Modalidade modalidade = new Modalidade().setDescricao(DEFAULT_DESCRICAO);
    return modalidade;
  }

  @Before
  public void initTest() {
    modalidade = createEntity(em);
  }

  @Test
  @Transactional
  public void createModalidade() throws Exception {
    int databaseSizeBeforeCreate = modalidadeRepository.findAll().size();

    // Create the Modalidade
    ModalidadeDTO modalidadeDTO = modalidadeMapper.toDto(modalidade);
    restModalidadeMockMvc
        .perform(post("/api/modalidades").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeDTO)))
        .andExpect(status().isCreated());

    // Validate the Modalidade in the database
    List<Modalidade> modalidadeList = modalidadeRepository.findAll();
    assertThat(modalidadeList).hasSize(databaseSizeBeforeCreate + 1);
    Modalidade testModalidade = modalidadeList.get(modalidadeList.size() - 1);
    assertThat(testModalidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
  }

  @Test
  @Transactional
  public void createModalidadeWithExistingId() throws Exception {
    int databaseSizeBeforeCreate = modalidadeRepository.findAll().size();

    // Create the Modalidade with an existing ID
    modalidade.setId(1L);
    ModalidadeDTO modalidadeDTO = modalidadeMapper.toDto(modalidade);

    // An entity with an existing ID cannot be created, so this API call must fail
    restModalidadeMockMvc
        .perform(post("/api/modalidades").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeDTO)))
        .andExpect(status().isBadRequest());

    // Validate the Modalidade in the database
    List<Modalidade> modalidadeList = modalidadeRepository.findAll();
    assertThat(modalidadeList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  public void checkDescricaoIsRequired() throws Exception {
    int databaseSizeBeforeTest = modalidadeRepository.findAll().size();
    // set the field null
    modalidade.setDescricao(null);

    // Create the Modalidade, which fails.
    ModalidadeDTO modalidadeDTO = modalidadeMapper.toDto(modalidade);

    restModalidadeMockMvc
        .perform(post("/api/modalidades").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeDTO)))
        .andExpect(status().isBadRequest());

    List<Modalidade> modalidadeList = modalidadeRepository.findAll();
    assertThat(modalidadeList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void getAllModalidades() throws Exception {
    // Initialize the database
    modalidadeRepository.saveAndFlush(modalidade);

    // Get all the modalidadeList
    restModalidadeMockMvc.perform(get("/api/modalidades?sort=id,desc")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.[*].id").value(hasItem(modalidade.getId().intValue())))
        .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
  }


  @Test
  @Transactional
  public void getModalidade() throws Exception {
    // Initialize the database
    modalidadeRepository.saveAndFlush(modalidade);

    // Get the modalidade
    restModalidadeMockMvc.perform(get("/api/modalidades/{id}", modalidade.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.id").value(modalidade.getId().intValue()))
        .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
  }

  @Test
  @Transactional
  public void getNonExistingModalidade() throws Exception {
    // Get the modalidade
    restModalidadeMockMvc.perform(get("/api/modalidades/{id}", Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void updateModalidade() throws Exception {
    // Initialize the database
    modalidadeRepository.saveAndFlush(modalidade);

    int databaseSizeBeforeUpdate = modalidadeRepository.findAll().size();

    // Update the modalidade
    Modalidade updatedModalidade = modalidadeRepository.findById(modalidade.getId()).get();
    // Disconnect from session so that the updates on updatedModalidade are not directly saved in db
    em.detach(updatedModalidade);
    updatedModalidade.setDescricao(UPDATED_DESCRICAO);
    ModalidadeDTO modalidadeDTO = modalidadeMapper.toDto(updatedModalidade);

    restModalidadeMockMvc
        .perform(put("/api/modalidades").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeDTO)))
        .andExpect(status().isOk());

    // Validate the Modalidade in the database
    List<Modalidade> modalidadeList = modalidadeRepository.findAll();
    assertThat(modalidadeList).hasSize(databaseSizeBeforeUpdate);
    Modalidade testModalidade = modalidadeList.get(modalidadeList.size() - 1);
    assertThat(testModalidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
  }

  @Test
  @Transactional
  public void updateNonExistingModalidade() throws Exception {
    int databaseSizeBeforeUpdate = modalidadeRepository.findAll().size();

    // Create the Modalidade
    ModalidadeDTO modalidadeDTO = modalidadeMapper.toDto(modalidade);

    // If the entity doesn't have an ID, it will be created instead of just being updated
    restModalidadeMockMvc
        .perform(put("/api/modalidades").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeDTO)))
        .andExpect(status().isBadRequest());

    // Validate the Modalidade in the database
    List<Modalidade> modalidadeList = modalidadeRepository.findAll();
    assertThat(modalidadeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  public void deleteModalidade() throws Exception {
    // Initialize the database
    modalidadeRepository.saveAndFlush(modalidade);

    int databaseSizeBeforeDelete = modalidadeRepository.findAll().size();

    // Get the modalidade
    restModalidadeMockMvc.perform(
        delete("/api/modalidades/{id}", modalidade.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());

    // Validate the database is empty
    List<Modalidade> modalidadeList = modalidadeRepository.findAll();
    assertThat(modalidadeList).hasSize(databaseSizeBeforeDelete - 1);
  }

  @Test
  @Transactional
  public void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Modalidade.class);
    Modalidade modalidade1 = new Modalidade();
    modalidade1.setId(1L);
    Modalidade modalidade2 = new Modalidade();
    modalidade2.setId(modalidade1.getId());
    assertThat(modalidade1).isEqualTo(modalidade2);
    modalidade2.setId(2L);
    assertThat(modalidade1).isNotEqualTo(modalidade2);
    modalidade1.setId(null);
    assertThat(modalidade1).isNotEqualTo(modalidade2);
  }

  @Test
  @Transactional
  public void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ModalidadeDTO.class);
    ModalidadeDTO modalidadeDTO1 = new ModalidadeDTO();
    modalidadeDTO1.setId(1L);
    ModalidadeDTO modalidadeDTO2 = new ModalidadeDTO();
    assertThat(modalidadeDTO1).isNotEqualTo(modalidadeDTO2);
    modalidadeDTO2.setId(modalidadeDTO1.getId());
    assertThat(modalidadeDTO1).isEqualTo(modalidadeDTO2);
    modalidadeDTO2.setId(2L);
    assertThat(modalidadeDTO1).isNotEqualTo(modalidadeDTO2);
    modalidadeDTO1.setId(null);
    assertThat(modalidadeDTO1).isNotEqualTo(modalidadeDTO2);
  }

  @Test
  @Transactional
  public void testEntityFromId() {
    assertThat(modalidadeMapper.fromId(42L).getId()).isEqualTo(42);
    assertThat(modalidadeMapper.fromId(null)).isNull();
  }
}
