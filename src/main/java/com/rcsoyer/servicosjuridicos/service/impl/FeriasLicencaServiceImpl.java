package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.feriaslicenca.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.repository.FeriasLicencaRepository;
import com.rcsoyer.servicosjuridicos.service.FeriasLicencaService;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.FeriasLicencaMapper;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing FeriasLicenca.
 */
@Slf4j
@Service
@Transactional
public class FeriasLicencaServiceImpl implements FeriasLicencaService {
    
    private final FeriasLicencaMapper mapper;
    private final FeriasLicencaRepository repository;
    
    public FeriasLicencaServiceImpl(final FeriasLicencaMapper feriasLicencaMapper,
                                    final FeriasLicencaRepository feriasLicencaRepository) {
        this.mapper = feriasLicencaMapper;
        this.repository = feriasLicencaRepository;
    }
    
    @Override
    public FeriasLicencaDTO save(final FeriasLicencaDTO dto) {
        log.debug("Request to save FeriasLicenca : {}", dto);
        Function<FeriasLicencaDTO, FeriasLicenca> fromDtoToEntity = mapper::toEntity;
        return fromDtoToEntity.andThen(repository::save)
                              .andThen(mapper::toDto)
                              .apply(dto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<FeriasLicencaDTO> findOne(Long id) {
        log.debug("Request to get FeriasLicenca : {}", id);
        return repository.findById(id)
                         .map(mapper::toDto);
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeriasLicenca : {}", id);
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<FeriasLicencaDTO> seekByParams(final FeriasLicencaDTO dto, final Pageable pageable) {
        return repository.query(mapper.toEntity(dto), pageable)
                         .map(mapper::toDto);
    }
}
