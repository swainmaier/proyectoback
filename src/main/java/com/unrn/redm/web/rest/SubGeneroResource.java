package com.unrn.redm.web.rest;

import com.unrn.redm.domain.SubGenero;
import com.unrn.redm.service.SubGeneroService;
import com.unrn.redm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.unrn.redm.domain.SubGenero}.
 */
@RestController
@RequestMapping("/api")
public class SubGeneroResource {

    private final Logger log = LoggerFactory.getLogger(SubGeneroResource.class);

    private static final String ENTITY_NAME = "subGenero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubGeneroService subGeneroService;

    public SubGeneroResource(SubGeneroService subGeneroService) {
        this.subGeneroService = subGeneroService;
    }

    /**
     * {@code POST  /sub-generos} : Create a new subGenero.
     *
     * @param subGenero the subGenero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subGenero, or with status {@code 400 (Bad Request)} if the subGenero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-generos")
    public ResponseEntity<SubGenero> createSubGenero(@RequestBody SubGenero subGenero) throws URISyntaxException {
        log.debug("REST request to save SubGenero : {}", subGenero);
        if (subGenero.getId() != null) {
            throw new BadRequestAlertException("A new subGenero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubGenero result = subGeneroService.save(subGenero);
        return ResponseEntity.created(new URI("/api/sub-generos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-generos} : Updates an existing subGenero.
     *
     * @param subGenero the subGenero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subGenero,
     * or with status {@code 400 (Bad Request)} if the subGenero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subGenero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-generos")
    public ResponseEntity<SubGenero> updateSubGenero(@RequestBody SubGenero subGenero) throws URISyntaxException {
        log.debug("REST request to update SubGenero : {}", subGenero);
        if (subGenero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubGenero result = subGeneroService.save(subGenero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subGenero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-generos} : get all the subGeneros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subGeneros in body.
     */
    @GetMapping("/sub-generos")
    public List<SubGenero> getAllSubGeneros() {
        log.debug("REST request to get all SubGeneros");
        return subGeneroService.findAll();
    }

    /**
     * {@code GET  /sub-generos/:id} : get the "id" subGenero.
     *
     * @param id the id of the subGenero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subGenero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-generos/{id}")
    public ResponseEntity<SubGenero> getSubGenero(@PathVariable Long id) {
        log.debug("REST request to get SubGenero : {}", id);
        Optional<SubGenero> subGenero = subGeneroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subGenero);
    }

    /**
     * {@code DELETE  /sub-generos/:id} : delete the "id" subGenero.
     *
     * @param id the id of the subGenero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-generos/{id}")
    public ResponseEntity<Void> deleteSubGenero(@PathVariable Long id) {
        log.debug("REST request to delete SubGenero : {}", id);
        subGeneroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
