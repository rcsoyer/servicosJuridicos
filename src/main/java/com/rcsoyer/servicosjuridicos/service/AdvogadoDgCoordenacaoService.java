package com.rcsoyer.servicosjuridicos.service;

import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdvogadoDgCoordenacao.
 */
public interface AdvogadoDgCoordenacaoService {
    
    /**
     * Save an AdvogAdvogadoDgCoordenacao applying the following business rules: count the AdvogadoDgCoordenacao table
     * to check the number of advogados registered with the passed digitoDupla. The max number of advogados using the
     * same digitoDupla at one time is always 2.
     *
     * @throws IllegalStateException if there's already 2 advogados using the passed digitoDupla
     */
    AdvogadoDgCoordenacaoDTO save(AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO);
    
    /**
     * Get the "id" advogadoDgCoordenacao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AdvogadoDgCoordenacaoDTO> findOne(Long id);
    
    /**
     * Delete the "id" advogadoDgCoordenacao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Will get a page of Advogados matching the given search parameters and pagination information
     */
    Page<AdvogadoDgCoordenacaoDTO> seekByParams(AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO, Pageable pageable);
}
