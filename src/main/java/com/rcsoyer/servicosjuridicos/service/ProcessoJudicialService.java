package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.service.dto.ProcessoJudicialDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProcessoJudicial.
 */
public interface ProcessoJudicialService {
    
    /**
     * Save a processoJudicial.
     *
     * @param processoJudicial the entity to save
     * @return the persisted entity
     */
    ProcessoJudicialDTO save(ProcessoJudicialDTO processoJudicial);
    
    /**
     * Get the "id" processoJudicial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProcessoJudicialDTO> findOne(Long id);
    
    /**
     * Seek the database for processos judiciais that matches the  given  search parameters
     */
    Page<ProcessoJudicialDTO> findByParams(ProcessoJudicialDTO searchParams, Pageable pageable);
    
    /**
     * Delete the "id" processoJudicial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
}
