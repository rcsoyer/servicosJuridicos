package com.rcsoyer.servicosjuridicos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

  private static final String ENTITY_NAME = "modalidade";
  private final ModalidadeService modalidadeService;
  private final Logger log = LoggerFactory.getLogger(ModalidadeResource.class);

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
  @Timed
  @PostMapping("/modalidade")
  public ResponseEntity<ModalidadeDTO> createModalidade(
      @Valid @RequestBody ModalidadeDTO modalidadeDTO) throws URISyntaxException {
    log.debug("REST request to save Modalidade : {}", modalidadeDTO);
    throwsBadRequestIfHasId(modalidadeDTO);
    ModalidadeDTO result = modalidadeService.save(modalidadeDTO);
    Long resultId = result.getId();
    HttpHeaders headerCreationAlert = HeaderUtil.entityCreationAlert(ENTITY_NAME, resultId.toString());
    URI uriCreate = new URI("/api/modalidades/" + resultId);
    return ResponseEntity.created(uriCreate)
                         .headers(headerCreationAlert)
                         .body(result);
  }
  
  private void throwsBadRequestIfHasId(ModalidadeDTO modalidadeDTO) {
    Supplier<BadRequestAlertException> throwBadRequestExcpetion = () -> {
      String msgError = "A new modalidade cannot already have an ID";
      BadRequestAlertException badRequestAlertException =
          new BadRequestAlertException(msgError, ENTITY_NAME, "idexists");
      log.error(msgError, badRequestAlertException);
      return badRequestAlertException;
    };
    Predicate<ModalidadeDTO> hasNoId = modalidade -> Objects.isNull(modalidade.getId());
    Optional.of(modalidadeDTO)
            .filter(hasNoId)
            .orElseThrow(throwBadRequestExcpetion);
  }

  /**
   * PUT  /modalidades : Updates an existing modalidade.
   *
   * @param modalidadeDTO the modalidadeDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated modalidadeDTO,
   * or with status 400 (Bad Request) if the modalidadeDTO is not valid,
   * or with status 500 (Internal Server Error) if the modalidadeDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @Timed
  @PutMapping("/modalidade")
  public ResponseEntity<ModalidadeDTO> updateModalidade(
      @Valid @RequestBody ModalidadeDTO modalidadeDTO) throws URISyntaxException {
    log.debug("REST request to update Modalidade : {}", modalidadeDTO);
    throwsBadRequestIfHasNoId(modalidadeDTO);
    ModalidadeDTO result = modalidadeService.save(modalidadeDTO);
    String idString = modalidadeDTO.getId().toString();
    HttpHeaders headerUpdateAlert = HeaderUtil.entityUpdateAlert(ENTITY_NAME, idString);
    return ResponseEntity.ok()
                         .headers(headerUpdateAlert)
                         .body(result);
  }
  
  private void throwsBadRequestIfHasNoId(ModalidadeDTO modalidadeDTO) {
    Supplier<BadRequestAlertException> throwBadRequestExcpetion = () -> {
      String msgError = "Invalid id";
      BadRequestAlertException badRequestAlertException =
          new BadRequestAlertException(msgError, ENTITY_NAME, "idnull");
      log.error(msgError, badRequestAlertException);
      return badRequestAlertException;
    };
    Predicate<ModalidadeDTO> hasId = modalidade -> Objects.nonNull(modalidade.getId());
    Optional.of(modalidadeDTO)
            .filter(hasId)
            .orElseThrow(throwBadRequestExcpetion);
  }

  /**
   * GET /modalidades : get all the modalidades.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of modalidades in body
   */
  @Timed
  @GetMapping("/modalidade")
  public ResponseEntity<List<ModalidadeDTO>> getAllModalidades(Pageable pageable) {
    log.debug("REST request to get a page of Modalidades");
    Page<ModalidadeDTO> page = modalidadeService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modalidade");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @Timed
  @GetMapping("/queryModalidades")
  public ResponseEntity<List<ModalidadeDTO>> getModalidades(@RequestParam("dto") ModalidadeDTO dto,
      @RequestParam("pageable") PageableDTO pageableDTO) {
    log.debug("REST request to get a page of Modalidades by input params");
    Pageable pageable = pageableDTO.getPageable();
    Page<ModalidadeDTO> page = modalidadeService.findByParams(dto, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/queryModalidades");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /modalidades/:id : get the "id" modalidade.
   *
   * @param id the id of the modalidadeDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the modalidadeDTO, or with status
   *         404 (Not Found)
   */
  @Timed
  @GetMapping("/modalidade/{id}")
  public ResponseEntity<ModalidadeDTO> getModalidade(@PathVariable Long id) {
    log.debug("REST request to get Modalidade : {}", id);
    Optional<ModalidadeDTO> modalidadeDTO = modalidadeService.findOne(id);
    return ResponseUtil.wrapOrNotFound(modalidadeDTO);
  }

  /**
   * DELETE /modalidades/:id : delete the "id" modalidade.
   *
   * @param id the id of the modalidadeDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @Timed
  @DeleteMapping("/modalidade/{id}")
  public ResponseEntity<Void> deleteModalidade(@PathVariable Long id) {
    log.debug("REST request to delete Modalidade : {}", id);
    modalidadeService.delete(id);
    HttpHeaders headerDeletionAlert = HeaderUtil.entityDeletionAlert(ENTITY_NAME, id.toString());
    return ResponseEntity.ok()
                         .headers(headerDeletionAlert)
                         .build();
  }
}
