package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.repository.AdvogadoRepository;
import com.rcsoyer.servicosjuridicos.repository.processo.ProcessoJudicialRepository;
import com.rcsoyer.servicosjuridicos.service.ProcessoJudicialService;
import com.rcsoyer.servicosjuridicos.service.dto.ProcessoJudicialDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.ProcessoJudicialMapper;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing ProcessoJudicial.
 */
@Slf4j
@Service
@Transactional
public class ProcessoJudicialServiceImpl implements ProcessoJudicialService {
    
    private final ProcessoJudicialMapper mapper;
    private final ProcessoJudicialRepository repository;
    private final AdvogadoRepository advogadoRepository;
    
    public ProcessoJudicialServiceImpl(final ProcessoJudicialRepository processoJudicialRepository,
                                       final ProcessoJudicialMapper processoJudicialMapper,
                                       final AdvogadoRepository advogadoRepository) {
        this.mapper = processoJudicialMapper;
        this.repository = processoJudicialRepository;
        this.advogadoRepository = advogadoRepository;
    }
    
    @Override
    public ProcessoJudicialDTO save(ProcessoJudicialDTO dto) {
        log.debug("Request to save ProcessoJudicial : {}", dto);
        Function<ProcessoJudicialDTO, ProcessoJudicial> toEntity = mapper::toEntity;
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessoJudicialDTO> findOne(Long id) {
        log.debug("Find  a ProcessoJudicial matching id={}", id);
        return repository.findById(id)
                         .map(mapper::toDto);
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessoJudicial : {}", id);
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ProcessoJudicialDTO> findByParams(final ProcessoJudicialDTO searchParams,
                                                  final Pageable pageable) {
        log.debug("Get a page of processos judiciais matching: searchParams={}, pageable={}", searchParams, pageable);
        return repository.query(mapper.toEntity(searchParams), pageable)
                         .map(mapper::toDto);
    }
    
    private void distribuir(final ProcessoJudicial processo) {
        final int sextoDigito = processo.getSextoDigito();
        
    }
    
}
