package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityCreationAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil.generatePaginationHttpHeaders;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.FeriasLicencaService;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Slf4j
@Validated
@RestController
@RequestMapping("/api/ferias-licencas")
public class FeriasLicencaResource {
    
    private static final String ENTITY_NAME = "feriasLicenca";
    
    private final FeriasLicencaService service;
    
    public FeriasLicencaResource(final FeriasLicencaService feriasLicencaService) {
        this.service = feriasLicencaService;
    }
    
    @Timed
    @PostMapping
    @ApiResponses({@ApiResponse(code = 201, message = "FeriasLicenca created"),
                      @ApiResponse(code = 400, message = "A new FeriasLicenca must not have an ID")
                  })
    public ResponseEntity<FeriasLicencaDTO> createFeriasLicenca(
        @Valid @RequestBody final FeriasLicencaDTO feriasLicencaDTO) {
        log.info("REST request to save FeriasLicenca : {}", feriasLicencaDTO);
        return Optional.of(feriasLicencaDTO)
                       .filter(dto -> isNull(dto.getId()))
                       .map(service::save)
                       .map(result -> ResponseEntity
                                          .created(URI.create("/api/ferias-licencas/" + result.getId().toString()))
                                          .headers(entityCreationAlert(ENTITY_NAME, result.getId().toString()))
                                          .body(result))
                       .orElseThrow(badRequestHasIdUponCreation(feriasLicencaDTO));
    }
    
    @Timed
    @PutMapping
    @ApiOperation(value = "Create a new FeriasLicenca", response = FeriasLicencaDTO.class)
    @ApiResponses({@ApiResponse(code = 200, message = "FeriasLicenca updated"),
                      @ApiResponse(code = 400, message = "An existing FeriasLicenca must have an ID")
                  })
    public ResponseEntity<FeriasLicencaDTO> updateFeriasLicenca(
        @Valid @RequestBody final FeriasLicencaDTO feriasLicencaDTO) {
        log.info("REST request to update FeriasLicenca : {}", feriasLicencaDTO);
        return Optional.of(feriasLicencaDTO)
                       .filter(dto -> nonNull(dto.getId()))
                       .map(service::save)
                       .map(result -> ResponseEntity.ok()
                                                    .headers(entityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                                                    .body(result))
                       .orElseThrow(badRequestDontHaveIdOnUpdate(feriasLicencaDTO));
    }
    
    @Timed
    @GetMapping("/{id}")
    @ApiOperation("Get a FeriasLicenca matching the given ID")
    public ResponseEntity<FeriasLicencaDTO> getFeriasLicenca(@PathVariable @Min(1L) Long id) {
        log.debug("REST request to get FeriasLicenca : {}", id);
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }
    
    @Timed
    @DeleteMapping("/{id}")
    @ApiOperation("Delete a FeriasLicenca matching the given ID")
    public ResponseEntity<Void> deleteFeriasLicenca(@PathVariable @Min(1L) Long id) {
        log.info("Request to delete FeriasLicenca: {}", id);
        service.delete(id);
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }
    
    @Timed
    @GetMapping
    @ApiOperation("Get a paginated list of FeriasLicenca matching the supplied query parameters and pagination information")
    public ResponseEntity<List<FeriasLicencaDTO>> getFeriasLicencas(final FeriasLicencaDTO queryParams,
                                                                    final Pageable pageable) {
        final Page<FeriasLicencaDTO> pageResult = service.seekByParams(queryParams, pageable);
        return ResponseEntity.ok()
                             .headers(generatePaginationHttpHeaders(pageResult, "/api/assunto"))
                             .body(pageResult.getContent());
    }
    
    private Supplier<BadRequestAlertException> badRequestHasIdUponCreation(final FeriasLicencaDTO dto) {
        return () -> {
            log.warn("Wrong attempt to create a Ferias Licenca: {}", dto);
            return BadRequestAlertException
                       .builder()
                       .defaultMessage("A new feriasLicenca cannot already have an ID")
                       .entityName(ENTITY_NAME)
                       .errorKey("idexists")
                       .build();
        };
    }
    
    private Supplier<BadRequestAlertException> badRequestDontHaveIdOnUpdate(final FeriasLicencaDTO dto) {
        return () -> {
            log.warn("Wrong attempt to update a FeriasLicenca: {}", dto);
            return BadRequestAlertException
                       .builder()
                       .defaultMessage("If don't have an ID there's nothing to update")
                       .entityName(ENTITY_NAME)
                       .errorKey("idnull")
                       .build();
        };
    }
}
