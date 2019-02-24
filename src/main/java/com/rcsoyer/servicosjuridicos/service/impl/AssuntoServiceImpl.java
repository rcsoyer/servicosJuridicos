package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.repository.assunto.AssuntoRepository;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AssuntoMapper;
import java.util.Optional;
import java.util.function.Function;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssuntoServiceImpl implements AssuntoService {
    
    private final AssuntoMapper mapper;
    
    private final AssuntoRepository repository;
    
    private final Logger log = LoggerFactory.getLogger(AssuntoServiceImpl.class);
    
    public AssuntoServiceImpl(AssuntoRepository assuntoRepository, AssuntoMapper assuntoMapper) {
        this.mapper = assuntoMapper;
        this.repository = assuntoRepository;
    }
    
    /**
     * Save a assunto.
     *
     * @param dto the entity to save
     * @return the persisted entity
     */
    @Override
    public AssuntoDTO save(AssuntoDTO dto) {
        log.debug("Request to save Assunto : {}", dto);
        Function<AssuntoDTO, Assunto> toEntity = mapper::toEntity;
        return toEntity.andThen(repository::save).andThen(mapper::toDto).apply(dto);
    }
    
    /**
     * Get all the assuntos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssuntoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assuntos");
        return repository.findAll(pageable).map(mapper::toDto);
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
        return repository.findById(id).map(mapper::toDto);
    }
    
    /**
     * Delete the assunto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assunto : {}", id);
        try {
            repository.deleteById(id);
        } catch (ConstraintViolationException dataIntegrityViolation) {
            throw new IllegalStateException("Nao pode", dataIntegrityViolation);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<AssuntoDTO> findByParams(final AssuntoDTO dto, final Pageable pageable) {
        log.debug("Request to get Processos Judiciais by params");
        Function<AssuntoDTO, Assunto> toEntity = mapper::toEntity;
        Function<Assunto, Page<Assunto>> query = repository.query(pageable);
        Function<Page<Assunto>, Page<AssuntoDTO>> toPageDTO = page -> page.map(mapper::toDto);
        return toEntity.andThen(query)
            .andThen(toPageDTO)
            .apply(dto);
    }
}
