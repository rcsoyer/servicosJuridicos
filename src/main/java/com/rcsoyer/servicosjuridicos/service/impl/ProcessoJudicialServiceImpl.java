package com.rcsoyer.servicosjuridicos.service.impl;

import static java.util.Comparator.comparingInt;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.repository.AdvogadoDgCoordenacaoRepository;
import com.rcsoyer.servicosjuridicos.repository.processo.ProcessoJudicialRepository;
import com.rcsoyer.servicosjuridicos.service.ProcessoJudicialService;
import com.rcsoyer.servicosjuridicos.service.dto.ProcessoJudicialDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.ProcessoJudicialMapper;
import java.util.List;
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
    private final AdvogadoDgCoordenacaoRepository advCoordenacaoRepository;
    
    public ProcessoJudicialServiceImpl(final ProcessoJudicialRepository processoJudicialRepository,
                                       final ProcessoJudicialMapper processoJudicialMapper,
                                       final AdvogadoDgCoordenacaoRepository advCoordenacaoRepository) {
        this.mapper = processoJudicialMapper;
        this.repository = processoJudicialRepository;
        this.advCoordenacaoRepository = advCoordenacaoRepository;
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
    
    private void distributeProcess(final ProcessoJudicial processo) {
        int sextoDigito = processo.getSextoDigito();
        final List<AdvogadoDgCoordenacao> advogadosDigitos = advCoordenacaoRepository.findByAnyDigitoEq(sextoDigito);
        List<Advogado> orderedByNumberOfProcessos = advogadosDigitos
                                                        .stream()
                                                        .map(AdvogadoDgCoordenacao::getAdvogado)
                                                        .sorted(comparingInt(Advogado::numberOfProcessosJudiciais))
                                                        .collect(toList());
        chooseAdvogado(orderedByNumberOfProcessos, processo);
    }
    
    private void chooseAdvogado(final List<Advogado> advogados, final ProcessoJudicial processo) {
        if (!advogados.isEmpty() && isNull(processo.getAdvogado())) {
            processo.setAdvogado(advogados.get(0));
            advogados.remove(0);
            chooseAdvogado(advogados, processo);
        }
    }
    
}
