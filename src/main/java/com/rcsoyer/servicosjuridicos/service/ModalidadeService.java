package com.rcsoyer.servicosjuridicos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;

/**
 * Service Interface for managing Modalidade.
 */
public interface ModalidadeService {

  /**
   * Save a modalidade.
   *
   * @param modalidadeDTO the entity to save
   * @return the persisted entity
   */
  ModalidadeDTO save(ModalidadeDTO modalidadeDTO);

  /**
   * Get all the modalidades.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<ModalidadeDTO> findAll(Pageable pageable);

  /**
   * Get the "id" modalidade.
   *
   * @param id the id of the entity
   * @return the entity
   */
  ModalidadeDTO findOne(Long id);

  /**
   * Delete the "id" modalidade.
   *
   * @param id the id of the entity
   */
  void delete(Long id);

  Page<ModalidadeDTO> findByParams(final ModalidadeDTO dto, final Pageable pageable);
}
