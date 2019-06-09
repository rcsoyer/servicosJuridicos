package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityCreationAlert;
import static java.util.Objects.isNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.FeriasLicencaService;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Supplier;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     */
    @PostMapping("/ferias-licencas")
    @Timed
    public ResponseEntity<FeriasLicencaDTO> createFeriasLicenca(
        @Valid @RequestBody final FeriasLicencaDTO feriasLicencaDTO) {
        log.debug("REST request to save FeriasLicenca : {}", feriasLicencaDTO);
        return Optional.of(feriasLicencaDTO)
                       .filter(dto -> isNull(dto.getId()))
                       .map(service::save)
                       .map(dto -> ResponseEntity
                                       .created(URI.create("/api/ferias-licencas/" + dto.getId().toString()))
                                       .headers(entityCreationAlert(ENTITY_NAME, dto.getId().toString()))
                                       .body(dto))
                       .orElseThrow(badRequestHasIdUponCreation());
    }
    
    private Supplier<BadRequestAlertException> badRequestHasIdUponCreation() {
        return () -> {
            final var badRequest = BadRequestAlertException
                                       .builder()
                                       .defaultMessage("A new feriasLicenca cannot already have an ID")
                                       .entityName(ENTITY_NAME)
                                       .errorKey("idexists")
                                       .build();
            log.error("Wrong attempt to create a Ferias Licenca", badRequest);
            return badRequest;
        };
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
