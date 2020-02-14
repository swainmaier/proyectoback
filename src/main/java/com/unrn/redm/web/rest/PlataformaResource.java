package com.unrn.redm.web.rest;

import com.unrn.redm.domain.Plataforma;
import com.unrn.redm.service.PlataformaService;
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
 * REST controller for managing {@link com.unrn.redm.domain.Plataforma}.
 */
@RestController
@RequestMapping("/api")
public class PlataformaResource {

    private final Logger log = LoggerFactory.getLogger(PlataformaResource.class);

    private static final String ENTITY_NAME = "plataforma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlataformaService plataformaService;

    public PlataformaResource(PlataformaService plataformaService) {
        this.plataformaService = plataformaService;
    }

    /**
     * {@code POST  /plataformas} : Create a new plataforma.
     *
     * @param plataforma the plataforma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plataforma, or with status {@code 400 (Bad Request)} if the plataforma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plataformas")
    public ResponseEntity<Plataforma> createPlataforma(@RequestBody Plataforma plataforma) throws URISyntaxException {
        log.debug("REST request to save Plataforma : {}", plataforma);
        if (plataforma.getId() != null) {
            throw new BadRequestAlertException("A new plataforma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plataforma result = plataformaService.save(plataforma);
        return ResponseEntity.created(new URI("/api/plataformas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plataformas} : Updates an existing plataforma.
     *
     * @param plataforma the plataforma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plataforma,
     * or with status {@code 400 (Bad Request)} if the plataforma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plataforma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plataformas")
    public ResponseEntity<Plataforma> updatePlataforma(@RequestBody Plataforma plataforma) throws URISyntaxException {
        log.debug("REST request to update Plataforma : {}", plataforma);
        if (plataforma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Plataforma result = plataformaService.save(plataforma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plataforma.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plataformas} : get all the plataformas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plataformas in body.
     */
    @GetMapping("/plataformas")
    public List<Plataforma> getAllPlataformas() {
        log.debug("REST request to get all Plataformas");
        return plataformaService.findAll();
    }

    /**
     * {@code GET  /plataformas/:id} : get the "id" plataforma.
     *
     * @param id the id of the plataforma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plataforma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plataformas/{id}")
    public ResponseEntity<Plataforma> getPlataforma(@PathVariable Long id) {
        log.debug("REST request to get Plataforma : {}", id);
        Optional<Plataforma> plataforma = plataformaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plataforma);
    }

    /**
     * {@code DELETE  /plataformas/:id} : delete the "id" plataforma.
     *
     * @param id the id of the plataforma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plataformas/{id}")
    public ResponseEntity<Void> deletePlataforma(@PathVariable Long id) {
        log.debug("REST request to delete Plataforma : {}", id);
        plataformaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
