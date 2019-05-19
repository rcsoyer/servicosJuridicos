package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityCreationAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
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
 * REST controller for managing AdvogadoDgCoordenacao.
 */
@Slf4j
@RestController
@RequestMapping("/api/advogado-dg-coordenacao")
public class AdvogadoDgCoordenacaoResource {
    
    private static final String ENTITY_NAME = "advogadoDgCoordenacao";
    
    private final AdvogadoDgCoordenacaoService service;
    
    public AdvogadoDgCoordenacaoResource(final AdvogadoDgCoordenacaoService advogadoDgCoordenacaoService) {
        this.service = advogadoDgCoordenacaoService;
    }
    
    @Timed
    @PostMapping
    @ApiOperation(value = "Create a new AdvogadoDgCoordenacao", response = AdvogadoDgCoordenacaoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "AdvogadoDgCoordenacao created"),
        @ApiResponse(code = 400, message = "AdvogadoDgCoordenacao already has and ID")
    })
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> createAdvogadoDgCoordenacao(
        @Valid @RequestBody final AdvogadoDgCoordenacaoDTO dgCoordenacaoDTO) {
        log.info("REST request to create an AdvogadoDgCoordenacao: {}", dgCoordenacaoDTO);
        return Optional.of(dgCoordenacaoDTO)
                       .filter(dto -> isNull(dto.getId()))
                       .map(service::save)
                       .map(dtoResult -> ResponseEntity
                                             .created(URI.create("/api/advogado-dg-coordenacaos/" + dtoResult.getId()))
                                             .headers(entityCreationAlert(ENTITY_NAME, dtoResult.getId().toString()))
                                             .body(dtoResult))
                       .orElseThrow(badRequestHasIdOnCreation());
    }
    
    @Timed
    @PutMapping
    @ApiOperation(value = "Update an existing AdvogadoDgCoordenacao", response = AdvogadoDgCoordenacaoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "AdvogadoDgCoordenacao updated"),
        @ApiResponse(code = 400, message = "An existing AdvogadoDgCoordenacao must have an ID")
    })
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> updateAdvogadoDgCoordenacao(
        @Valid @RequestBody final AdvogadoDgCoordenacaoDTO dgCoordenacaoDTO) {
        log.info("REST request to update an AdvogadoDgCoordenacao: {}", dgCoordenacaoDTO);
        return Optional.of(dgCoordenacaoDTO)
                       .filter(dto -> nonNull(dto.getId()))
                       .map(service::save)
                       .map(dtoResult -> ResponseEntity.ok()
                                                       .headers(
                                                           entityUpdateAlert(ENTITY_NAME, dtoResult.getId().toString()))
                                                       .body(dtoResult))
                       .orElseThrow(badRequestHasNoIdOnUpdate());
    }
    
    @Timed
    @GetMapping("/{id}")
    @ApiOperation(value = "Get an AdvogadoDgCoordenacao matching the given ID", response = AdvogadoDgCoordenacaoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "AdvogadoDgCoordenacao exists"),
        @ApiResponse(code = 404, message = "No AdvogadoDgCoordenacao found matching the given ID")
    })
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> getAdvogadoDgCoordenacao(@PathVariable @Valid @Min(1L) Long id) {
        log.info("REST request to get AdvogadoDgCoordenacao: {}", id);
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }
    
    @Timed
    @DeleteMapping("/{id}")
    @ApiOperation("Delete an AdvogadoDgCoordenacao matching the given id")
    public ResponseEntity<Void> delete(@PathVariable @Valid @Min(1L) Long id) {
        log.info("REST request to delete AdvogadoDgCoordenacao: {}", id);
        service.delete(id);
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }
    
    private Supplier<BadRequestAlertException> badRequestHasIdOnCreation() {
        return () -> {
            final var idExists =
                new BadRequestAlertException(
                    "A new AdvogadoDgCoordenacao cannot already have an ID", ENTITY_NAME, "idexists");
            log.error("Invalid attempt to create an AdvogadoDgCoordenacao", idExists);
            return idExists;
        };
    }
    
    private Supplier<BadRequestAlertException> badRequestHasNoIdOnUpdate() {
        return () -> {
            final var idNull =
                new BadRequestAlertException("A existing AdvogadoDgCoordenacao must have an ID", ENTITY_NAME, "idnull");
            log.error("Invalid attempt to update an AdvogadoDgCoordenacao", idNull);
            return idNull;
        };
    }
}
