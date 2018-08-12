package com.rcsoyer.servicosjuridicos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.servicosjuridicos.service.AdvogadoDgCoordenacaoService;
import com.rcsoyer.servicosjuridicos.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.servicosjuridicos.web.rest.util.HeaderUtil;
import com.rcsoyer.servicosjuridicos.web.rest.util.PaginationUtil;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AdvogadoDgCoordenacao.
 */
@RestController
@RequestMapping("/api")
public class AdvogadoDgCoordenacaoResource {

    private final Logger log = LoggerFactory.getLogger(AdvogadoDgCoordenacaoResource.class);

    private static final String ENTITY_NAME = "advogadoDgCoordenacao";

    private final AdvogadoDgCoordenacaoService advogadoDgCoordenacaoService;

    public AdvogadoDgCoordenacaoResource(AdvogadoDgCoordenacaoService advogadoDgCoordenacaoService) {
        this.advogadoDgCoordenacaoService = advogadoDgCoordenacaoService;
    }

    /**
     * POST  /advogado-dg-coordenacaos : Create a new advogadoDgCoordenacao.
     *
     * @param advogadoDgCoordenacaoDTO the advogadoDgCoordenacaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new advogadoDgCoordenacaoDTO, or with status 400 (Bad Request) if the advogadoDgCoordenacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/advogado-dg-coordenacaos")
    @Timed
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> createAdvogadoDgCoordenacao(@Valid @RequestBody AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO) throws URISyntaxException {
        log.debug("REST request to save AdvogadoDgCoordenacao : {}", advogadoDgCoordenacaoDTO);
        if (advogadoDgCoordenacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new advogadoDgCoordenacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvogadoDgCoordenacaoDTO result = advogadoDgCoordenacaoService.save(advogadoDgCoordenacaoDTO);
        return ResponseEntity.created(new URI("/api/advogado-dg-coordenacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /advogado-dg-coordenacaos : Updates an existing advogadoDgCoordenacao.
     *
     * @param advogadoDgCoordenacaoDTO the advogadoDgCoordenacaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated advogadoDgCoordenacaoDTO,
     * or with status 400 (Bad Request) if the advogadoDgCoordenacaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the advogadoDgCoordenacaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/advogado-dg-coordenacaos")
    @Timed
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> updateAdvogadoDgCoordenacao(@Valid @RequestBody AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO) throws URISyntaxException {
        log.debug("REST request to update AdvogadoDgCoordenacao : {}", advogadoDgCoordenacaoDTO);
        if (advogadoDgCoordenacaoDTO.getId() == null) {
            return createAdvogadoDgCoordenacao(advogadoDgCoordenacaoDTO);
        }
        AdvogadoDgCoordenacaoDTO result = advogadoDgCoordenacaoService.save(advogadoDgCoordenacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, advogadoDgCoordenacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /advogado-dg-coordenacaos : get all the advogadoDgCoordenacaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of advogadoDgCoordenacaos in body
     */
    @GetMapping("/advogado-dg-coordenacaos")
    @Timed
    public ResponseEntity<List<AdvogadoDgCoordenacaoDTO>> getAllAdvogadoDgCoordenacaos(Pageable pageable) {
        log.debug("REST request to get a page of AdvogadoDgCoordenacaos");
        Page<AdvogadoDgCoordenacaoDTO> page = advogadoDgCoordenacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/advogado-dg-coordenacaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /advogado-dg-coordenacaos/:id : get the "id" advogadoDgCoordenacao.
     *
     * @param id the id of the advogadoDgCoordenacaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the advogadoDgCoordenacaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/advogado-dg-coordenacaos/{id}")
    @Timed
    public ResponseEntity<AdvogadoDgCoordenacaoDTO> getAdvogadoDgCoordenacao(@PathVariable Long id) {
        log.debug("REST request to get AdvogadoDgCoordenacao : {}", id);
        AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO = advogadoDgCoordenacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(advogadoDgCoordenacaoDTO));
    }

    /**
     * DELETE  /advogado-dg-coordenacaos/:id : delete the "id" advogadoDgCoordenacao.
     *
     * @param id the id of the advogadoDgCoordenacaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/advogado-dg-coordenacaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdvogadoDgCoordenacao(@PathVariable Long id) {
        log.debug("REST request to delete AdvogadoDgCoordenacao : {}", id);
        advogadoDgCoordenacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
