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
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing CoordenacaoJuridica.
 */
@RestController
@RequestMapping("/api")
public class CoordenacaoJuridicaResource {

  private final Logger log = LoggerFactory.getLogger(CoordenacaoJuridicaResource.class);

  private static final String ENTITY_NAME = "coordenacaoJuridica";

  private final CoordenacaoJuridicaService coordenacaoJuridicaService;

  public CoordenacaoJuridicaResource(CoordenacaoJuridicaService coordenacaoJuridicaService) {
    this.coordenacaoJuridicaService = coordenacaoJuridicaService;
  }

  /**
   * POST /coordenacao-juridicas : Create a new coordenacaoJuridica.
   *
   * @param coordenacaoJuridicaDTO the coordenacaoJuridicaDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new
   *         coordenacaoJuridicaDTO, or with status 400 (Bad Request) if the coordenacaoJuridica has
   *         already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/coordenacao-juridicas")
  @Timed
  public ResponseEntity<CoordenacaoJuridicaDTO> createCoordenacaoJuridica(
      @Valid @RequestBody CoordenacaoJuridicaDTO coordenacaoJuridicaDTO) throws URISyntaxException {
    log.debug("REST request to save CoordenacaoJuridica : {}", coordenacaoJuridicaDTO);
    if (coordenacaoJuridicaDTO.getId() != null) {
      throw new BadRequestAlertException("A new coordenacaoJuridica cannot already have an ID",
          ENTITY_NAME, "idexists");
    }
    CoordenacaoJuridicaDTO result = coordenacaoJuridicaService.save(coordenacaoJuridicaDTO);
    return ResponseEntity.created(new URI("/api/coordenacao-juridicas/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * PUT /coordenacao-juridicas : Updates an existing coordenacaoJuridica.
   *
   * @param coordenacaoJuridicaDTO the coordenacaoJuridicaDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated
   *         coordenacaoJuridicaDTO, or with status 400 (Bad Request) if the coordenacaoJuridicaDTO
   *         is not valid, or with status 500 (Internal Server Error) if the coordenacaoJuridicaDTO
   *         couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/coordenacao-juridicas")
  @Timed
  public ResponseEntity<CoordenacaoJuridicaDTO> updateCoordenacaoJuridica(
      @Valid @RequestBody CoordenacaoJuridicaDTO coordenacaoJuridicaDTO) throws URISyntaxException {
    log.debug("REST request to update CoordenacaoJuridica : {}", coordenacaoJuridicaDTO);
    if (coordenacaoJuridicaDTO.getId() == null) {
      return createCoordenacaoJuridica(coordenacaoJuridicaDTO);
    }
    CoordenacaoJuridicaDTO result = coordenacaoJuridicaService.save(coordenacaoJuridicaDTO);
    return ResponseEntity.ok().headers(
        HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coordenacaoJuridicaDTO.getId().toString()))
        .body(result);
  }

  /**
   * GET /coordenacao-juridicas : get all the coordenacaoJuridicas.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of coordenacaoJuridicas in body
   */
  @GetMapping("/coordenacao-juridicas")
  @Timed
  public ResponseEntity<List<CoordenacaoJuridicaDTO>> getAllCoordenacaoJuridicas(
      Pageable pageable) {
    log.debug("REST request to get a page of CoordenacaoJuridicas");
    Page<CoordenacaoJuridicaDTO> page = coordenacaoJuridicaService.findAll(pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, "/api/coordenacao-juridicas");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @Timed
  @GetMapping("/coordenacoes")
  public ResponseEntity<List<CoordenacaoJuridicaDTO>> getCoordenacoes(
      @RequestParam("dto") CoordenacaoJuridicaDTO dto,
      @RequestParam("pageable") PageableDTO pageableDTO) {
    log.debug("REST request to get a page of CoordenacaoJuridicas");
    Pageable pageable = pageableDTO.getPageable();
    Page<CoordenacaoJuridicaDTO> page = coordenacaoJuridicaService.findByParams(dto, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, "/api/coordenacao-juridicas");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /coordenacao-juridicas/:id : get the "id" coordenacaoJuridica.
   *
   * @param id the id of the coordenacaoJuridicaDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the coordenacaoJuridicaDTO, or
   *         with status 404 (Not Found)
   */
  @GetMapping("/coordenacao-juridicas/{id}")
  @Timed
  public ResponseEntity<CoordenacaoJuridicaDTO> getCoordenacaoJuridica(@PathVariable Long id) {
    log.debug("REST request to get CoordenacaoJuridica : {}", id);
    CoordenacaoJuridicaDTO coordenacaoJuridicaDTO = coordenacaoJuridicaService.findOne(id);
    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coordenacaoJuridicaDTO));
  }

  /**
   * DELETE /coordenacao-juridicas/:id : delete the "id" coordenacaoJuridica.
   *
   * @param id the id of the coordenacaoJuridicaDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/coordenacao-juridicas/{id}")
  @Timed
  public ResponseEntity<Void> deleteCoordenacaoJuridica(@PathVariable Long id) {
    log.debug("REST request to delete CoordenacaoJuridica : {}", id);
    coordenacaoJuridicaService.delete(id);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }
}
