package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    
    /**
     * PUT /assuntos : Updates an existing assunto.
     *
     * @param dto the assuntoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assuntoDTO, or with status 400 (Bad
     * Request) if the assuntoDTO is not valid, or with status 500 (Internal Server Error) if the assuntoDTO couldn't be
     * updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PutMapping
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
    
    /**
     * GET /assuntos : get all the assuntos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assuntos in body
     */
    @Timed
    @GetMapping
    public ResponseEntity<List<AssuntoDTO>> getAllAssuntos(Pageable pageable) {
        log.debug("REST request to get a page of Assuntos");
        var page = assuntoService.findAll(pageable);
        var headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assunto");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @Timed
    @GetMapping("/queryAssuntos")
    public ResponseEntity<List<AssuntoDTO>> getAssuntos(@RequestParam("dto") AssuntoDTO dto,
                                                        @RequestParam("pageable") PageableDTO pageableDTO) {
        log.debug("REST request to get a page of Assuntos");
        var pageable = pageableDTO.getPageable();
        var page = assuntoService.seekByParams(dto, pageable);
        var headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/queryAssuntos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    
    /**
     * GET /assuntos/:id : get the "id" assunto.
     *
     * @param id the id of the assuntoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assuntoDTO, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/{id}")
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
