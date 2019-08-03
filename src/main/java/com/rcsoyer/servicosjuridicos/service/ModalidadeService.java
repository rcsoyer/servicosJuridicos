package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Get the "id" modalidade.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ModalidadeDTO> findOne(Long id);
    
    /**
     * Delete the "id" modalidade.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    Page<ModalidadeDTO> seekByParams(ModalidadeDTO dto, Pageable pageable);
    
}
