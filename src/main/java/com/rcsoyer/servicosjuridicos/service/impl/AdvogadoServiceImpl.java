package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.repository.advogado.AdvogadoRepository;
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
    
    public AdvogadoServiceImpl(AdvogadoRepository advogadoRepository,
        AdvogadoMapper advogadoMapper) {
        this.mapper = advogadoMapper;
        this.repository = advogadoRepository;
    }
    
    /**
     * Save a advogado.
     *
     * @param advogadoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdvogadoDTO save(AdvogadoDTO advogadoDTO) {
        log.debug("Request to save Advogado : {}", advogadoDTO);
        Function<AdvogadoDTO, Advogado> toEntity = mapper::toEntity;
        return toEntity.andThen(repository::save)
                       .andThen(mapper::toDto)
                       .apply(advogadoDTO);
    }
    
    
    /**
     * Get all the advogados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdvogadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Advogados");
        return repository.findAll(pageable).map(mapper::toDto);
    }
    
    /**
     * Get one advogado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdvogadoDTO> findOne(Long id) {
        log.debug("Request to get Advogado : {}", id);
        return repository.findById(id).map(mapper::toDto);
    }
    
    /**
     * Delete the advogado by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Advogado : {}", id);
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<AdvogadoDTO> findByParams(final AdvogadoDTO dto, final Pageable pageable) {
        log.debug("AdvogadoService mehtod to get Processos Judiciais by params");
        Function<AdvogadoDTO, Advogado> toEntity = mapper::toEntity;
        Function<Page<Advogado>, Page<AdvogadoDTO>> toPageDto =
            pageEntity -> pageEntity.map(mapper::toDto);
        Function<Advogado, Page<Advogado>> query = repository.query(pageable);
        return toEntity.andThen(query)
                       .andThen(toPageDto)
                       .apply(dto);
    }
}
