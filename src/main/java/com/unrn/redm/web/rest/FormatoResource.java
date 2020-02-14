package com.unrn.redm.web.rest;

import com.unrn.redm.domain.Formato;
import com.unrn.redm.service.FormatoService;
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
 * REST controller for managing {@link com.unrn.redm.domain.Formato}.
 */
@RestController
@RequestMapping("/api")
public class FormatoResource {

    private final Logger log = LoggerFactory.getLogger(FormatoResource.class);

    private static final String ENTITY_NAME = "formato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormatoService formatoService;

    public FormatoResource(FormatoService formatoService) {
        this.formatoService = formatoService;
    }

    /**
     * {@code POST  /formatoes} : Create a new formato.
     *
     * @param formato the formato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formato, or with status {@code 400 (Bad Request)} if the formato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formatoes")
    public ResponseEntity<Formato> createFormato(@RequestBody Formato formato) throws URISyntaxException {
        log.debug("REST request to save Formato : {}", formato);
        if (formato.getId() != null) {
            throw new BadRequestAlertException("A new formato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Formato result = formatoService.save(formato);
        return ResponseEntity.created(new URI("/api/formatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formatoes} : Updates an existing formato.
     *
     * @param formato the formato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formato,
     * or with status {@code 400 (Bad Request)} if the formato is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formatoes")
    public ResponseEntity<Formato> updateFormato(@RequestBody Formato formato) throws URISyntaxException {
        log.debug("REST request to update Formato : {}", formato);
        if (formato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Formato result = formatoService.save(formato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formato.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /formatoes} : get all the formatoes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formatoes in body.
     */
    @GetMapping("/formatoes")
    public List<Formato> getAllFormatoes() {
        log.debug("REST request to get all Formatoes");
        return formatoService.findAll();
    }

    /**
     * {@code GET  /formatoes/:id} : get the "id" formato.
     *
     * @param id the id of the formato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formatoes/{id}")
    public ResponseEntity<Formato> getFormato(@PathVariable Long id) {
        log.debug("REST request to get Formato : {}", id);
        Optional<Formato> formato = formatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formato);
    }

    /**
     * {@code DELETE  /formatoes/:id} : delete the "id" formato.
     *
     * @param id the id of the formato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formatoes/{id}")
    public ResponseEntity<Void> deleteFormato(@PathVariable Long id) {
        log.debug("REST request to delete Formato : {}", id);
        formatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
