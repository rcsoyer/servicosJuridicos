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
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.repository.advogado.AdvogadoRepository;
import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoMapper;
import com.rcsoyer.servicosjuridicos.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the AdvogadoResource REST controller.
 *
 * @see AdvogadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicosJuridicosApp.class)
public class AdvogadoResourceIntTest {

  private static final String DEFAULT_NOME = "AAAAAAAAAA";
  private static final String UPDATED_NOME = "BBBBBBBBBB";

  private static final String DEFAULT_CPF = "AAAAAAAAAAA";
  private static final String UPDATED_CPF = "BBBBBBBBBBB";

  private static final Integer DEFAULT_RAMAL = 1;
  private static final Integer UPDATED_RAMAL = 2;

  @Autowired
  private AdvogadoRepository advogadoRepository;


  @Autowired
  private AdvogadoMapper advogadoMapper;


  @Autowired
  private AdvogadoService advogadoService;

  @Autowired
  private MappingJackson2HttpMessageConverter jacksonMessageConverter;

  @Autowired
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

  @Autowired
  private ExceptionTranslator exceptionTranslator;

  @Autowired
  private EntityManager em;

  private MockMvc restAdvogadoMockMvc;

  private Advogado advogado;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    final AdvogadoResource advogadoResource = new AdvogadoResource(advogadoService);
    this.restAdvogadoMockMvc = MockMvcBuilders.standaloneSetup(advogadoResource)
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
  public static Advogado createEntity(EntityManager em) {
    Advogado advogado =
        new Advogado().setNome(DEFAULT_NOME).setCpf(DEFAULT_CPF).setRamal(DEFAULT_RAMAL);
    return advogado;
  }

  @Before
  public void initTest() {
    advogado = createEntity(em);
  }

  @Test
  @Transactional
  public void createAdvogado() throws Exception {
    int databaseSizeBeforeCreate = advogadoRepository.findAll().size();

    // Create the Advogado
    AdvogadoDTO advogadoDTO = advogadoMapper.toDto(advogado);
    restAdvogadoMockMvc
        .perform(post("/api/advogados").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advogadoDTO)))
        .andExpect(status().isCreated());

    // Validate the Advogado in the database
    List<Advogado> advogadoList = advogadoRepository.findAll();
    assertThat(advogadoList).hasSize(databaseSizeBeforeCreate + 1);
    Advogado testAdvogado = advogadoList.get(advogadoList.size() - 1);
    assertThat(testAdvogado.getNome()).isEqualTo(DEFAULT_NOME);
    assertThat(testAdvogado.getCpf()).isEqualTo(DEFAULT_CPF);
    assertThat(testAdvogado.getRamal()).isEqualTo(DEFAULT_RAMAL);
  }

  @Test
  @Transactional
  public void createAdvogadoWithExistingId() throws Exception {
    int databaseSizeBeforeCreate = advogadoRepository.findAll().size();

    // Create the Advogado with an existing ID
    advogado.setId(1L);
    AdvogadoDTO advogadoDTO = advogadoMapper.toDto(advogado);

    // An entity with an existing ID cannot be created, so this API call must fail
    restAdvogadoMockMvc
        .perform(post("/api/advogados").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advogadoDTO)))
        .andExpect(status().isBadRequest());

    // Validate the Advogado in the database
    List<Advogado> advogadoList = advogadoRepository.findAll();
    assertThat(advogadoList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  public void checkNomeIsRequired() throws Exception {
    int databaseSizeBeforeTest = advogadoRepository.findAll().size();
    // set the field null
    advogado.setNome(null);

    // Create the Advogado, which fails.
    AdvogadoDTO advogadoDTO = advogadoMapper.toDto(advogado);

    restAdvogadoMockMvc
        .perform(post("/api/advogados").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advogadoDTO)))
        .andExpect(status().isBadRequest());

    List<Advogado> advogadoList = advogadoRepository.findAll();
    assertThat(advogadoList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void checkCpfIsRequired() throws Exception {
    int databaseSizeBeforeTest = advogadoRepository.findAll().size();
    // set the field null
    advogado.setCpf(null);

    // Create the Advogado, which fails.
    AdvogadoDTO advogadoDTO = advogadoMapper.toDto(advogado);

    restAdvogadoMockMvc
        .perform(post("/api/advogados").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advogadoDTO)))
        .andExpect(status().isBadRequest());

    List<Advogado> advogadoList = advogadoRepository.findAll();
    assertThat(advogadoList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void getAllAdvogados() throws Exception {
    // Initialize the database
    advogadoRepository.saveAndFlush(advogado);

    // Get all the advogadoList
    restAdvogadoMockMvc.perform(get("/api/advogados?sort=id,desc")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.[*].id").value(hasItem(advogado.getId().intValue())))
        .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
        .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
        .andExpect(jsonPath("$.[*].ramal").value(hasItem(DEFAULT_RAMAL)));
  }


  @Test
  @Transactional
  public void getAdvogado() throws Exception {
    // Initialize the database
    advogadoRepository.saveAndFlush(advogado);

    // Get the advogado
    restAdvogadoMockMvc.perform(get("/api/advogados/{id}", advogado.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.id").value(advogado.getId().intValue()))
        .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
        .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
        .andExpect(jsonPath("$.ramal").value(DEFAULT_RAMAL));
  }

  @Test
  @Transactional
  public void getNonExistingAdvogado() throws Exception {
    // Get the advogado
    restAdvogadoMockMvc.perform(get("/api/advogados/{id}", Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void updateAdvogado() throws Exception {
    // Initialize the database
    advogadoRepository.saveAndFlush(advogado);

    int databaseSizeBeforeUpdate = advogadoRepository.findAll().size();

    // Update the advogado
    Advogado updatedAdvogado = advogadoRepository.findById(advogado.getId()).get();
    // Disconnect from session so that the updates on updatedAdvogado are not directly saved in db
    em.detach(updatedAdvogado);
    updatedAdvogado.setNome(UPDATED_NOME).setCpf(UPDATED_CPF).setRamal(UPDATED_RAMAL);
    AdvogadoDTO advogadoDTO = advogadoMapper.toDto(updatedAdvogado);

    restAdvogadoMockMvc.perform(put("/api/advogados").contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(advogadoDTO))).andExpect(status().isOk());

    // Validate the Advogado in the database
    List<Advogado> advogadoList = advogadoRepository.findAll();
    assertThat(advogadoList).hasSize(databaseSizeBeforeUpdate);
    Advogado testAdvogado = advogadoList.get(advogadoList.size() - 1);
    assertThat(testAdvogado.getNome()).isEqualTo(UPDATED_NOME);
    assertThat(testAdvogado.getCpf()).isEqualTo(UPDATED_CPF);
    assertThat(testAdvogado.getRamal()).isEqualTo(UPDATED_RAMAL);
  }

  @Test
  @Transactional
  public void updateNonExistingAdvogado() throws Exception {
    int databaseSizeBeforeUpdate = advogadoRepository.findAll().size();

    // Create the Advogado
    AdvogadoDTO advogadoDTO = advogadoMapper.toDto(advogado);

    // If the entity doesn't have an ID, it will be created instead of just being updated
    restAdvogadoMockMvc
        .perform(put("/api/advogados").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advogadoDTO)))
        .andExpect(status().isBadRequest());

    // Validate the Advogado in the database
    List<Advogado> advogadoList = advogadoRepository.findAll();
    assertThat(advogadoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  public void deleteAdvogado() throws Exception {
    // Initialize the database
    advogadoRepository.saveAndFlush(advogado);

    int databaseSizeBeforeDelete = advogadoRepository.findAll().size();

    // Get the advogado
    restAdvogadoMockMvc
        .perform(
            delete("/api/advogados/{id}", advogado.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());

    // Validate the database is empty
    List<Advogado> advogadoList = advogadoRepository.findAll();
    assertThat(advogadoList).hasSize(databaseSizeBeforeDelete - 1);
  }

  @Test
  @Transactional
  public void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Advogado.class);
    Advogado advogado1 = new Advogado();
    advogado1.setId(1L);
    Advogado advogado2 = new Advogado();
    advogado2.setId(advogado1.getId());
    assertThat(advogado1).isEqualTo(advogado2);
    advogado2.setId(2L);
    assertThat(advogado1).isNotEqualTo(advogado2);
    advogado1.setId(null);
    assertThat(advogado1).isNotEqualTo(advogado2);
  }

  @Test
  @Transactional
  public void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(AdvogadoDTO.class);
    AdvogadoDTO advogadoDTO1 = new AdvogadoDTO();
    advogadoDTO1.setId(1L);
    AdvogadoDTO advogadoDTO2 = new AdvogadoDTO();
    assertThat(advogadoDTO1).isNotEqualTo(advogadoDTO2);
    advogadoDTO2.setId(advogadoDTO1.getId());
    assertThat(advogadoDTO1).isEqualTo(advogadoDTO2);
    advogadoDTO2.setId(2L);
    assertThat(advogadoDTO1).isNotEqualTo(advogadoDTO2);
    advogadoDTO1.setId(null);
    assertThat(advogadoDTO1).isNotEqualTo(advogadoDTO2);
  }

  @Test
  @Transactional
  public void testEntityFromId() {
    assertThat(advogadoMapper.fromId(42L).getId()).isEqualTo(42);
    assertThat(advogadoMapper.fromId(null)).isNull();
  }
}
