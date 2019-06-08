package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityCreationAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil.generatePaginationHttpHeaders;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.AdvogadoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
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
 * REST controller for managing Advogado.
 */
@Slf4j
@RestController
@RequestMapping("/api/advogado")
public class AdvogadoResource {
    
    private static final String ENTITY_NAME = "advogado";
    
    private final AdvogadoService service;
    
    public AdvogadoResource(final AdvogadoService advogadoService) {
        this.service = advogadoService;
    }
    
    @Timed
    @PostMapping
    @ApiOperation(value = "Create a new Advogado", response = AdvogadoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Advogado created"),
        @ApiResponse(code = 400, message = "Advogado already has and ID")
    })
    public ResponseEntity<AdvogadoDTO> create(@Valid @RequestBody final AdvogadoDTO advogadoDTO) {
        log.debug("REST request to create Advogado : {}", advogadoDTO);
        return Optional.of(advogadoDTO)
                       .filter(dto -> isNull(dto.getId()))
                       .map(service::save)
                       .map(dto -> ResponseEntity.created(URI.create(("/api/advogado/" + dto.getId())))
                                                 .headers(entityCreationAlert(ENTITY_NAME, dto.getId().toString()))
                                                 .body(dto))
                       .orElseThrow(badRequestHasIdOnCreation());
    }
    
    @Timed
    @PutMapping
    @ApiOperation(value = "Update an existing Advgado", response = AdvogadoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Advogado updated"),
        @ApiResponse(code = 400, message = "An existing Advogado must have an ID")
    })
    public ResponseEntity<AdvogadoDTO> update(@Valid @RequestBody final AdvogadoDTO advogadoDTO) {
        log.debug("REST request to update Advogado : {}", advogadoDTO);
        return Optional.of(advogadoDTO)
                       .filter(dto -> nonNull(dto.getId()))
                       .map(service::save)
                       .map(dto -> ResponseEntity.ok()
                                                 .headers(entityUpdateAlert(ENTITY_NAME, dto.getId().toString()))
                                                 .body(dto))
                       .orElseThrow(badRequestHasNoIdOnUpdate());
    }
    
    @Timed
    @GetMapping
    @ApiOperation("Get a paginated list of Advogado matching the supplied query parameters and pagination information")
    public ResponseEntity<List<AdvogadoDTO>> getAdvogados(final AdvogadoDTO dto, final Pageable pageable) {
        log.debug("REST request to get a page of Advogados by input params");
        final var page = service.seekByParams(dto, pageable);
        return ResponseEntity.ok()
                             .headers(generatePaginationHttpHeaders(page, "/api/advogado"))
                             .body(page.getContent());
    }
    
    @Timed
    @GetMapping("/{id}")
    @ApiOperation(value = "Get an Advgado matching the given ID", response = AdvogadoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Advogado exists"),
        @ApiResponse(code = 404, message = "No Advogado found matching the given ID")
    })
    public ResponseEntity<AdvogadoDTO> getAdvogado(@PathVariable @Valid @Min(1L) Long id) {
        log.debug("REST request to get Advogado matching: id={}", id);
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }
    
    @Timed
    @DeleteMapping("/{id}")
    @ApiOperation("Delete an Advogado matching the given id")
    public ResponseEntity<Void> delete(@PathVariable @Valid @Min(1L) Long id) {
        log.debug("REST request to delete Advogado : {}", id);
        service.delete(id);
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }
    
    private Supplier<BadRequestAlertException> badRequestHasIdOnCreation() {
        return () -> {
            final var badRequestAlertException = BadRequestAlertException
                                                     .builder()
                                                     .defaultMessage("A new Advogado cannot already have an ID")
                                                     .entityName(ENTITY_NAME)
                                                     .errorKey("idexists")
                                                     .build();
            log.error("Invalid attempt to create an Advogado", badRequestAlertException);
            return badRequestAlertException;
        };
    }
    
    private Supplier<BadRequestAlertException> badRequestHasNoIdOnUpdate() {
        return () -> {
            final var badRequestAlertException = BadRequestAlertException
                                                     .builder()
                                                     .defaultMessage("An existing Advogado must have an ID")
                                                     .entityName(ENTITY_NAME)
                                                     .errorKey("idnull")
                                                     .build();
            log.error("Invalid attempt to update an Advogado", badRequestAlertException);
            return badRequestAlertException;
        };
    }
}
