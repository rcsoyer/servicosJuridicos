package com.rcsoyer.servicosjuridicos.service.impl;

import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.repository.advogado.AdvogadoRepository;
import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoMapper;


/**
 * Service Implementation for managing Advogado.
 */
@Service
@Transactional
public class AdvogadoServiceImpl implements AdvogadoService {

  private final AdvogadoMapper mapper;
  private final AdvogadoRepository repository;
  private final Logger log = LoggerFactory.getLogger(AdvogadoServiceImpl.class);

  public AdvogadoServiceImpl(AdvogadoRepository advogadoRepository, AdvogadoMapper advogadoMapper) {
    this.mapper = advogadoMapper;
    this.repository = advogadoRepository;
  }

  /**
   * Save a advogado.
   *
   * @param advogadoDTO the entity to save
   * @return the persisted entity
   */
  @Override
  public AdvogadoDTO save(AdvogadoDTO advogadoDTO) {
    log.debug("Request to save Advogado : {}", advogadoDTO);
    Function<AdvogadoDTO, Advogado> toEntity = mapper::toEntity;
    return toEntity.andThen(repository::save).andThen(mapper::toDto).apply(advogadoDTO);
  }


  /**
   * Get all the advogados.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<AdvogadoDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Advogados");
    return repository.findAll(pageable).map(mapper::toDto);
  }

  /**
   * Get one advogado by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public AdvogadoDTO findOne(Long id) {
    log.debug("Request to get Advogado : {}", id);
    /*return advogadoRepository.findById(id)
        .map(advogadoMapper::toDto);*/
    return null;
  }

  /**
   * Delete the advogado by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Advogado : {}", id);
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AdvogadoDTO> findByParams(final AdvogadoDTO dto, final Pageable pageable) {
    log.debug("AdvogadoService mehtod to get Processos Judiciais by params");
    Function<AdvogadoDTO, Advogado> toEntity = mapper::toEntity;
    Function<Page<Advogado>, Page<AdvogadoDTO>> toPageDto = pageEntity -> pageEntity.map(mapper::toDto);
    Function<Advogado, Page<Advogado>> query = repository.query(pageable);
    return toEntity.andThen(query)
                   .andThen(toPageDto)
                   .apply(dto);
  }
}
