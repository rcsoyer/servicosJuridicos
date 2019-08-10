package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
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
    
    public AdvogadoDgCoordenacaoServiceImpl(final AdvogadoDgCoordenacaoRepository dgCoordenacaoRepository,
                                            final AdvogadoDgCoordenacaoMapper dgCoordenacaoMapper) {
        this.mapper = dgCoordenacaoMapper;
        this.repository = dgCoordenacaoRepository;
    }
    
    @Override
    public AdvogadoDgCoordenacaoDTO save(final AdvogadoDgCoordenacaoDTO dto) {
        log.debug("Call to service layer to create Advogado: {}", dto);
        checkMaxAdvogadosWithOneDgDupla(dto.getDgDupla());
        Function<AdvogadoDgCoordenacaoDTO, AdvogadoDgCoordenacao> toEntity = mapper::toEntity;
        return toEntity.andThen(repository::save)
                       .andThen(mapper::toDto)
                       .apply(dto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<AdvogadoDgCoordenacaoDTO> findOne(Long id) {
        log.debug("Call to service layer to get Advogado by id={}", id);
        return repository.findById(id)
                         .map(mapper::toDto);
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Access to service layer to delete Advogado by id={}", id);
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<AdvogadoDgCoordenacaoDTO> seekByParams(final AdvogadoDgCoordenacaoDTO dto,
                                                       final Pageable pageable) {
        log.debug("Call to service layer to get a page of Advogado by: searchParams={}, pageable={}", dto, pageable);
        return repository.query(mapper.toEntity(dto), pageable)
                         .map(mapper::toDto);
    }
    
    /**
     * Count the AdvogadoDgCoordenacao table to check the number of advogados registered with the passed digitoDupla.
     * The max number of advogados using the same digitoDupla at one time is always 2.
     *
     * @throws IllegalStateException if there's already 2 advogados using the passed digitoDupla
     */
    private void checkMaxAdvogadosWithOneDgDupla(Integer dgDupla) {
        Optional.ofNullable(dgDupla)
                .ifPresent(digitoDupla -> {
                    int numberOfAdvogadosWithDgDupla = repository.countByDgDuplaEquals(digitoDupla);
            
                    if (numberOfAdvogadosWithDgDupla == 2) {
                        final var illegalState =
                            new IllegalStateException("Máximo número de advogados usando mesmo dígito dupla é 2");
                        log.error("Error ao tentar registrar esse dígito dupla: {}", digitoDupla, illegalState);
                        throw illegalState;
                    }
                });
    }
    
}
