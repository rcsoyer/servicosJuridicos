package com.rcsoyer.servicosjuridicos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.ModalidadeService;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Modalidade.
 */
@RestController
@RequestMapping("/api")
public class ModalidadeResource {

  private final Logger log = LoggerFactory.getLogger(ModalidadeResource.class);

  private static final String ENTITY_NAME = "modalidade";

  private final ModalidadeService modalidadeService;

  public ModalidadeResource(ModalidadeService modalidadeService) {
    this.modalidadeService = modalidadeService;
  }

  /**
   * POST /modalidades : Create a new modalidade.
   *
   * @param modalidadeDTO the modalidadeDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new modalidadeDTO, or
   *         with status 400 (Bad Request) if the modalidade has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/modalidades")
  @Timed
  public ResponseEntity<ModalidadeDTO> createModalidade(
      @Valid @RequestBody ModalidadeDTO modalidadeDTO) throws URISyntaxException {
    log.debug("REST request to save Modalidade : {}", modalidadeDTO);
    if (modalidadeDTO.getId() != null) {
      throw new BadRequestAlertException("A new modalidade cannot already have an ID", ENTITY_NAME,
          "idexists");
    }
    ModalidadeDTO result = modalidadeService.save(modalidadeDTO);
    return ResponseEntity.created(new URI("/api/modalidades/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * PUT /modalidades : Updates an existing modalidade.
   *
   * @param modalidadeDTO the modalidadeDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated modalidadeDTO, or
   *         with status 400 (Bad Request) if the modalidadeDTO is not valid, or with status 500
   *         (Internal Server Error) if the modalidadeDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @Timed
  @PutMapping("/modalidades")
  public ResponseEntity<ModalidadeDTO> updateModalidade(
      @Valid @RequestBody ModalidadeDTO modalidadeDTO) throws URISyntaxException {
    log.debug("REST request to update Modalidade : {}", modalidadeDTO);
    if (modalidadeDTO.getId() == null) {
      return createModalidade(modalidadeDTO);
    }
    ModalidadeDTO result = modalidadeService.save(modalidadeDTO);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modalidadeDTO.getId().toString()))
        .body(result);
  }

  /**
   * GET /modalidades : get all the modalidades.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of modalidades in body
   */
  @GetMapping("/modalidades")
  @Timed
  public ResponseEntity<List<ModalidadeDTO>> getAllModalidades(Pageable pageable) {
    log.debug("REST request to get a page of Modalidades");
    Page<ModalidadeDTO> page = modalidadeService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modalidades");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @Timed
  @GetMapping("/getModalidades")
  public ResponseEntity<List<ModalidadeDTO>> getModalidades(@RequestParam("dto") ModalidadeDTO dto,
      @RequestParam("pageable") PageableDTO pageableDTO) {
    log.debug("REST request to get a page of Modalidades by input params");
    Pageable pageable = pageableDTO.getPageable();
    Page<ModalidadeDTO> page = modalidadeService.findByParams(dto, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getModalidades");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /modalidades/:id : get the "id" modalidade.
   *
   * @param id the id of the modalidadeDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the modalidadeDTO, or with status
   *         404 (Not Found)
   */
  @GetMapping("/modalidades/{id}")
  @Timed
  public ResponseEntity<ModalidadeDTO> getModalidade(@PathVariable Long id) {
    log.debug("REST request to get Modalidade : {}", id);
    ModalidadeDTO modalidadeDTO = modalidadeService.findOne(id);
    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modalidadeDTO));
  }

  /**
   * DELETE /modalidades/:id : delete the "id" modalidade.
   *
   * @param id the id of the modalidadeDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/modalidades/{id}")
  @Timed
  public ResponseEntity<Void> deleteModalidade(@PathVariable Long id) {
    log.debug("REST request to delete Modalidade : {}", id);
    modalidadeService.delete(id);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }
}
