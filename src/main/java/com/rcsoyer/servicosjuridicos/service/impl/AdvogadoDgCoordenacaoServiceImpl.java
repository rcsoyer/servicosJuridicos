package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.repository.AdvogadoDgCoordenacaoRepository;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoDgCoordenacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AdvogadoDgCoordenacao.
 */
@Service
@Transactional
public class AdvogadoDgCoordenacaoServiceImpl implements AdvogadoDgCoordenacaoService {

    private final Logger log = LoggerFactory.getLogger(AdvogadoDgCoordenacaoServiceImpl.class);

    private final AdvogadoDgCoordenacaoRepository advogadoDgCoordenacaoRepository;

    private final AdvogadoDgCoordenacaoMapper advogadoDgCoordenacaoMapper;

    public AdvogadoDgCoordenacaoServiceImpl(AdvogadoDgCoordenacaoRepository advogadoDgCoordenacaoRepository, AdvogadoDgCoordenacaoMapper advogadoDgCoordenacaoMapper) {
        this.advogadoDgCoordenacaoRepository = advogadoDgCoordenacaoRepository;
        this.advogadoDgCoordenacaoMapper = advogadoDgCoordenacaoMapper;
    }

    /**
     * Save a advogadoDgCoordenacao.
     *
     * @param advogadoDgCoordenacaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdvogadoDgCoordenacaoDTO save(AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO) {
        log.debug("Request to save AdvogadoDgCoordenacao : {}", advogadoDgCoordenacaoDTO);
        AdvogadoDgCoordenacao advogadoDgCoordenacao = advogadoDgCoordenacaoMapper.toEntity(advogadoDgCoordenacaoDTO);
        advogadoDgCoordenacao = advogadoDgCoordenacaoRepository.save(advogadoDgCoordenacao);
        return advogadoDgCoordenacaoMapper.toDto(advogadoDgCoordenacao);
    }

    /**
     * Get all the advogadoDgCoordenacaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdvogadoDgCoordenacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdvogadoDgCoordenacaos");
        return advogadoDgCoordenacaoRepository.findAll(pageable)
            .map(advogadoDgCoordenacaoMapper::toDto);
    }

    /**
     * Get one advogadoDgCoordenacao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdvogadoDgCoordenacaoDTO findOne(Long id) {
        log.debug("Request to get AdvogadoDgCoordenacao : {}", id);
      /*  return advogadoDgCoordenacaoRepository.findById(id)
            .map(advogadoDgCoordenacaoMapper::toDto);*/
        return null;
    }

    /**
     * Delete the advogadoDgCoordenacao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdvogadoDgCoordenacao : {}", id);
        advogadoDgCoordenacaoRepository.deleteById(id);
    }
}
