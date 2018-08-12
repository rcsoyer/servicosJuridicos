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
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Assunto.
 */
@RestController
@RequestMapping("/api")
public class AssuntoResource {

  private final Logger log = LoggerFactory.getLogger(AssuntoResource.class);

  private static final String ENTITY_NAME = "assunto";

  private final AssuntoService assuntoService;

  public AssuntoResource(AssuntoService assuntoService) {
    this.assuntoService = assuntoService;
  }

  /**
   * POST /assuntos : Create a new assunto.
   *
   * @param assuntoDTO the assuntoDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new assuntoDTO, or with
   *         status 400 (Bad Request) if the assunto has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/assuntos")
  @Timed
  public ResponseEntity<AssuntoDTO> createAssunto(@Valid @RequestBody AssuntoDTO assuntoDTO)
      throws URISyntaxException {
    log.debug("REST request to save Assunto : {}", assuntoDTO);
    if (assuntoDTO.getId() != null) {
      throw new BadRequestAlertException("A new assunto cannot already have an ID", ENTITY_NAME,
          "idexists");
    }
    AssuntoDTO result = assuntoService.save(assuntoDTO);
    return ResponseEntity.created(new URI("/api/assuntos/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * PUT /assuntos : Updates an existing assunto.
   *
   * @param assuntoDTO the assuntoDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated assuntoDTO, or with
   *         status 400 (Bad Request) if the assuntoDTO is not valid, or with status 500 (Internal
   *         Server Error) if the assuntoDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/assuntos")
  @Timed
  public ResponseEntity<AssuntoDTO> updateAssunto(@Valid @RequestBody AssuntoDTO assuntoDTO)
      throws URISyntaxException {
    log.debug("REST request to update Assunto : {}", assuntoDTO);
    if (assuntoDTO.getId() == null) {
      return createAssunto(assuntoDTO);
    }
    AssuntoDTO result = assuntoService.save(assuntoDTO);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assuntoDTO.getId().toString()))
        .body(result);
  }

  /**
   * GET /assuntos : get all the assuntos.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of assuntos in body
   */
  @GetMapping("/assuntos")
  @Timed
  public ResponseEntity<List<AssuntoDTO>> getAllAssuntos(Pageable pageable) {
    log.debug("REST request to get a page of Assuntos");
    Page<AssuntoDTO> page = assuntoService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assuntos");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @Timed
  @GetMapping("/queryAssuntos")
  public ResponseEntity<List<AssuntoDTO>> getAssuntos(@RequestParam("dto") AssuntoDTO dto,
      @RequestParam("pageable") PageableDTO pageableDTO) {
    log.debug("REST request to get a page of Assuntos");
    Pageable pageable = pageableDTO.getPageable();
    Page<AssuntoDTO> page = assuntoService.findByParams(dto, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assuntos");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }


  /**
   * GET /assuntos/:id : get the "id" assunto.
   *
   * @param id the id of the assuntoDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the assuntoDTO, or with status
   *         404 (Not Found)
   */
  @GetMapping("/assuntos/{id}")
  @Timed
  public ResponseEntity<AssuntoDTO> getAssunto(@PathVariable Long id) {
    log.debug("REST request to get Assunto : {}", id);
    AssuntoDTO assuntoDTO = assuntoService.findOne(id);
    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assuntoDTO));
  }

  /**
   * DELETE /assuntos/:id : delete the "id" assunto.
   *
   * @param id the id of the assuntoDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/assuntos/{id}")
  @Timed
  public ResponseEntity<Void> deleteAssunto(@PathVariable Long id) {
    log.debug("REST request to delete Assunto : {}", id);
    assuntoService.delete(id);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }
}
