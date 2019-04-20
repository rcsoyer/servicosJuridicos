package com.rcsoyer.servicosjuridicos.service.impl;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.repository.AdvogadoRepository;
import com.rcsoyer.servicosjuridicos.repository.processo.ProcessoJudicialRepository;
import com.rcsoyer.servicosjuridicos.service.ProcessoJudicialService;
import com.rcsoyer.servicosjuridicos.service.dto.ProcessoJudicialDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.ProcessoJudicialMapper;
import io.vavr.control.Option;


/**
 * Service Implementation for managing ProcessoJudicial.
 */
@Service
@Transactional
public class ProcessoJudicialServiceImpl implements ProcessoJudicialService {

  private final ProcessoJudicialMapper mapper;
  private final ProcessoJudicialRepository repository;
  private final AdvogadoRepository advogadoRepository;
  private final Logger log = LoggerFactory.getLogger(ProcessoJudicialServiceImpl.class);

  public ProcessoJudicialServiceImpl(ProcessoJudicialRepository processoJudicialRepository,
      ProcessoJudicialMapper processoJudicialMapper, AdvogadoRepository advogadoRepository) {
    this.mapper = processoJudicialMapper;
    this.repository = processoJudicialRepository;
    this.advogadoRepository = advogadoRepository;
  }

  /**
   * Save a processoJudicial.
   *
   * @param dto the entity to save
   * @return the persisted entity
   */
  @Override
  public ProcessoJudicialDTO save(ProcessoJudicialDTO dto) {
    log.debug("Request to save ProcessoJudicial : {}", dto);
    Function<ProcessoJudicialDTO, ProcessoJudicial> toEntity = mapper::toEntity;
    return toEntity.andThen(distribuir())
                   .andThen(repository::save)
                   .andThen(mapper::toDto)
                   .apply(dto);
  }

  /**
   * Get all the processoJudicials.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<ProcessoJudicialDTO> findAll(Pageable pageable) {
    log.debug("Request to get all ProcessoJudicials");
    return repository.findAll(pageable).map(mapper::toDto);
  }

  /**
   * Get one processoJudicial by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public ProcessoJudicialDTO findOne(Long id) {
    log.debug("Request to get ProcessoJudicial : {}", id);
    /*return advogadoRepository.findById(id)
            .map(advogadoMapper::toDto);*/
    return null;
  }

  /**
   * Delete the processoJudicial by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete ProcessoJudicial : {}", id);
    repository.deleteById(id);
  }
  
  /**
   * Verificar a lógica desse método, acho que está sendo redundante. Se não estiver então não estou
   * usando bons nomes para os métodos
   * 
   * @return
   */
  private UnaryOperator<ProcessoJudicial> distribuir() {
    return processo -> Option.of(processo)
                             .filter(ProcessoJudicial::foiDistribuido)
                             .peek(this::verificarTrocouAdvogado)
                             .onEmpty(verificarAtribuirAdvogado(processo))
                             .getOrElse(processo);
  }

  private void verificarTrocouAdvogado(ProcessoJudicial processo) {
     // ProcessoJudicial consultadoBD = repository.findById(processo.getId());
      //processo.verificarTrocouAdvogado(consultadoBD);
  }

  private Runnable verificarAtribuirAdvogado(final ProcessoJudicial processo) {
    return () -> Optional.of(processo)
                         .filter(ProcessoJudicial::naoFoiAtribuidoAdvogado)
                         .ifPresent(atribuirAdvogado());
  }

  // TODO Aqui esta sendo só um teste. ainda necessário aplicar as regras reais
  private Consumer<ProcessoJudicial> atribuirAdvogado() {
    return processo -> {
      Advogado advogado = advogadoRepository.findAll().get(0);
      processo.setAdvogado(advogado);
    };
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProcessoJudicialDTO> findByParams(final ProcessoJudicialDTO dto,
      final Pageable pageable) {
    log.debug("Request to get Processos Judiciais by params");
    ProcessoJudicial processo = mapper.toEntity(dto);
    return repository.query(processo, pageable).map(mapper::toDto);
  }

  @Override
  public ProcessoJudicialDTO create(ProcessoJudicialDTO processoJudicialDTO) {
    return null;
  }

  @Override
  public ProcessoJudicialDTO update(ProcessoJudicialDTO processoJudicialDTO) {
    return null;
  }
}
