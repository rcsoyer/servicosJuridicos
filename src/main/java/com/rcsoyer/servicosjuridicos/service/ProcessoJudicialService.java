package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.service.dto.ProcessoJudicialDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProcessoJudicial.
 */
public interface ProcessoJudicialService {
    
    /**
     * Save a processoJudicial.
     *
     * @param processoJudicialDTO the entity to save
     * @return the persisted entity
     */
    ProcessoJudicialDTO save(ProcessoJudicialDTO processoJudicialDTO);
    
    ProcessoJudicialDTO create(ProcessoJudicialDTO processoJudicialDTO);
    
    ProcessoJudicialDTO update(ProcessoJudicialDTO processoJudicialDTO);
    
    /**
     * Get all the processoJudicials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProcessoJudicialDTO> findAll(Pageable pageable);
    
    /**
     * Get the "id" processoJudicial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProcessoJudicialDTO findOne(Long id);
    
    Page<ProcessoJudicialDTO> findByParams(ProcessoJudicialDTO dto, Pageable pageable);
    
    /**
     * Delete the "id" processoJudicial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
