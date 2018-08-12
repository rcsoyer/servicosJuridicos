package com.rcsoyer.servicosjuridicos.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.repository.modalidade.ModalidadeRepository;
import com.rcsoyer.servicosjuridicos.service.ModalidadeService;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.ModalidadeMapper;


/**
 * Service Implementation for managing Modalidade.
 */
@Service
@Transactional
public class ModalidadeServiceImpl implements ModalidadeService {

  private final ModalidadeRepository repository;
  private final ModalidadeMapper mapper;
  private final Logger log = LoggerFactory.getLogger(ModalidadeServiceImpl.class);

  public ModalidadeServiceImpl(ModalidadeRepository modalidadeRepository,
      ModalidadeMapper modalidadeMapper) {
    this.repository = modalidadeRepository;
    this.mapper = modalidadeMapper;
  }

  /**
   * Save a modalidade.
   *
   * @param modalidadeDTO the entity to save
   * @return the persisted entity
   */
  @Override
  public ModalidadeDTO save(ModalidadeDTO modalidadeDTO) {
    log.debug("Request to save Modalidade : {}", modalidadeDTO);
    Modalidade modalidade = mapper.toEntity(modalidadeDTO);
    modalidade = repository.save(modalidade);
    return mapper.toDto(modalidade);
  }

  /**
   * Get all the modalidades.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<ModalidadeDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Modalidades");
    return repository.findAll(pageable).map(mapper::toDto);
  }

  /**
   * Get one modalidade by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public ModalidadeDTO findOne(Long id) {
    log.debug("Request to get Modalidade : {}", id);
    /*return advogadoRepository.findById(id)
            .map(advogadoMapper::toDto);*/
    return null;
  }

  /**
   * Delete the modalidade by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Modalidade : {}", id);
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ModalidadeDTO> findByParams(final ModalidadeDTO dto, final Pageable pageable) {
    log.debug("ModalidadeService method to get Modalidades by params");
    Modalidade modalidade = mapper.toEntity(dto);
    return repository.query(modalidade, pageable).map(mapper::toDto);
  }
}
