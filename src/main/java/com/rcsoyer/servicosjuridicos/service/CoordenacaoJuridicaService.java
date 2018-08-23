package com.rcsoyer.servicosjuridicos.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;

/**
 * Service Interface for managing CoordenacaoJuridica.
 */
public interface CoordenacaoJuridicaService {

  /**
   * Save a coordenacaoJuridica.
   *
   * @param coordenacaoJuridicaDTO the entity to save
   * @return the persisted entity
   */
  CoordenacaoJuridicaDTO save(CoordenacaoJuridicaDTO coordenacaoJuridicaDTO);

  /**
   * Get all the coordenacaoJuridicas.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<CoordenacaoJuridicaDTO> findAll(Pageable pageable);

  Page<CoordenacaoJuridicaDTO> findByParams(CoordenacaoJuridicaDTO dto, Pageable pageable);

  /**
   * Get all the CoordenacaoJuridica with eager load of many-to-many relationships.
   *
   * @return the list of entities
   */
  Page<CoordenacaoJuridicaDTO> findAllWithEagerRelationships(Pageable pageable);

  /**
   * Get the "id" coordenacaoJuridica.
   *
   * @param id the id of the entity
   * @return the entity
   */
  Optional<CoordenacaoJuridicaDTO> findOne(Long id);

  /**
   * Delete the "id" coordenacaoJuridica.
   *
   * @param id the id of the entity
   */
  void delete(Long id);
}
