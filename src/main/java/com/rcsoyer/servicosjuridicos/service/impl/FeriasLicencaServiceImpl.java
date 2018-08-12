package com.rcsoyer.servicosjuridicos.service.impl;

import com.rcsoyer.servicosjuridicos.service.FeriasLicencaService;
import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.repository.FeriasLicencaRepository;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.FeriasLicencaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FeriasLicenca.
 */
@Service
@Transactional
public class FeriasLicencaServiceImpl implements FeriasLicencaService {

    private final Logger log = LoggerFactory.getLogger(FeriasLicencaServiceImpl.class);

    private final FeriasLicencaRepository feriasLicencaRepository;

    private final FeriasLicencaMapper feriasLicencaMapper;

    public FeriasLicencaServiceImpl(FeriasLicencaRepository feriasLicencaRepository, FeriasLicencaMapper feriasLicencaMapper) {
        this.feriasLicencaRepository = feriasLicencaRepository;
        this.feriasLicencaMapper = feriasLicencaMapper;
    }

    /**
     * Save a feriasLicenca.
     *
     * @param feriasLicencaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FeriasLicencaDTO save(FeriasLicencaDTO feriasLicencaDTO) {
        log.debug("Request to save FeriasLicenca : {}", feriasLicencaDTO);
        FeriasLicenca feriasLicenca = feriasLicencaMapper.toEntity(feriasLicencaDTO);
        feriasLicenca = feriasLicencaRepository.save(feriasLicenca);
        return feriasLicencaMapper.toDto(feriasLicenca);
    }

    /**
     * Get all the feriasLicencas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FeriasLicencaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FeriasLicencas");
        return feriasLicencaRepository.findAll(pageable)
            .map(feriasLicencaMapper::toDto);
    }

    /**
     * Get one feriasLicenca by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FeriasLicencaDTO findOne(Long id) {
        log.debug("Request to get FeriasLicenca : {}", id);
/*return advogadoRepository.findById(id)
            .map(advogadoMapper::toDto);*/
        return null;
    }

    /**
     * Delete the feriasLicenca by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeriasLicenca : {}", id);
        feriasLicencaRepository.deleteById(id);
    }
}
