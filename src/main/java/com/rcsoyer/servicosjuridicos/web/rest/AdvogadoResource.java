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
    
    private final AdvogadoService service;
    private static final String ENTITY_NAME = "advogado";
    
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
        @ApiResponse(code = 200, message = "Advogado created"),
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
        final var page = service.findByParams(dto, pageable);
        return ResponseEntity.ok()
                             .headers(generatePaginationHttpHeaders(page, "/api/advogado"))
                             .body(page.getContent());
    }
    
    /**
     * GET /advogados/:id : get the "id" advogado.
     *
     * @param id the id of the advogadoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the advogadoDTO, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/{id}")
    public ResponseEntity<AdvogadoDTO> getAdvogado(@PathVariable @Valid @Min(1) Long id) {
        log.debug("REST request to get Advogado : {}", id);
        var advogadoDTO = service.findOne(id);
        return ResponseUtil.wrapOrNotFound(advogadoDTO);
    }
    
    /**
     * DELETE /advogados/:id : delete the "id" advogado.
     *
     * @param id the id of the advogadoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid @Min(1) Long id) {
        log.debug("REST request to delete Advogado : {}", id);
        service.delete(id);
        var entityDeletionAlert = entityDeletionAlert(ENTITY_NAME, id.toString());
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert)
                             .build();
    }
    
    private Supplier<BadRequestAlertException> badRequestHasIdOnCreation() {
        return () -> {
            var badRequestAlertException =
                new BadRequestAlertException("A new Advogado cannot already have an ID", ENTITY_NAME, "idexists");
            log.error("Invalid attempt to create an Advogado", badRequestAlertException);
            return badRequestAlertException;
        };
    }
    
    private Supplier<BadRequestAlertException> badRequestHasNoIdOnUpdate() {
        return () -> {
            var badRequestAlertException =
                new BadRequestAlertException("An existing Advogado must have an ID", ENTITY_NAME, "idnull");
            log.error("Invalid attempt to update an Advogado", badRequestAlertException);
            return badRequestAlertException;
        };
    }
}
