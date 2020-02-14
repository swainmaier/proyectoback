package com.unrn.redm.web.rest;

import com.unrn.redm.domain.Capitulo;
import com.unrn.redm.service.CapituloService;
import com.unrn.redm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.unrn.redm.domain.Capitulo}.
 */
@RestController
@RequestMapping("/api")
public class CapituloResource {

    private final Logger log = LoggerFactory.getLogger(CapituloResource.class);

    private static final String ENTITY_NAME = "capitulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CapituloService capituloService;

    public CapituloResource(CapituloService capituloService) {
        this.capituloService = capituloService;
    }

    /**
     * {@code POST  /capitulos} : Create a new capitulo.
     *
     * @param capitulo the capitulo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new capitulo, or with status {@code 400 (Bad Request)} if the capitulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/capitulos")
    public ResponseEntity<Capitulo> createCapitulo(@RequestBody Capitulo capitulo) throws URISyntaxException {
        log.debug("REST request to save Capitulo : {}", capitulo);
        if (capitulo.getId() != null) {
            throw new BadRequestAlertException("A new capitulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Capitulo result = capituloService.save(capitulo);
        return ResponseEntity.created(new URI("/api/capitulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /capitulos} : Updates an existing capitulo.
     *
     * @param capitulo the capitulo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capitulo,
     * or with status {@code 400 (Bad Request)} if the capitulo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the capitulo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/capitulos")
    public ResponseEntity<Capitulo> updateCapitulo(@RequestBody Capitulo capitulo) throws URISyntaxException {
        log.debug("REST request to update Capitulo : {}", capitulo);
        if (capitulo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Capitulo result = capituloService.save(capitulo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, capitulo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /capitulos} : get all the capitulos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of capitulos in body.
     */
    @GetMapping("/capitulos")
    public ResponseEntity<List<Capitulo>> getAllCapitulos(Pageable pageable) {
        log.debug("REST request to get a page of Capitulos");
        Page<Capitulo> page = capituloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /capitulos/:id} : get the "id" capitulo.
     *
     * @param id the id of the capitulo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the capitulo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/capitulos/{id}")
    public ResponseEntity<Capitulo> getCapitulo(@PathVariable Long id) {
        log.debug("REST request to get Capitulo : {}", id);
        Optional<Capitulo> capitulo = capituloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(capitulo);
    }

    /**
     * {@code DELETE  /capitulos/:id} : delete the "id" capitulo.
     *
     * @param id the id of the capitulo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/capitulos/{id}")
    public ResponseEntity<Void> deleteCapitulo(@PathVariable Long id) {
        log.debug("REST request to delete Capitulo : {}", id);
        capituloService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
