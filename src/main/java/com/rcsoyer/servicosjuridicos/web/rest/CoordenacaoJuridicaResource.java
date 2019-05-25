package com.rcsoyer.servicosjuridicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.CoordenacaoJuridicaService;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import com.rcsoyer.servicosjuridicos.service.dto.PageableDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing CoordenacaoJuridica.
 */
@RestController
@RequestMapping("/api")
public class CoordenacaoJuridicaResource {
    
    private final CoordenacaoJuridicaService service;
    private static final String ENTITY_NAME = "coordenacaoJuridica";
    private final Logger log = LoggerFactory.getLogger(CoordenacaoJuridicaResource.class);
    
    public CoordenacaoJuridicaResource(CoordenacaoJuridicaService coordenacaoJuridicaService) {
        this.service = coordenacaoJuridicaService;
    }
    
    /**
     * POST /coordenacao-juridicas : Create a new coordenacaoJuridica.
     *
     * @param dto the coordenacaoJuridicaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coordenacaoJuridicaDTO, or with status
     * 400 (Bad Request) if the coordenacaoJuridica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping("/coordenacao-juridica")
    public ResponseEntity<CoordenacaoJuridicaDTO> createCoordenacaoJuridica(
        @Valid @RequestBody CoordenacaoJuridicaDTO dto) throws URISyntaxException {
        log.debug("REST request to save CoordenacaoJuridica : {}", dto);
        throwsBadRequestIfHasId(dto);
        CoordenacaoJuridicaDTO result = service.save(dto);
        Long resultId = result.getId();
        URI uriCreate = new URI("/api/coordenacao-juridicas/" + resultId);
        HttpHeaders headerCreationAlert = HeaderUtil.entityCreationAlert(ENTITY_NAME, resultId.toString());
        return ResponseEntity.created(uriCreate)
                             .headers(headerCreationAlert)
                             .body(result);
    }
    
    private void throwsBadRequestIfHasId(CoordenacaoJuridicaDTO dto) {
        Supplier<BadRequestAlertException> throwBadRequestExcpetion = () -> {
            String msgError = "A new coordenacaoJuridica cannot already have an ID";
            BadRequestAlertException badRequestAlertException =
                new BadRequestAlertException(msgError, ENTITY_NAME, "idexists");
            log.error(msgError, badRequestAlertException);
            return badRequestAlertException;
        };
        Predicate<CoordenacaoJuridicaDTO> hasNoId = coordenacao -> Objects.isNull(coordenacao.getId());
        Optional.of(dto)
                .filter(hasNoId)
                .orElseThrow(throwBadRequestExcpetion);
    }
    
    /**
     * PUT /coordenacao-juridicas : Updates an existing coordenacaoJuridica.
     *
     * @param dto the coordenacaoJuridicaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coordenacaoJuridicaDTO, or with status
     * 400 (Bad Request) if the coordenacaoJuridicaDTO is not valid, or with status 500 (Internal Server Error) if the
     * coordenacaoJuridicaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PutMapping("/coordenacao-juridica")
    public ResponseEntity<CoordenacaoJuridicaDTO> updateCoordenacaoJuridica(
        @Valid @RequestBody CoordenacaoJuridicaDTO dto) throws URISyntaxException {
        log.debug("REST request to update CoordenacaoJuridica : {}", dto);
        throwsBadRequestIfHasNoId(dto);
        CoordenacaoJuridicaDTO result = service.save(dto);
        String idString = dto.getId().toString();
        HttpHeaders headerUpdateAlert = HeaderUtil.entityUpdateAlert(ENTITY_NAME, idString);
        return ResponseEntity.ok()
                             .headers(headerUpdateAlert)
                             .body(result);
    }
    
    private void throwsBadRequestIfHasNoId(CoordenacaoJuridicaDTO dto) {
        Supplier<BadRequestAlertException> throwBadRequestExcpetion = () -> {
            String msgError = "Invalid id";
            BadRequestAlertException badRequestAlertException =
                new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            log.error(msgError, badRequestAlertException);
            return badRequestAlertException;
        };
        Predicate<CoordenacaoJuridicaDTO> hasId = assunto -> Objects.nonNull(assunto.getId());
        Optional.of(dto)
                .filter(hasId)
                .orElseThrow(throwBadRequestExcpetion);
    }
    
    @Timed
    @GetMapping("/getCoordenacoes")
    public ResponseEntity<List<CoordenacaoJuridicaDTO>> getCoordenacoes(
        @RequestParam("dto") CoordenacaoJuridicaDTO dto,
        @RequestParam("pageable") PageableDTO pageableDTO) {
        log.debug("REST request to get a page of CoordenacaoJuridicas");
        Pageable pageable = pageableDTO.getPageable();
        Page<CoordenacaoJuridicaDTO> page = service.findByParams(dto, pageable);
        HttpHeaders headers =
            PaginationUtil.generatePaginationHttpHeaders(page, "/api/coordenacoes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET /coordenacao-juridicas/:id : get the "id" coordenacaoJuridica.
     *
     * @param id the id of the coordenacaoJuridicaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coordenacaoJuridicaDTO, or with status 404 (Not
     * Found)
     */
    @Timed
    @GetMapping("/coordenacao-juridica/{id}")
    public ResponseEntity<CoordenacaoJuridicaDTO> getCoordenacaoJuridica(@PathVariable Long id) {
        log.debug("REST request to get CoordenacaoJuridica : {}", id);
        Optional<CoordenacaoJuridicaDTO> coordenacaoJuridicaDTO = service.findOne(id);
        return ResponseUtil.wrapOrNotFound(coordenacaoJuridicaDTO);
    }
    
    /**
     * DELETE /coordenacao-juridicas/:id : delete the "id" coordenacaoJuridica.
     *
     * @param id the id of the coordenacaoJuridicaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/coordenacao-juridica/{id}")
    public ResponseEntity<Void> deleteCoordenacaoJuridica(@PathVariable Long id) {
        log.debug("REST request to delete CoordenacaoJuridica : {}", id);
        service.delete(id);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.entityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
