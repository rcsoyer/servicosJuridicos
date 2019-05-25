package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    
    Page<CoordenacaoJuridicaDTO> findByParams(CoordenacaoJuridicaDTO dto, Pageable pageable);
    
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
