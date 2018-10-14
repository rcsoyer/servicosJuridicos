package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.repository.coordenacao.CoordenacaoJuridicaRepository;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.CoordenacaoJuridicaMapper;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CoordenacaoJuridica.
 */
@Service
@Transactional
public class CoordenacaoJuridicaServiceImpl implements CoordenacaoJuridicaService {

  private final CoordenacaoJuridicaMapper mapper;
  private final CoordenacaoJuridicaRepository repository;
  private final Logger log = LoggerFactory.getLogger(CoordenacaoJuridicaServiceImpl.class);

  public CoordenacaoJuridicaServiceImpl(CoordenacaoJuridicaRepository coordenacaoJuridicaRepository,
      CoordenacaoJuridicaMapper coordenacaoJuridicaMapper) {
    this.mapper = coordenacaoJuridicaMapper;
    this.repository = coordenacaoJuridicaRepository;
  }

  /**
   * Save a coordenacaoJuridica.
   *
   * @param coordenacaoJuridicaDTO the entity to save
   * @return the persisted entity
   */
  @Override
  public CoordenacaoJuridicaDTO save(CoordenacaoJuridicaDTO coordenacaoJuridicaDTO) {
    log.debug("Request to save CoordenacaoJuridica : {}", coordenacaoJuridicaDTO);
    Function<CoordenacaoJuridicaDTO, CoordenacaoJuridica> toEntity = mapper::toEntity;
    UnaryOperator<CoordenacaoJuridica> save = repository::save;
    Function<CoordenacaoJuridica, CoordenacaoJuridicaDTO> toDto = mapper::toDto;
    return toEntity.andThen(save)
                   .andThen(toDto)
                   .apply(coordenacaoJuridicaDTO);
  }

  /**
   * Get all the coordenacoesJuridicas.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<CoordenacaoJuridicaDTO> findAll(Pageable pageable) {
    log.debug("Request to get all CoordenacaoJuridicas");
    return repository.findAll(pageable)
                     .map(mapper::toDto);
  }

  /**
   * Get one coordenacaoJuridica by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<CoordenacaoJuridicaDTO> findOne(Long id) {
    log.debug("Request to get CoordenacaoJuridica : {}", id);
    return repository.findById(id)
                     .map(mapper::toDto);
  }

  /**
   * Delete the coordenacaoJuridica by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete CoordenacaoJuridica : {}", id);
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CoordenacaoJuridicaDTO> findByParams(final CoordenacaoJuridicaDTO dto,
      final Pageable pageable) {
    Function<CoordenacaoJuridicaDTO, CoordenacaoJuridica> toEntity = mapper::toEntity;
    Function<CoordenacaoJuridica, Page<CoordenacaoJuridica>> query = repository.query(pageable);
    Function<Page<CoordenacaoJuridica>, Page<CoordenacaoJuridicaDTO>> toPageDTO =
        page -> page.map(mapper::toDto);
    return toEntity.andThen(query)
                   .andThen(toPageDTO)
                   .apply(dto);
  }

  @Override
  public Page<CoordenacaoJuridicaDTO> findAllWithEagerRelationships(Pageable pageable) {
    return repository.findAllWithEagerRelationships(pageable)
                     .map(mapper::toDto);
  }
}
