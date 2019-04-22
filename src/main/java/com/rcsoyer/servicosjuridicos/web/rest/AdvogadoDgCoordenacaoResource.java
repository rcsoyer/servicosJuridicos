package com.rcsoyer.servicosjuridicos.web.rest;

import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityCreationAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityDeletionAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil.entityUpdateAlert;
import static com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil.generatePaginationHttpHeaders;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/api")
public class AdvogadoDgCoordenacaoResource {
    
    private static final String ENTITY_NAME = "advogadoDgCoordenacao";
    
    private final AdvogadoDgCoordenacaoService dgCoordenacaoService;
    
    public AdvogadoDgCoordenacaoResource(final AdvogadoDgCoordenacaoService advogadoDgCoordenacaoService) {
        this.dgCoordenacaoService = advogadoDgCoordenacaoService;
    }
    
    /**
     * POST  /advogado-dg-coordenacaos : Create a new advogadoDgCoordenacao.
     *
     * @param dto the advogadoDgCoordenacaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new advogadoDgCoordenacaoDTO, or with
     * status 400 (Bad Request) if the advogadoDgCoordenacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping("/advogado-dg-coordenacaos")
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> createAdvogadoDgCoordenacao(
        @Valid @RequestBody AdvogadoDgCoordenacaoDTO dto) throws URISyntaxException {
        log.info("REST request to save AdvogadoDgCoordenacao : {}", dto);
        throwsBadRequestIfHasId(dto);
        AdvogadoDgCoordenacaoDTO result = dgCoordenacaoService.save(dto);
        return ResponseEntity.created(new URI("/api/advogado-dg-coordenacaos/" + result.getId()))
                             .headers(entityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }
    
    /**
     * PUT  /advogado-dg-coordenacaos : Updates an existing advogadoDgCoordenacao.
     *
     * @param dto the advogadoDgCoordenacaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated advogadoDgCoordenacaoDTO, or with
     * status 400 (Bad Request) if the advogadoDgCoordenacaoDTO is not valid, or with status 500 (Internal Server Error)
     * if the advogadoDgCoordenacaoDTO couldn't be updated
     */
    @Timed
    @PutMapping("/advogado-dg-coordenacaos")
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> updateAdvogadoDgCoordenacao(
        @Valid @RequestBody AdvogadoDgCoordenacaoDTO dto) {
        log.info("REST request to update AdvogadoDgCoordenacao : {}", dto);
        throwsBadRequestIfHasNoId(dto);
        AdvogadoDgCoordenacaoDTO result = dgCoordenacaoService.save(dto);
        return ResponseEntity.ok()
                             .headers(entityUpdateAlert(ENTITY_NAME, dto.getId().toString()))
                             .body(result);
    }
    
    /**
     * GET  /advogado-dg-coordenacaos : get all the advogadoDgCoordenacaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of advogadoDgCoordenacaos in body
     */
    @Timed
    @GetMapping("/advogado-dg-coordenacaos")
    public ResponseEntity<List<AdvogadoDgCoordenacaoDTO>> getAllAdvogadoDgCoordenacaos(
        Pageable pageable) {
        log.debug("REST request to get a page of AdvogadoDgCoordenacaos");
        Page<AdvogadoDgCoordenacaoDTO> page = dgCoordenacaoService.findAll(pageable);
        HttpHeaders headers = generatePaginationHttpHeaders(page, "/api/advogado-dg-coordenacaos");
        return ResponseEntity.ok()
                             .headers(headers)
                             .body(page.getContent());
    }
    
    /**
     * GET  /advogado-dg-coordenacaos/:id : get the "id" advogadoDgCoordenacao.
     *
     * @param id the id of the advogadoDgCoordenacaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the advogadoDgCoordenacaoDTO, or with status 404
     * (Not Found)
     */
    @Timed
    @GetMapping("/advogado-dg-coordenacaos/{id}")
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> getAdvogadoDgCoordenacao(@PathVariable Long id) {
        log.info("REST request to get AdvogadoDgCoordenacao : {}", id);
        return ResponseUtil.wrapOrNotFound(dgCoordenacaoService.findOne(id));
    }
    
    /**
     * DELETE  /advogado-dg-coordenacaos/:id : delete the "id" advogadoDgCoordenacao.
     *
     * @param id the id of the advogadoDgCoordenacaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/advogado-dg-coordenacaos/{id}")
    public ResponseEntity<Void> deleteAdvogadoDgCoordenacao(@PathVariable Long id) {
        log.info("REST request to delete AdvogadoDgCoordenacao : {}", id);
        dgCoordenacaoService.delete(id);
        return ResponseEntity.ok()
                             .headers(entityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }
    
    private void throwsBadRequestIfHasId(final AdvogadoDgCoordenacaoDTO dto) {
        if (nonNull(dto.getId())) {
            var msgError = "A new AdvogadoDgCoordenacao cannot already have an ID";
            throw new BadRequestAlertException(msgError, ENTITY_NAME, "idexists");
        }
    }
    
    private void throwsBadRequestIfHasNoId(final AdvogadoDgCoordenacaoDTO dto) {
        if (isNull(dto.getId())) {
            var errorMsg = "A existing AdvogadoDgCoordenacao must have an ID";
            throw new BadRequestAlertException(errorMsg, ENTITY_NAME, "idnull");
        }
    }
}
