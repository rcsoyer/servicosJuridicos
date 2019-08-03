package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.repository.ModalidadeRepository;
import com.rcsoyer.servicosjuridicos.service.ModalidadeService;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.ModalidadeMapper;
import io.vavr.Function1;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ModalidadeServiceImpl implements ModalidadeService {
    
    private final ModalidadeMapper mapper;
    private final ModalidadeRepository repository;
    
    public ModalidadeServiceImpl(final ModalidadeRepository modalidadeRepository,
                                 final ModalidadeMapper modalidadeMapper) {
        this.mapper = modalidadeMapper;
        this.repository = modalidadeRepository;
    }
    
    @Override
    public ModalidadeDTO save(final ModalidadeDTO dto) {
        log.debug("Create modalidade={}", dto);
        return Function1.of(mapper::toEntity)
                        .andThen(repository::save)
                        .andThen(mapper::toDto)
                        .apply(dto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ModalidadeDTO> findOne(Long id) {
        log.debug("Seek for modalidade={}", id);
        return repository.findById(id)
                         .map(mapper::toDto);
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Delete modalidade={}", id);
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ModalidadeDTO> findByParams(final ModalidadeDTO dto, final Pageable pageable) {
        log.debug("Seek modalidades by params searchParams={} and pageable={}", dto, pageable);
        return repository.query(mapper.toEntity(dto), pageable)
                         .map(mapper::toDto);
    }
}
