package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil.generatePaginationHttpHeaders;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    
    /**
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping
    @ApiOperation(value = "Create a new assunto", response = AssuntoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Assunto created"),
        @ApiResponse(code = 400, message = "Assunto already has and ID")
    })
    public ResponseEntity<AssuntoDTO> createAssunto(@Valid @RequestBody AssuntoDTO dto)
        throws URISyntaxException {
        log.debug("REST request to save Assunto : {}", dto);
        throwsBadRequestIfHasId(dto);
        AssuntoDTO result = assuntoService.save(dto);
        var resultId = result.getId();
        var uriCreate = new URI("/api/assunto/" + resultId);
        return ResponseEntity.created(uriCreate)
                             .body(result);
    }
    
    @Timed
    @PutMapping
    @ApiOperation(value = "Update an existing assunto", response = AssuntoDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Assunto created"),
        @ApiResponse(code = 400, message = "An existing Assunto must have an ID"),
        @ApiResponse(code = 500, message = "Assunto couldn't be update")
    })
    public ResponseEntity<AssuntoDTO> updateAssunto(@Valid @RequestBody AssuntoDTO dto) {
        log.debug("REST request to update Assunto : {}", dto);
        throwsBadRequestIfHasNoId(dto);
        var result = assuntoService.save(dto);
        var idString = dto.getId().toString();
        var headerUpdateAlert = entityUpdateAlert(ENTITY_NAME, idString);
        return ResponseEntity.ok()
                             .headers(headerUpdateAlert)
                             .body(result);
    }
    
    @Timed
    @GetMapping
    @ApiOperation("Get a paginated list of Assunto matching the supplied query parameters and pagination information")
    public ResponseEntity<List<AssuntoDTO>> getAssuntos(AssuntoDTO dto, Pageable pageable) {
        log.debug("REST request to get a page of Assuntos with following params: {}, {}", dto, pageable);
        var page = assuntoService.seekByParams(dto, pageable);
        var headers = generatePaginationHttpHeaders(page, "/api/assunto");
        return ResponseEntity.ok()
                             .headers(headers)
                             .body(page.getContent());
    }
    
    
    /**
     * GET /assuntos/:id : get the "id" assunto.
     *
     * @param id the id of the assuntoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assuntoDTO, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/{id}")
    @ApiOperation("Get an Assunto matching the given id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Assunto founded matching the given id"),
        @ApiResponse(code = 404, message = "No Assunto found with the given id")
    })
    public ResponseEntity<AssuntoDTO> getAssunto(@PathVariable Long id) {
        log.debug("REST request to get Assunto : {}", id);
        var assuntoFounded = assuntoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assuntoFounded);
    }
    
    /**
     * DELETE /assuntos/:id : delete the "id" assunto.
     *
     * @param id the id of the assuntoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssunto(@PathVariable Long id) {
        log.debug("REST request to delete Assunto : {}", id);
        assuntoService.delete(id);
        var headerDeletionAlert = entityDeletionAlert(ENTITY_NAME, id.toString());
        return ResponseEntity.ok()
                             .headers(headerDeletionAlert)
                             .build();
    }
    
    @ResponseStatus(value = HttpStatus.CONFLICT,
        reason = "Não é possível excluir esse 'Assunto', pois ele está associado a uma ou mais 'Coordenações Jurídicas'")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict(HttpServletRequest httpRequest,
                         DataIntegrityViolationException dataIntegrityViolation) {
        var requestURI = httpRequest.getRequestURI();
        log.warn(
            "Attempt to call '{}' failed. 'Assunto' cannot be excluded because is tied to one or more: {}",
            requestURI, CoordenacaoJuridica.class.getCanonicalName());
    }
    
    private void throwsBadRequestIfHasId(final AssuntoDTO dto) {
        if (nonNull(dto.getId())) {
            var msgError = "A new assunto cannot already have an ID";
            var badRequestAlertException = new BadRequestAlertException(msgError, ENTITY_NAME, "idexists");
            log.error(msgError, badRequestAlertException);
            throw badRequestAlertException;
        }
    }
    
    private void throwsBadRequestIfHasNoId(final AssuntoDTO dto) {
        if (isNull(dto.getId())) {
            var msgError = "An existing Assunto must have an id";
            var badRequestAlertException = new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            log.error(msgError, badRequestAlertException);
            throw badRequestAlertException;
        }
    }
}
