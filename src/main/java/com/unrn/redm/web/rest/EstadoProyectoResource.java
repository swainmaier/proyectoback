package com.unrn.redm.web.rest;

import com.unrn.redm.domain.EstadoProyecto;
import com.unrn.redm.repository.EstadoProyectoRepository;
import com.unrn.redm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.unrn.redm.domain.EstadoProyecto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoProyectoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoProyectoResource.class);

    private static final String ENTITY_NAME = "estadoProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoProyectoRepository estadoProyectoRepository;

    public EstadoProyectoResource(EstadoProyectoRepository estadoProyectoRepository) {
        this.estadoProyectoRepository = estadoProyectoRepository;
    }

    /**
     * {@code POST  /estado-proyectos} : Create a new estadoProyecto.
     *
     * @param estadoProyecto the estadoProyecto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoProyecto, or with status {@code 400 (Bad Request)} if the estadoProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-proyectos")
    public ResponseEntity<EstadoProyecto> createEstadoProyecto(@RequestBody EstadoProyecto estadoProyecto) throws URISyntaxException {
        log.debug("REST request to save EstadoProyecto : {}", estadoProyecto);
        if (estadoProyecto.getId() != null) {
            throw new BadRequestAlertException("A new estadoProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoProyecto result = estadoProyectoRepository.save(estadoProyecto);
        return ResponseEntity.created(new URI("/api/estado-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-proyectos} : Updates an existing estadoProyecto.
     *
     * @param estadoProyecto the estadoProyecto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoProyecto,
     * or with status {@code 400 (Bad Request)} if the estadoProyecto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoProyecto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-proyectos")
    public ResponseEntity<EstadoProyecto> updateEstadoProyecto(@RequestBody EstadoProyecto estadoProyecto) throws URISyntaxException {
        log.debug("REST request to update EstadoProyecto : {}", estadoProyecto);
        if (estadoProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoProyecto result = estadoProyectoRepository.save(estadoProyecto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estadoProyecto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-proyectos} : get all the estadoProyectos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoProyectos in body.
     */
    @GetMapping("/estado-proyectos")
    public List<EstadoProyecto> getAllEstadoProyectos() {
        log.debug("REST request to get all EstadoProyectos");
        return estadoProyectoRepository.findAll();
    }

    /**
     * {@code GET  /estado-proyectos/:id} : get the "id" estadoProyecto.
     *
     * @param id the id of the estadoProyecto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoProyecto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-proyectos/{id}")
    public ResponseEntity<EstadoProyecto> getEstadoProyecto(@PathVariable Long id) {
        log.debug("REST request to get EstadoProyecto : {}", id);
        Optional<EstadoProyecto> estadoProyecto = estadoProyectoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoProyecto);
    }

    /**
     * {@code DELETE  /estado-proyectos/:id} : delete the "id" estadoProyecto.
     *
     * @param id the id of the estadoProyecto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-proyectos/{id}")
    public ResponseEntity<Void> deleteEstadoProyecto(@PathVariable Long id) {
        log.debug("REST request to delete EstadoProyecto : {}", id);
        estadoProyectoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
