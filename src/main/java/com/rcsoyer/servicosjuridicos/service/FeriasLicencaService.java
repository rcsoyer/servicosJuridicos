package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FeriasLicenca.
 */
public interface FeriasLicencaService {
    
    /**
     * Save a feriasLicenca.
     *
     * @param feriasLicencaDTO the entity to save
     * @return the persisted entity
     */
    FeriasLicencaDTO save(FeriasLicencaDTO feriasLicencaDTO);
    
    /**
     * Get all the feriasLicencas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FeriasLicencaDTO> findAll(Pageable pageable);
    
    /**
     * Get the "id" feriasLicenca.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FeriasLicencaDTO> findOne(Long id);
    
    /**
     * Delete the "id" feriasLicenca.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
