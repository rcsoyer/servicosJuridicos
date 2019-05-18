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
    
    /**
     * Gets a page of {@link AdvogadoDTO} matching the given params and pagination information
     */
    Page<AdvogadoDTO> seekByParams(AdvogadoDTO dto, Pageable pageable);
}
