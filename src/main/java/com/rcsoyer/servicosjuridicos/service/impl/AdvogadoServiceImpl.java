package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.repository.AdvogadoRepository;
import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoMapper;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Advogado.
 */
@Slf4j
@Service
@Transactional
public class AdvogadoServiceImpl implements AdvogadoService {
    
    private final AdvogadoMapper mapper;
    private final AdvogadoRepository repository;
    
    public AdvogadoServiceImpl(final AdvogadoRepository advogadoRepository, final AdvogadoMapper advogadoMapper) {
        this.mapper = advogadoMapper;
        this.repository = advogadoRepository;
    }
    
    @Override
    public AdvogadoDTO save(final AdvogadoDTO advogadoDTO) {
        log.info("Call to service layer to save Advogado: {}", advogadoDTO);
        Function<AdvogadoDTO, Advogado> toEntity = mapper::toEntity;
        return toEntity.andThen(repository::save)
                       .andThen(mapper::toDto)
                       .apply(advogadoDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<AdvogadoDTO> findOne(Long id) {
        log.debug("Call to service layer to find an Advogado by id={}", id);
        return repository.findById(id)
                         .map(mapper::toDto);
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Call to service layer to delete Advogado by id={}", id);
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<AdvogadoDTO> seekByParams(final AdvogadoDTO dto, final Pageable pageable) {
        log.debug("Call to service layer to get a Page of Advogados by the given params: queryParams={}, pagination={}",
                  dto, pageable);
        return repository.query(mapper.toEntity(dto), pageable)
                         .map(mapper::toDto);
    }
}
