package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.createEntityCreationAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.createEntityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.createEntityUpdateAlert;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.service.AssuntoService;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
@RequestMapping("/api")
public class AssuntoResource {

    private final AssuntoService assuntoService;
    private static final String ENTITY_NAME = "assunto";

    public AssuntoResource(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    /**
     * POST /assuntos : Create a new assunto.
     *
     * @param assuntoDTO the assuntoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assuntoDTO, or with
     * status 400 (Bad Request) if the assunto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping("/assunto")
    public ResponseEntity<AssuntoDTO> createAssunto(@Valid @RequestBody AssuntoDTO assuntoDTO)
        throws URISyntaxException {
        log.debug("REST request to save Assunto : {}", assuntoDTO);
        throwsBadRequestIfHasId(assuntoDTO);
        var result = assuntoService.save(assuntoDTO);
        var resultId = result.getId();
        var headerCreationAlert = createEntityCreationAlert(ENTITY_NAME, resultId.toString());
        var uriCreate = new URI("/api/assuntos/" + resultId);
        return ResponseEntity.created(uriCreate)
                             .headers(headerCreationAlert)
                             .body(result);
    }

    private void throwsBadRequestIfHasId(AssuntoDTO assuntoDTO) {
        Supplier<BadRequestAlertException> throwBadRequestExcpetion = () -> {
            var msgError = "A new assunto cannot already have an ID";
            var badRequestAlertException =
                new BadRequestAlertException(msgError, ENTITY_NAME, "idexists");
            log.error(msgError, badRequestAlertException);
            return badRequestAlertException;
        };
        Predicate<AssuntoDTO> hasNoId = assunto -> Objects.isNull(assunto.getId());
        Optional.of(assuntoDTO)
                .filter(hasNoId)
                .orElseThrow(throwBadRequestExcpetion);
    }

    /**
     * PUT /assuntos : Updates an existing assunto.
     *
     * @param assuntoDTO the assuntoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assuntoDTO, or with
     * status 400 (Bad Request) if the assuntoDTO is not valid, or with status 500 (Internal Server
     * Error) if the assuntoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PutMapping("/assunto")
    public ResponseEntity<AssuntoDTO> updateAssunto(@Valid @RequestBody AssuntoDTO assuntoDTO)
        throws URISyntaxException {
        log.debug("REST request to update Assunto : {}", assuntoDTO);
        throwsBadRequestIfHasNoId(assuntoDTO);
        var result = assuntoService.save(assuntoDTO);
        var idString = assuntoDTO.getId().toString();
        var headerUpdateAlert = createEntityUpdateAlert(ENTITY_NAME, idString);
        return ResponseEntity.ok()
                             .headers(headerUpdateAlert)
                             .body(result);
    }

    private void throwsBadRequestIfHasNoId(AssuntoDTO assuntoDTO) {
        Supplier<BadRequestAlertException> throwBadRequestExcpetion = () -> {
            var msgError = "An existing Assunto must have an id";
            var badRequestAlertException =
                new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            log.error(msgError, badRequestAlertException);
            return badRequestAlertException;
        };
        Predicate<AssuntoDTO> hasId = assunto -> Objects.nonNull(assunto.getId());
        Optional.of(assuntoDTO)
                .filter(hasId)
                .orElseThrow(throwBadRequestExcpetion);
    }

    /**
     * GET /assuntos : get all the assuntos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assuntos in body
     */
    @Timed
    @GetMapping("/assunto")
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
        var page = assuntoService.findByParams(dto, pageable);
        var headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/queryAssuntos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET /assuntos/:id : get the "id" assunto.
     *
     * @param id the id of the assuntoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assuntoDTO, or with status
     * 404 (Not Found)
     */
    @Timed
    @GetMapping("/assunto/{id}")
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
    @DeleteMapping("/assunto/{id}")
    public ResponseEntity<Void> deleteAssunto(@PathVariable Long id) {
        log.debug("REST request to delete Assunto : {}", id);
        assuntoService.delete(id);
        var headerDeletionAlert = createEntityDeletionAlert(ENTITY_NAME, id.toString());
        return ResponseEntity.ok()
                             .headers(headerDeletionAlert)
                             .build();
    }
    
    @ResponseStatus(value = HttpStatus.CONFLICT,
        reason = "Não é possível excluir esse 'Assunto', pois ele está associado a uma ou mais Coordenações Jurídicas")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict(HttpServletRequest httpRequest,
        DataIntegrityViolationException dataIntegrityViolation) {
        var requestURI = httpRequest.getRequestURI();
        log.warn(
            "Attempt to call '{}' failed. 'Assunto' cannot be excluded because is tied to one or more: {}",
            requestURI, CoordenacaoJuridica.class.getCanonicalName());
    }
}
