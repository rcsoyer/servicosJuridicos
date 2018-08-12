package com.rcsoyer.servicosjuridicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.FeriasLicencaService;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FeriasLicenca.
 */
@RestController
@RequestMapping("/api")
public class FeriasLicencaResource {

    private final Logger log = LoggerFactory.getLogger(FeriasLicencaResource.class);

    private static final String ENTITY_NAME = "feriasLicenca";

    private final FeriasLicencaService feriasLicencaService;

    public FeriasLicencaResource(FeriasLicencaService feriasLicencaService) {
        this.feriasLicencaService = feriasLicencaService;
    }

    /**
     * POST  /ferias-licencas : Create a new feriasLicenca.
     *
     * @param feriasLicencaDTO the feriasLicencaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feriasLicencaDTO, or with status 400 (Bad Request) if the feriasLicenca has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ferias-licencas")
    @Timed
    public ResponseEntity<FeriasLicencaDTO> createFeriasLicenca(@Valid @RequestBody FeriasLicencaDTO feriasLicencaDTO) throws URISyntaxException {
        log.debug("REST request to save FeriasLicenca : {}", feriasLicencaDTO);
        if (feriasLicencaDTO.getId() != null) {
            throw new BadRequestAlertException("A new feriasLicenca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeriasLicencaDTO result = feriasLicencaService.save(feriasLicencaDTO);
        return ResponseEntity.created(new URI("/api/ferias-licencas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ferias-licencas : Updates an existing feriasLicenca.
     *
     * @param feriasLicencaDTO the feriasLicencaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feriasLicencaDTO,
     * or with status 400 (Bad Request) if the feriasLicencaDTO is not valid,
     * or with status 500 (Internal Server Error) if the feriasLicencaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ferias-licencas")
    @Timed
    public ResponseEntity<FeriasLicencaDTO> updateFeriasLicenca(@Valid @RequestBody FeriasLicencaDTO feriasLicencaDTO) throws URISyntaxException {
        log.debug("REST request to update FeriasLicenca : {}", feriasLicencaDTO);
        if (feriasLicencaDTO.getId() == null) {
            return createFeriasLicenca(feriasLicencaDTO);
        }
        FeriasLicencaDTO result = feriasLicencaService.save(feriasLicencaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feriasLicencaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ferias-licencas : get all the feriasLicencas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of feriasLicencas in body
     */
    @GetMapping("/ferias-licencas")
    @Timed
    public ResponseEntity<List<FeriasLicencaDTO>> getAllFeriasLicencas(Pageable pageable) {
        log.debug("REST request to get a page of FeriasLicencas");
        Page<FeriasLicencaDTO> page = feriasLicencaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ferias-licencas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ferias-licencas/:id : get the "id" feriasLicenca.
     *
     * @param id the id of the feriasLicencaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feriasLicencaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ferias-licencas/{id}")
    @Timed
    public ResponseEntity<FeriasLicencaDTO> getFeriasLicenca(@PathVariable Long id) {
        log.debug("REST request to get FeriasLicenca : {}", id);
        FeriasLicencaDTO feriasLicencaDTO = feriasLicencaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(feriasLicencaDTO));
    }

    /**
     * DELETE  /ferias-licencas/:id : delete the "id" feriasLicenca.
     *
     * @param id the id of the feriasLicencaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ferias-licencas/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeriasLicenca(@PathVariable Long id) {
        log.debug("REST request to delete FeriasLicenca : {}", id);
        feriasLicencaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
