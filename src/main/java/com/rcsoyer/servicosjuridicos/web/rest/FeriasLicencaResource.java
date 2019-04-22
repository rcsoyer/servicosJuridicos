package com.rcsoyer.servicosjuridicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.FeriasLicencaService;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing FeriasLicenca.
 */
@RestController
@RequestMapping("/api")
public class FeriasLicencaResource {
    
    private final Logger log = LoggerFactory.getLogger(FeriasLicencaResource.class);
    
    private static final String ENTITY_NAME = "feriasLicenca";
    
    private final FeriasLicencaService service;
    
    public FeriasLicencaResource(FeriasLicencaService feriasLicencaService) {
        this.service = feriasLicencaService;
    }
    
    /**
     * POST  /ferias-licencas : Create a new feriasLicenca.
     *
     * @param feriasLicencaDTO the feriasLicencaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feriasLicencaDTO, or with status 400
     * (Bad Request) if the feriasLicenca has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ferias-licencas")
    @Timed
    public ResponseEntity<FeriasLicencaDTO> createFeriasLicenca(@Valid @RequestBody FeriasLicencaDTO feriasLicencaDTO)
        throws URISyntaxException {
        log.debug("REST request to save FeriasLicenca : {}", feriasLicencaDTO);
        if (feriasLicencaDTO.getId() != null) {
            throw new BadRequestAlertException("A new feriasLicenca cannot already have an ID", ENTITY_NAME,
                                               "idexists");
        }
        FeriasLicencaDTO result = service.save(feriasLicencaDTO);
        return ResponseEntity.created(new URI("/api/ferias-licencas/" + result.getId()))
                             .headers(HeaderUtil.entityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }
    
    /**
     * PUT  /ferias-licencas : Updates an existing feriasLicenca.
     *
     * @param feriasLicencaDTO the feriasLicencaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feriasLicencaDTO, or with status 400
     * (Bad Request) if the feriasLicencaDTO is not valid, or with status 500 (Internal Server Error) if the
     * feriasLicencaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ferias-licencas")
    @Timed
    public ResponseEntity<FeriasLicencaDTO> updateFeriasLicenca(@Valid @RequestBody FeriasLicencaDTO feriasLicencaDTO)
        throws URISyntaxException {
        log.debug("REST request to update FeriasLicenca : {}", feriasLicencaDTO);
        if (feriasLicencaDTO.getId() == null) {
            return createFeriasLicenca(feriasLicencaDTO);
        }
        FeriasLicencaDTO result = service.save(feriasLicencaDTO);
        return ResponseEntity.ok()
                             .headers(
                                 HeaderUtil.entityUpdateAlert(ENTITY_NAME, feriasLicencaDTO.getId().toString()))
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
        Page<FeriasLicencaDTO> page = service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ferias-licencas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /ferias-licencas/:id : get the "id" feriasLicenca.
     *
     * @param id the id of the feriasLicencaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feriasLicencaDTO, or with status 404 (Not
     * Found)
     */
    @GetMapping("/ferias-licencas/{id}")
    @Timed
    public ResponseEntity<FeriasLicencaDTO> getFeriasLicenca(@PathVariable Long id) {
        log.debug("REST request to get FeriasLicenca : {}", id);
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
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
        service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.entityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
