package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Advogado.
 */
public interface AdvogadoService {
    
    /**
     * Save a advogado.
     *
     * @param advogadoDTO the entity to save
     * @return the persisted entity
     */
    AdvogadoDTO save(AdvogadoDTO advogadoDTO);
    
    /**
     * Get all the advogados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdvogadoDTO> findAll(Pageable pageable);
    
    /**
     * Get the "id" advogado.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AdvogadoDTO> findOne(Long id);
    
    /**
     * Delete the "id" advogado.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    Page<AdvogadoDTO> findByParams(AdvogadoDTO dto, Pageable pageable);
}
