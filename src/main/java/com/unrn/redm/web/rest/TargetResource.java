package com.unrn.redm.web.rest;

import com.unrn.redm.domain.Target;
import com.unrn.redm.service.TargetService;
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
 * REST controller for managing {@link com.unrn.redm.domain.Target}.
 */
@RestController
@RequestMapping("/api")
public class TargetResource {

    private final Logger log = LoggerFactory.getLogger(TargetResource.class);

    private static final String ENTITY_NAME = "target";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TargetService targetService;

    public TargetResource(TargetService targetService) {
        this.targetService = targetService;
    }

    /**
     * {@code POST  /targets} : Create a new target.
     *
     * @param target the target to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new target, or with status {@code 400 (Bad Request)} if the target has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/targets")
    public ResponseEntity<Target> createTarget(@RequestBody Target target) throws URISyntaxException {
        log.debug("REST request to save Target : {}", target);
        if (target.getId() != null) {
            throw new BadRequestAlertException("A new target cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Target result = targetService.save(target);
        return ResponseEntity.created(new URI("/api/targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /targets} : Updates an existing target.
     *
     * @param target the target to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated target,
     * or with status {@code 400 (Bad Request)} if the target is not valid,
     * or with status {@code 500 (Internal Server Error)} if the target couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/targets")
    public ResponseEntity<Target> updateTarget(@RequestBody Target target) throws URISyntaxException {
        log.debug("REST request to update Target : {}", target);
        if (target.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Target result = targetService.save(target);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, target.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /targets} : get all the targets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of targets in body.
     */
    @GetMapping("/targets")
    public List<Target> getAllTargets() {
        log.debug("REST request to get all Targets");
        return targetService.findAll();
    }

    /**
     * {@code GET  /targets/:id} : get the "id" target.
     *
     * @param id the id of the target to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the target, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/targets/{id}")
    public ResponseEntity<Target> getTarget(@PathVariable Long id) {
        log.debug("REST request to get Target : {}", id);
        Optional<Target> target = targetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(target);
    }

    /**
     * {@code DELETE  /targets/:id} : delete the "id" target.
     *
     * @param id the id of the target to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/targets/{id}")
    public ResponseEntity<Void> deleteTarget(@PathVariable Long id) {
        log.debug("REST request to delete Target : {}", id);
        targetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
