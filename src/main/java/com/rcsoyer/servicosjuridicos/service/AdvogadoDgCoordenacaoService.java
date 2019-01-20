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
     * Save a advogadoDgCoordenacao.
     *
     * @param advogadoDgCoordenacaoDTO the entity to save
     * @return the persisted entity
     */
    AdvogadoDgCoordenacaoDTO save(AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO);

    /**
     * Get all the advogadoDgCoordenacaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdvogadoDgCoordenacaoDTO> findAll(Pageable pageable);

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
}
