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
import com.rcsoyer.servicosjuridicos.service.ProcessoJudicialService;
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.service.dto.ProcessoJudicialDTO;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing ProcessoJudicial.
 */
@RestController
@RequestMapping("/api")
public class ProcessoJudicialResource {

  private final Logger log = LoggerFactory.getLogger(ProcessoJudicialResource.class);

  private static final String ENTITY_NAME = "processoJudicial";

  private final ProcessoJudicialService processoJudicialService;

  public ProcessoJudicialResource(ProcessoJudicialService processoJudicialService) {
    this.processoJudicialService = processoJudicialService;
  }

  /**
   * POST /processo-judicials : Create a new processoJudicial.
   *
   * @param dto the processoJudicialDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new processoJudicialDTO,
   *         or with status 400 (Bad Request) if the processoJudicial has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @Timed
  @PostMapping("/processo-judicials")
  public ResponseEntity<ProcessoJudicialDTO> createProcessoJudicial(
      @Valid @RequestBody ProcessoJudicialDTO dto) throws URISyntaxException {
    log.debug("REST request to save ProcessoJudicial : {}", dto);
    ProcessoJudicialDTO result = processoJudicialService.save(dto);
    return ResponseEntity.created(new URI("/api/processo-judicials/" + result.getId()))
        .headers(HeaderUtil.entityCreationAlert(ENTITY_NAME, result.getId().toString()))
        .body(result);
  }


  /**
   * PUT /processo-judicials : Updates an existing processoJudicial.
   *
   * @param processoJudicialDTO the processoJudicialDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated processoJudicialDTO,
   *         or with status 400 (Bad Request) if the processoJudicialDTO is not valid, or with
   *         status 500 (Internal Server Error) if the processoJudicialDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @Timed
  @PutMapping("/processo-judicials")
  public ResponseEntity<ProcessoJudicialDTO> updateProcessoJudicial(
      @Valid @RequestBody ProcessoJudicialDTO processoJudicialDTO) throws URISyntaxException {
    log.debug("REST request to update ProcessoJudicial : {}", processoJudicialDTO);
    ProcessoJudicialDTO result = processoJudicialService.save(processoJudicialDTO);
    return ResponseEntity.ok()
        .headers(
            HeaderUtil.entityUpdateAlert(ENTITY_NAME, processoJudicialDTO.getId().toString()))
        .body(result);
  }

  /**
   * GET /processo-judicials : get all the processoJudicials.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of processoJudicials in body
   */
  @GetMapping("/processo-judicials")
  @Timed
  public ResponseEntity<List<ProcessoJudicialDTO>> getAllProcessoJudicials(Pageable pageable) {
    log.debug("REST request to get a page of ProcessoJudicials");
    Page<ProcessoJudicialDTO> page = processoJudicialService.findAll(pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, "/api/processo-judicials");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @Timed
  @GetMapping("/processos-judiciais")
  public ResponseEntity<List<ProcessoJudicialDTO>> getProcessosJudiciais(
      @RequestParam("dto") ProcessoJudicialDTO dto,
      @RequestParam("pageable") PageableDTO pageableDTO) {
    log.debug("REST request to get a page of ProcessoJudiciais");
    Pageable pageable = pageableDTO.getPageable();
    Page<ProcessoJudicialDTO> page = processoJudicialService.findByParams(dto, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, "/api/processos-judiciais");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /processo-judicials/:id : get the "id" processoJudicial.
   *
   * @param id the id of the processoJudicialDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the processoJudicialDTO, or with
   *         status 404 (Not Found)
   */
  @GetMapping("/processo-judicials/{id}")
  @Timed
  public ResponseEntity<ProcessoJudicialDTO> getProcessoJudicial(@PathVariable Long id) {
    log.debug("REST request to get ProcessoJudicial : {}", id);
    ProcessoJudicialDTO processoJudicialDTO = processoJudicialService.findOne(id);
    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processoJudicialDTO));
  }

  /**
   * DELETE /processo-judicials/:id : delete the "id" processoJudicial.
   *
   * @param id the id of the processoJudicialDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/processo-judicials/{id}")
  @Timed
  public ResponseEntity<Void> deleteProcessoJudicial(@PathVariable Long id) {
    log.debug("REST request to delete ProcessoJudicial : {}", id);
    processoJudicialService.delete(id);
    return ResponseEntity.ok()
                         .headers(HeaderUtil.entityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }
}
