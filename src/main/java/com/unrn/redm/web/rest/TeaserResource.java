package com.unrn.redm.web.rest;

import com.unrn.redm.domain.Teaser;
import com.unrn.redm.service.TeaserService;
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
 * REST controller for managing {@link com.unrn.redm.domain.Teaser}.
 */
@RestController
@RequestMapping("/api")
public class TeaserResource {

    private final Logger log = LoggerFactory.getLogger(TeaserResource.class);

    private static final String ENTITY_NAME = "teaser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeaserService teaserService;

    public TeaserResource(TeaserService teaserService) {
        this.teaserService = teaserService;
    }

    /**
     * {@code POST  /teasers} : Create a new teaser.
     *
     * @param teaser the teaser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teaser, or with status {@code 400 (Bad Request)} if the teaser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teasers")
    public ResponseEntity<Teaser> createTeaser(@RequestBody Teaser teaser) throws URISyntaxException {
        log.debug("REST request to save Teaser : {}", teaser);
        if (teaser.getId() != null) {
            throw new BadRequestAlertException("A new teaser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Teaser result = teaserService.save(teaser);
        return ResponseEntity.created(new URI("/api/teasers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teasers} : Updates an existing teaser.
     *
     * @param teaser the teaser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teaser,
     * or with status {@code 400 (Bad Request)} if the teaser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teaser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teasers")
    public ResponseEntity<Teaser> updateTeaser(@RequestBody Teaser teaser) throws URISyntaxException {
        log.debug("REST request to update Teaser : {}", teaser);
        if (teaser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Teaser result = teaserService.save(teaser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teaser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /teasers} : get all the teasers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teasers in body.
     */
    @GetMapping("/teasers")
    public List<Teaser> getAllTeasers() {
        log.debug("REST request to get all Teasers");
        return teaserService.findAll();
    }

    /**
     * {@code GET  /teasers/:id} : get the "id" teaser.
     *
     * @param id the id of the teaser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teaser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teasers/{id}")
    public ResponseEntity<Teaser> getTeaser(@PathVariable Long id) {
        log.debug("REST request to get Teaser : {}", id);
        Optional<Teaser> teaser = teaserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teaser);
    }

    /**
     * {@code DELETE  /teasers/:id} : delete the "id" teaser.
     *
     * @param id the id of the teaser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teasers/{id}")
    public ResponseEntity<Void> deleteTeaser(@PathVariable Long id) {
        log.debug("REST request to delete Teaser : {}", id);
        teaserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
