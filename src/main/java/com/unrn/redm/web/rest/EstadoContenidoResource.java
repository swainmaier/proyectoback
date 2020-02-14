package com.unrn.redm.web.rest;

import com.unrn.redm.domain.EstadoContenido;
import com.unrn.redm.service.EstadoContenidoService;
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
 * REST controller for managing {@link com.unrn.redm.domain.EstadoContenido}.
 */
@RestController
@RequestMapping("/api")
public class EstadoContenidoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoContenidoResource.class);

    private static final String ENTITY_NAME = "estadoContenido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoContenidoService estadoContenidoService;

    public EstadoContenidoResource(EstadoContenidoService estadoContenidoService) {
        this.estadoContenidoService = estadoContenidoService;
    }

    /**
     * {@code POST  /estado-contenidos} : Create a new estadoContenido.
     *
     * @param estadoContenido the estadoContenido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoContenido, or with status {@code 400 (Bad Request)} if the estadoContenido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-contenidos")
    public ResponseEntity<EstadoContenido> createEstadoContenido(@RequestBody EstadoContenido estadoContenido) throws URISyntaxException {
        log.debug("REST request to save EstadoContenido : {}", estadoContenido);
        if (estadoContenido.getId() != null) {
            throw new BadRequestAlertException("A new estadoContenido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoContenido result = estadoContenidoService.save(estadoContenido);
        return ResponseEntity.created(new URI("/api/estado-contenidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-contenidos} : Updates an existing estadoContenido.
     *
     * @param estadoContenido the estadoContenido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoContenido,
     * or with status {@code 400 (Bad Request)} if the estadoContenido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoContenido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-contenidos")
    public ResponseEntity<EstadoContenido> updateEstadoContenido(@RequestBody EstadoContenido estadoContenido) throws URISyntaxException {
        log.debug("REST request to update EstadoContenido : {}", estadoContenido);
        if (estadoContenido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoContenido result = estadoContenidoService.save(estadoContenido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estadoContenido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-contenidos} : get all the estadoContenidos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoContenidos in body.
     */
    @GetMapping("/estado-contenidos")
    public List<EstadoContenido> getAllEstadoContenidos() {
        log.debug("REST request to get all EstadoContenidos");
        return estadoContenidoService.findAll();
    }

    /**
     * {@code GET  /estado-contenidos/:id} : get the "id" estadoContenido.
     *
     * @param id the id of the estadoContenido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoContenido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-contenidos/{id}")
    public ResponseEntity<EstadoContenido> getEstadoContenido(@PathVariable Long id) {
        log.debug("REST request to get EstadoContenido : {}", id);
        Optional<EstadoContenido> estadoContenido = estadoContenidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoContenido);
    }

    /**
     * {@code DELETE  /estado-contenidos/:id} : delete the "id" estadoContenido.
     *
     * @param id the id of the estadoContenido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-contenidos/{id}")
    public ResponseEntity<Void> deleteEstadoContenido(@PathVariable Long id) {
        log.debug("REST request to delete EstadoContenido : {}", id);
        estadoContenidoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
