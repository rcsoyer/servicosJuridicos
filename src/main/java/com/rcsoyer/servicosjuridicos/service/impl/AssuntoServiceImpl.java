package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.repository.AssuntoRepository;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AssuntoMapper;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class AssuntoServiceImpl implements AssuntoService {
    
    private final AssuntoMapper mapper;
    private final AssuntoRepository repository;
    
    public AssuntoServiceImpl(final AssuntoRepository assuntoRepository, final AssuntoMapper assuntoMapper) {
        this.mapper = assuntoMapper;
        this.repository = assuntoRepository;
    }
    
    /**
     * Save a assunto. ps: read from the right to the left
     *
     * @param dto the entity to save
     * @return the persisted entity
     */
    @Override
    public AssuntoDTO save(final AssuntoDTO dto) {
        log.debug("Request to save Assunto : {}", dto);
        Function<AssuntoDTO, Assunto> toEntity = mapper::toEntity;
        return toEntity.andThen(repository::save)
                       .andThen(mapper::toDto)
                       .apply(dto);
    }
    
    /**
     * Get all the assuntos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssuntoDTO> findAll(final Pageable pageable) {
        log.debug("Request to get all Assuntos");
        return repository.findAll(pageable)
                         .map(mapper::toDto);
    }
    
    /**
     * Get one assunto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AssuntoDTO> findOne(Long id) {
        log.debug("Request to get Assunto : {}", id);
        return repository.findById(id)
                         .map(mapper::toDto);
    }
    
    /**
     * Delete the assunto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    /**
     * Get a page of Assunto based on the param values from the dto and the pagination information
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssuntoDTO> seekByParams(final AssuntoDTO dto, final Pageable pageable) {
        log.debug("Passing throw the seekParams to get Assuntos page by params: {}, {} ", dto, pageable);
        return repository.findByAssunto(mapper.toEntity(dto), pageable)
                         .map(mapper::toDto);
    }
}
