package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityCreationAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil.generatePaginationHttpHeaders;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Assunto.
 */
@Slf4j
@RestController
@RequestMapping("/api/assunto")
public class AssuntoResource {
    
    private final AssuntoService assuntoService;
    private static final String ENTITY_NAME = "assunto";
    
    public AssuntoResource(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }
    
    @Timed
    @PostMapping
    @ApiOperation(value = "Create a new assunto", response = AssuntoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Assunto created"),
        @ApiResponse(code = 400, message = "A new Assunto must not have and ID")
    })
    public ResponseEntity<AssuntoDTO> createAssunto(@Valid @RequestBody AssuntoDTO assuntoDTO) {
        log.info("REST request to save Assunto : {}", assuntoDTO);
        return Optional.of(assuntoDTO)
                       .filter(assunto -> isNull(assunto.getId()))
                       .map(assuntoService::save)
                       .map(dto -> ResponseEntity.created(URI.create("/api/assunto/" + dto.getId()))
                                                 .headers(entityCreationAlert(ENTITY_NAME, dto.getId().toString()))
                                                 .body(dto))
                       .orElseThrow(hasIdOnCreationBadRequest());
    }
    
    @Timed
    @PutMapping
    @ApiOperation(value = "Update an existing assunto", response = AssuntoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Assunto created"),
        @ApiResponse(code = 400, message = "An existing Assunto must have an ID")
    })
    public ResponseEntity<AssuntoDTO> updateAssunto(@Valid @RequestBody AssuntoDTO dto) {
        log.info("REST request to update Assunto : {}", dto);
        return Optional.of(dto)
                       .filter(assunto -> nonNull(assunto.getId()))
                       .map(assuntoService::save)
                       .map(result -> ResponseEntity.ok()
                                                    .headers(entityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                                                    .body(result))
                       .orElseThrow(hasNoIdOnUpdateBadRequest());
    }
    
    @Timed
    @GetMapping
    @ApiOperation("Get a paginated list of Assunto matching the supplied query parameters and pagination information")
    public ResponseEntity<List<AssuntoDTO>> getAssuntos(AssuntoDTO dto, Pageable pageable) {
        log.info("REST request to get a page of Assuntos with following params: {}, {}", dto, pageable);
        var page = assuntoService.seekByParams(dto, pageable);
        return ResponseEntity.ok()
                             .headers(generatePaginationHttpHeaders(page, "/api/assunto"))
                             .body(page.getContent());
    }
    
    @Timed
    @GetMapping("/{id}")
    @ApiOperation("Get an Assunto matching the given id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Assunto founded matching the given id"),
        @ApiResponse(code = 404, message = "No Assunto found with the given id")
    })
    public ResponseEntity<AssuntoDTO> getAssunto(@PathVariable Long id) {
        log.info("REST request to get Assunto : {}", id);
        return ResponseUtil.wrapOrNotFound(assuntoService.findOne(id));
    }
    
    @Timed
    @DeleteMapping("/{id}")
    @ApiOperation("Delete an Assunto matching the given id")
    public ResponseEntity<Void> deleteAssunto(@PathVariable Long id) {
        log.info("REST request to delete Assunto : {}", id);
        assuntoService.delete(id);
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }
    
    private Supplier<BadRequestAlertException> hasIdOnCreationBadRequest() {
        return () -> {
            var msgError = "A new assunto cannot already have an ID";
            var badRequestAlertException = new BadRequestAlertException(msgError, ENTITY_NAME, "idexists");
            log.error(msgError, badRequestAlertException);
            return badRequestAlertException;
        };
    }
    
    private Supplier<BadRequestAlertException> hasNoIdOnUpdateBadRequest() {
        return () -> {
            var msgError = "An existing Assunto must have an id";
            var badRequestAlertException = new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            log.error(msgError, badRequestAlertException);
            return badRequestAlertException;
        };
    }
}
