package com.rcsoyer.servicosjuridicos.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.repository.coordenacao.CoordenacaoJuridicaRepository;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.CoordenacaoJuridicaMapper;


/**
 * Service Implementation for managing CoordenacaoJuridica.
 */
@Service
@Transactional
public class CoordenacaoJuridicaServiceImpl implements CoordenacaoJuridicaService {

  private final Logger log = LoggerFactory.getLogger(CoordenacaoJuridicaServiceImpl.class);

  private final CoordenacaoJuridicaRepository repository;

  private final CoordenacaoJuridicaMapper mapper;

  public CoordenacaoJuridicaServiceImpl(CoordenacaoJuridicaRepository coordenacaoJuridicaRepository,
      CoordenacaoJuridicaMapper coordenacaoJuridicaMapper) {
    this.repository = coordenacaoJuridicaRepository;
    this.mapper = coordenacaoJuridicaMapper;
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
    CoordenacaoJuridica coordenacaoJuridica = mapper.toEntity(coordenacaoJuridicaDTO);
    coordenacaoJuridica = repository.save(coordenacaoJuridica);
    return mapper.toDto(coordenacaoJuridica);
  }

  /**
   * Get all the coordenacaoJuridicas.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<CoordenacaoJuridicaDTO> findAll(Pageable pageable) {
    log.debug("Request to get all CoordenacaoJuridicas");
    return repository.findAll(pageable).map(mapper::toDto);
  }

  /**
   * Get one coordenacaoJuridica by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public CoordenacaoJuridicaDTO findOne(Long id) {
    log.debug("Request to get CoordenacaoJuridica : {}", id);
    CoordenacaoJuridica coordenacaoJuridica = repository.findOneWithEagerRelationships(id);
    return mapper.toDto(coordenacaoJuridica);
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
    CoordenacaoJuridica coordenacao = mapper.toEntity(dto);
    return repository.query(coordenacao, pageable).map(mapper::toDto);
  }

  @Override
  public Page<CoordenacaoJuridicaDTO> findAllWithEagerRelationships(CoordenacaoJuridicaDTO dto) {
    return null;
  }
}
