package com.rcsoyer.servicosjuridicos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;

/**
 * Service Interface for managing Assunto.
 */
public interface AssuntoService {

  /**
   * Save a assunto.
   *
   * @param assuntoDTO the entity to save
   * @return the persisted entity
   */
  AssuntoDTO save(AssuntoDTO assuntoDTO);

  /**
   * Get all the assuntos.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<AssuntoDTO> findAll(Pageable pageable);

  /**
   * Get the "id" assunto.
   *
   * @param id the id of the entity
   * @return the entity
   */
  AssuntoDTO findOne(Long id);

  /**
   * Delete the "id" assunto.
   *
   * @param id the id of the entity
   */
  void delete(Long id);

  Page<AssuntoDTO> findByParams(AssuntoDTO dto, Pageable pageable);
}
