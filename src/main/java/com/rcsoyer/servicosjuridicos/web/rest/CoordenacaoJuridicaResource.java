package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityCreationAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil.generatePaginationHttpHeaders;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoCreateUpdateDto;
import com.rcsoyer.servicosjuridicos.service.dto.QueryParamsCoordenacao;
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
 * REST controller for managing CoordenacaoJuridica.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/coordenacao-juridica")
public class CoordenacaoJuridicaResource {
    
    private static final String ENTITY_NAME = "coordenacaoJuridica";
    
    private final CoordenacaoJuridicaService service;
    
    public CoordenacaoJuridicaResource(final CoordenacaoJuridicaService coordenacaoJuridicaService) {
        this.service = coordenacaoJuridicaService;
    }
    
    @Timed
    @PostMapping
    @ApiOperation(value = "Create a new CoordenacaoJuridica", response = CoordenacaoCreateUpdateDto.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "CoordenacaoJuridica created"),
        @ApiResponse(code = 400, message = "A new CoordenacaoJuridica must not have and ID")
    })
    public ResponseEntity<CoordenacaoCreateUpdateDto> createCoordenacaoJuridica(
        @Valid @RequestBody final CoordenacaoCreateUpdateDto coordenacaoJuridicaDto) {
        log.info("request to create a CoordenacaoJuridica: {}", coordenacaoJuridicaDto);
        return Optional.of(coordenacaoJuridicaDto)
                       .filter(dto -> isNull(dto.getId()))
                       .map(service::save)
                       .map(savedDto -> ResponseEntity
                                            .created(URI.create("/api/coordenacao-juridica/" + savedDto.getId()))
                                            .headers(entityCreationAlert(ENTITY_NAME, savedDto.getId().toString()))
                                            .body(savedDto))
                       .orElseThrow(badRequestHasIdOnCreation());
    }
    
    @Timed
    @PutMapping
    @ApiOperation(value = "Update an existing CoordenacaoJuridica", response = CoordenacaoCreateUpdateDto.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "CoordenacaoJuridica updated"),
        @ApiResponse(code = 400, message = "An existing CoordenacaoJuridica must have an ID")
    })
    public ResponseEntity<CoordenacaoCreateUpdateDto> updateCoordenacaoJuridica(
        @Valid @RequestBody final CoordenacaoCreateUpdateDto coordenacaoJuridicaDto) {
        log.info("request to update CoordenacaoJuridica: {}", coordenacaoJuridicaDto);
        return Optional.of(coordenacaoJuridicaDto)
                       .filter(dto -> nonNull(dto.getId()))
                       .map(service::save)
                       .map(result -> ResponseEntity.ok()
                                                    .headers(entityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                                                    .body(result))
                       .orElseThrow(badRequestHasNoIdOnUpdate());
    }
    
    @Timed
    @GetMapping
    @ApiOperation("Get a paginated list of CoordenacaoJuridica matching the supplied query parameters and pagination information")
    public ResponseEntity<List<CoordenacaoCreateUpdateDto>> getCoordenacoes(
        final QueryParamsCoordenacao queryParams, final Pageable pageable) {
        log.debug("request to get a page of CoordenacaoJuridicas with: {} and {}", queryParams, pageable);
        final Page<CoordenacaoCreateUpdateDto> page = service.seekByParams(queryParams, pageable);
        return ResponseEntity.ok()
                             .headers(generatePaginationHttpHeaders(page, "/api/coordenacao-juridica"))
                             .body(page.getContent());
    }
    
    @Timed
    @GetMapping("/{id}")
    @ApiOperation("Get an CoordenacaoJuridica matching the given id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "CoordenacaoJuridica founded matching the given id"),
        @ApiResponse(code = 404, message = "No CoordenacaoJuridica found with the given id")
    })
    public ResponseEntity<CoordenacaoCreateUpdateDto> getCoordenacaoJuridica(@PathVariable @Min(1L) Long id) {
        log.debug("request to get CoordenacaoJuridica: {}", id);
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }
    
    @Timed
    @DeleteMapping("/{id}")
    @ApiOperation("Delete a CoordenacaoJuridica matching the given id")
    public ResponseEntity<Void> deleteCoordenacaoJuridica(@PathVariable @Min(1L) Long id) {
        log.info("Request to delete CoordenacaoJuridica: {}", id);
        service.delete(id);
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }
    
    private Supplier<BadRequestAlertException> badRequestHasIdOnCreation() {
        return () -> {
            final var badRequestAlertException = BadRequestAlertException
                                                     .builder()
                                                     .defaultMessage(
                                                         "A new coordenacaoJuridica cannot already have an ID")
                                                     .entityName(ENTITY_NAME)
                                                     .errorKey("idexists")
                                                     .build();
            log.error("Wrong attempt to create a CoordenacaoJuridica", badRequestAlertException);
            return badRequestAlertException;
        };
    }
    
    private Supplier<BadRequestAlertException> badRequestHasNoIdOnUpdate() {
        return () -> {
            final var badRequestAlertException = BadRequestAlertException.builder()
                                                                         .defaultMessage("Invalid id")
                                                                         .entityName(ENTITY_NAME)
                                                                         .errorKey("idnull")
                                                                         .build();
            log.error("Invalid attempt to update a CoordenacaoJuridica", badRequestAlertException);
            return badRequestAlertException;
        };
    }
}
