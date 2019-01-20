package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.repository.AdvogadoDgCoordenacaoRepository;
import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoDgCoordenacaoMapper;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AdvogadoDgCoordenacao.
 */
@Slf4j
@Service
@Transactional
public class AdvogadoDgCoordenacaoServiceImpl implements AdvogadoDgCoordenacaoService {
    
    private final AdvogadoDgCoordenacaoMapper mapper;
    private final AdvogadoDgCoordenacaoRepository repository;
    
    public AdvogadoDgCoordenacaoServiceImpl(AdvogadoDgCoordenacaoRepository repository,
        AdvogadoDgCoordenacaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    /**
     * Save a advogadoDgCoordenacao.
     *
     * @param dto the entity to save
     * @return the persisted entity
     */
    @Override
    public AdvogadoDgCoordenacaoDTO save(final AdvogadoDgCoordenacaoDTO dto) {
        log.debug("Request to save AdvogadoDgCoordenacao : {}", dto);
        Function<AdvogadoDgCoordenacaoDTO, AdvogadoDgCoordenacao> toEntity = mapper::toEntity;
        return toEntity.andThen(repository::save)
                       .andThen(mapper::toDto)
                       .apply(dto);
    }
    
    /**
     * Get all the advogadoDgCoordenacaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdvogadoDgCoordenacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdvogadoDgCoordenacaos");
        return repository.findAll(pageable)
                         .map(mapper::toDto);
    }
    
    /**
     * Get one advogadoDgCoordenacao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdvogadoDgCoordenacaoDTO> findOne(Long id) {
        log.debug("Request to get AdvogadoDgCoordenacao : {}", id);
       return repository.findById(id)
                        .map(mapper::toDto);
    }
    
    /**
     * Delete the advogadoDgCoordenacao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdvogadoDgCoordenacao : {}", id);
        repository.deleteById(id);
    }
}
