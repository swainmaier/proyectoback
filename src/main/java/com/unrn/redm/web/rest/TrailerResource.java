package com.unrn.redm.web.rest;

import com.unrn.redm.domain.Trailer;
import com.unrn.redm.service.TrailerService;
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
 * REST controller for managing {@link com.unrn.redm.domain.Trailer}.
 */
@RestController
@RequestMapping("/api")
public class TrailerResource {

    private final Logger log = LoggerFactory.getLogger(TrailerResource.class);

    private static final String ENTITY_NAME = "trailer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrailerService trailerService;

    public TrailerResource(TrailerService trailerService) {
        this.trailerService = trailerService;
    }

    /**
     * {@code POST  /trailers} : Create a new trailer.
     *
     * @param trailer the trailer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trailer, or with status {@code 400 (Bad Request)} if the trailer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trailers")
    public ResponseEntity<Trailer> createTrailer(@RequestBody Trailer trailer) throws URISyntaxException {
        log.debug("REST request to save Trailer : {}", trailer);
        if (trailer.getId() != null) {
            throw new BadRequestAlertException("A new trailer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trailer result = trailerService.save(trailer);
        return ResponseEntity.created(new URI("/api/trailers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trailers} : Updates an existing trailer.
     *
     * @param trailer the trailer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trailer,
     * or with status {@code 400 (Bad Request)} if the trailer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trailer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trailers")
    public ResponseEntity<Trailer> updateTrailer(@RequestBody Trailer trailer) throws URISyntaxException {
        log.debug("REST request to update Trailer : {}", trailer);
        if (trailer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trailer result = trailerService.save(trailer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trailer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trailers} : get all the trailers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trailers in body.
     */
    @GetMapping("/trailers")
    public List<Trailer> getAllTrailers() {
        log.debug("REST request to get all Trailers");
        return trailerService.findAll();
    }

    /**
     * {@code GET  /trailers/:id} : get the "id" trailer.
     *
     * @param id the id of the trailer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trailer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trailers/{id}")
    public ResponseEntity<Trailer> getTrailer(@PathVariable Long id) {
        log.debug("REST request to get Trailer : {}", id);
        Optional<Trailer> trailer = trailerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trailer);
    }

    /**
     * {@code DELETE  /trailers/:id} : delete the "id" trailer.
     *
     * @param id the id of the trailer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trailers/{id}")
    public ResponseEntity<Void> deleteTrailer(@PathVariable Long id) {
        log.debug("REST request to delete Trailer : {}", id);
        trailerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
