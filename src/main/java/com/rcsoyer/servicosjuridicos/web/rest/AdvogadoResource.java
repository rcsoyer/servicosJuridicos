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
import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Advogado.
 */
@RestController
@RequestMapping("/api")
public class AdvogadoResource {

  private final Logger log = LoggerFactory.getLogger(AdvogadoResource.class);

  private static final String ENTITY_NAME = "advogado";

  private final AdvogadoService advogadoService;

  public AdvogadoResource(AdvogadoService advogadoService) {
    this.advogadoService = advogadoService;
  }

  /**
   * POST /advogados : Create a new advogado.
   *
   * @param advogadoDTO the advogadoDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new advogadoDTO, or with
   *         status 400 (Bad Request) if the advogado has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/advogados")
  @Timed
  public ResponseEntity<AdvogadoDTO> createAdvogado(@Valid @RequestBody AdvogadoDTO advogadoDTO)
      throws URISyntaxException {
    log.debug("REST request to save Advogado : {}", advogadoDTO);
    if (advogadoDTO.getId() != null) {
      throw new BadRequestAlertException("A new advogado cannot already have an ID", ENTITY_NAME,
          "idexists");
    }
    AdvogadoDTO result = advogadoService.save(advogadoDTO);
    return ResponseEntity.created(new URI("/api/advogados/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * PUT /advogados : Updates an existing advogado.
   *
   * @param advogadoDTO the advogadoDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated advogadoDTO, or with
   *         status 400 (Bad Request) if the advogadoDTO is not valid, or with status 500 (Internal
   *         Server Error) if the advogadoDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/advogados")
  @Timed
  public ResponseEntity<AdvogadoDTO> updateAdvogado(@Valid @RequestBody AdvogadoDTO advogadoDTO)
      throws URISyntaxException {
    log.debug("REST request to update Advogado : {}", advogadoDTO);
    if (advogadoDTO.getId() == null) {
      return createAdvogado(advogadoDTO);
    }
    AdvogadoDTO result = advogadoService.save(advogadoDTO);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, advogadoDTO.getId().toString()))
        .body(result);
  }

  /**
   * GET /advogados : get all the advogados.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of advogados in body
   */
  @GetMapping("/advogados")
  @Timed
  public ResponseEntity<List<AdvogadoDTO>> getAllAdvogados(Pageable pageable) {
    log.debug("REST request to get a page of Advogados");
    Page<AdvogadoDTO> page = advogadoService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/advogados");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @Timed
  @GetMapping("/getAdvogados")
  public ResponseEntity<List<AdvogadoDTO>> getAdvogados(@RequestParam("dto") AdvogadoDTO dto,
      @RequestParam("pageable") PageableDTO pageableDTO) {
    log.debug("REST request to get a page of Advogados by input params");
    Pageable pageable = pageableDTO.getPageable();
    Page<AdvogadoDTO> page = advogadoService.findByParams(dto, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getAdvogados");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /advogados/:id : get the "id" advogado.
   *
   * @param id the id of the advogadoDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the advogadoDTO, or with status
   *         404 (Not Found)
   */
  @GetMapping("/advogados/{id}")
  @Timed
  public ResponseEntity<AdvogadoDTO> getAdvogado(@PathVariable Long id) {
    log.debug("REST request to get Advogado : {}", id);
    AdvogadoDTO advogadoDTO = advogadoService.findOne(id);
    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(advogadoDTO));
  }

  /**
   * DELETE /advogados/:id : delete the "id" advogado.
   *
   * @param id the id of the advogadoDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/advogados/{id}")
  @Timed
  public ResponseEntity<Void> deleteAdvogado(@PathVariable Long id) {
    log.debug("REST request to delete Advogado : {}", id);
    advogadoService.delete(id);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }
}
