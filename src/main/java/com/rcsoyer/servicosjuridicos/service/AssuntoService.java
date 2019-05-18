package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Get the "id" assunto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AssuntoDTO> findOne(Long id);
    
    /**
     * Delete the "id" assunto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Get a page of {@link AssuntoDTO} matching the given params and pagination information
     */
    Page<AssuntoDTO> seekByParams(AssuntoDTO dto, Pageable pageable);
}
