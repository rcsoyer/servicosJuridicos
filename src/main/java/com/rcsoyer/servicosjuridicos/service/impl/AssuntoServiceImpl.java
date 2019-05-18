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
    
    @Override
    public AssuntoDTO save(final AssuntoDTO dto) {
        log.debug("Call to service layer to save Assunto: {}", dto);
        Function<AssuntoDTO, Assunto> toEntity = mapper::toEntity;
        return toEntity.andThen(repository::save)
                       .andThen(mapper::toDto)
                       .apply(dto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<AssuntoDTO> findOne(Long id) {
        log.debug("Call to service layer to find an Assunto by id={}", id);
        return repository.findById(id)
                         .map(mapper::toDto);
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Call to service layer to delete Assunto by id={}", id);
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<AssuntoDTO> seekByParams(final AssuntoDTO dto, final Pageable pageable) {
        log.debug("Passing throw the seekParams to get Assuntos page by params: {}, {} ", dto, pageable);
        return repository.findByAssunto(mapper.toEntity(dto), pageable)
                         .map(mapper::toDto);
    }
}
