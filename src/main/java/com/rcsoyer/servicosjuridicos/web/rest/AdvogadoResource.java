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
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Advogado.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class AdvogadoResource {
    
    private final AdvogadoService service;
    private static final String ENTITY_NAME = "advogado";
    
    public AdvogadoResource(AdvogadoService advogadoService) {
        this.service = advogadoService;
    }
    
    /**
     * POST /advogados : Create a new advogado.
     *
     * @param advogadoDTO the advogadoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new advogadoDTO, or with status 400 (Bad
     * Request) if the advogado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping("/advogado")
    public ResponseEntity<AdvogadoDTO> create(@Valid @RequestBody AdvogadoDTO advogadoDTO)
        throws URISyntaxException {
        log.debug("REST request to save Advogado : {}", advogadoDTO);
        throwsBadRequestIfHasId(advogadoDTO);
        var createdAdvogado = service.save(advogadoDTO);
        var resultId = createdAdvogado.getId();
        var location = new URI("/api/advogado/" + resultId);
        var entityCreationAlert = entityCreationAlert(ENTITY_NAME, resultId.toString());
        return ResponseEntity.created(location)
                             .headers(entityCreationAlert)
                             .body(createdAdvogado);
    }
    
    /**
     * PUT /advogados : Updates an existing advogado.
     *
     * @param advogadoDTO the advogadoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated advogadoDTO, or with status 400 (Bad
     * Request) if the advogadoDTO is not valid, or with status 500 (Internal Server Error) if the advogadoDTO couldn't
     * be updated
     */
    @Timed
    @PutMapping("/advogado")
    public ResponseEntity<AdvogadoDTO> updateAdvogado(@Valid @RequestBody AdvogadoDTO advogadoDTO) {
        log.debug("REST request to update Advogado : {}", advogadoDTO);
        throwsBadRequestIfHasNoId(advogadoDTO);
        var updatedAdvogado = service.save(advogadoDTO);
        var entityUpdateAlert = entityUpdateAlert(ENTITY_NAME, updatedAdvogado.getId().toString());
        return ResponseEntity.ok()
                             .headers(entityUpdateAlert)
                             .body(updatedAdvogado);
    }
    
    /**
     * GET /advogados : get all the advogados.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of advogados in body
     */
    @Timed
    @GetMapping("/advogado")
    public ResponseEntity<List<AdvogadoDTO>> getAllAdvogados(Pageable pageable) {
        log.debug("REST request to get a page of Advogados");
        var page = service.findAll(pageable);
        var headers = generatePaginationHttpHeaders(page, "/api/advogado");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @Timed
    @GetMapping("/getAdvogados")
    public ResponseEntity<List<AdvogadoDTO>> getAdvogados(@RequestParam("dto") AdvogadoDTO dto,
                                                          @RequestParam("pageable") @Valid PageableDTO pageableDTO) {
        log.debug("REST request to get a page of Advogados by input params");
        var pageable = pageableDTO.getPageable();
        var page = service.findByParams(dto, pageable);
        var headers = generatePaginationHttpHeaders(page, "/api/getAdvogados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET /advogados/:id : get the "id" advogado.
     *
     * @param id the id of the advogadoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the advogadoDTO, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/advogado/{id}")
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
    @DeleteMapping("/advogado/{id}")
    public ResponseEntity<Void> deleteAdvogado(@PathVariable @Valid @Min(1) Long id) {
        log.debug("REST request to delete Advogado : {}", id);
        service.delete(id);
        var entityDeletionAlert = entityDeletionAlert(ENTITY_NAME, id.toString());
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert)
                             .build();
    }
    
    private void throwsBadRequestIfHasId(final AdvogadoDTO dto) {
        Supplier<BadRequestAlertException> throwBadRequestExcpetion = () -> {
            var msgError = "A new Advogado cannot already have an ID";
            var badRequestAlertException =
                new BadRequestAlertException(msgError, ENTITY_NAME, "idexists");
            log.error(msgError, badRequestAlertException);
            return badRequestAlertException;
        };
        Predicate<AdvogadoDTO> hasNoId = advogado -> isNull(advogado.getId());
        Optional.of(dto)
                .filter(hasNoId)
                .orElseThrow(throwBadRequestExcpetion);
    }
    
    private void throwsBadRequestIfHasNoId(final AdvogadoDTO advogadoDTO) {
        Supplier<BadRequestAlertException> throwBadRequestExcpetion = () -> {
            var msgError = "An existing Advogado must have an id";
            var badRequestAlertException =
                new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            log.error(msgError, badRequestAlertException);
            return badRequestAlertException;
        };
        Predicate<AdvogadoDTO> hasId = advogado -> nonNull(advogado.getId());
        Optional.of(advogadoDTO)
                .filter(hasId)
                .orElseThrow(throwBadRequestExcpetion);
    }
}
