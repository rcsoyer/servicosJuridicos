package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityCreationAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil.generatePaginationHttpHeaders;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.ModalidadeService;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import com.rcsoyer.servicosjuridicos.service.dto.validationgroups.ModalidadeOnCreate;
import com.rcsoyer.servicosjuridicos.service.dto.validationgroups.ModalidadeOnUpdate;
import io.github.jhipster.web.util.ResponseUtil;
import io.vavr.Function1;
import java.net.URI;
import java.util.List;
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
 * REST controller for managing Modalidade.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/modalidade")
public class ModalidadeResource {
    
    private static final String ENTITY_NAME = "modalidade";
    private final ModalidadeService service;
    
    public ModalidadeResource(final ModalidadeService modalidadeService) {
        this.service = modalidadeService;
    }
    
    /**
     * POST /modalidades : Create a new modalidade.
     *
     * @param dto the modalidadeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modalidadeDTO, or with status 400 (Bad
     * Request) if the modalidade has already an ID
     */
    @Timed
    @PostMapping
    public ResponseEntity<ModalidadeDTO> createModalidade(
        @Validated(ModalidadeOnCreate.class) @RequestBody final ModalidadeDTO dto) {
        log.debug("REST request to create a Modalidade: {}", dto);
        return Function1.of(service::save)
                        .andThen(result -> ResponseEntity.created(URI.create("/api/modalidades/" + result.getId()))
                                                         .headers(entityCreationAlert(ENTITY_NAME, result.getId()
                                                                                                         .toString()))
                                                         .body(result))
                        .apply(dto);
    }
    
    /**
     * PUT /modalidades : Updates an existing modalidade.
     *
     * @param dto the modalidadeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modalidadeDTO, or with status 400 (Bad
     * Request) if the modalidadeDTO is not valid, or with status 500 (Internal Server Error) if the modalidadeDTO
     * couldn't be updated
     */
    @Timed
    @PutMapping
    public ResponseEntity<ModalidadeDTO> updateModalidade(
        @Validated(ModalidadeOnUpdate.class) @RequestBody final ModalidadeDTO dto) {
        log.debug("REST request to update Modalidade: {}", dto);
        return Function1.of(service::save)
                        .andThen(result -> ResponseEntity.ok()
                                                         .headers(
                                                             entityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                                                         .body(result))
                        .apply(dto);
    }
    
    @Timed
    @GetMapping
    public ResponseEntity<List<ModalidadeDTO>> getModalidades(final ModalidadeDTO searchParams,
                                                              final Pageable pageable) {
        log.debug("REST request to get a page of Modalidades by search params: searchParams={} and pageable={}",
                  searchParams, pageable);
        final Page<ModalidadeDTO> modalidades = service.seekByParams(searchParams, pageable);
        return ResponseEntity.ok()
                             .headers(generatePaginationHttpHeaders(modalidades, "/api/modalidade"))
                             .body(modalidades.getContent());
    }
    
    /**
     * GET /modalidades/:id : get the "id" modalidade.
     *
     * @param id the id of the modalidadeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modalidadeDTO, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/{id}")
    public ResponseEntity<ModalidadeDTO> getModalidade(@PathVariable @Min(1L) Long id) {
        log.debug("REST request to get Modalidade: {}", id);
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }
    
    /**
     * DELETE /modalidades/:id : delete the "id" modalidade.
     *
     * @param id the id of the modalidadeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModalidade(@PathVariable @Min(1L) Long id) {
        log.debug("REST request to delete Modalidade: {}", id);
        service.delete(id);
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }
}
