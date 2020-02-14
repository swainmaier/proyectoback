package com.unrn.redm.service;

import com.unrn.redm.domain.Contenido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Contenido}.
 */
public interface ContenidoService {

    /**
     * Save a contenido.
     *
     * @param contenido the entity to save.
     * @return the persisted entity.
     */
    Contenido save(Contenido contenido);

    /**
     * Get all the contenidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Contenido> findAll(Pageable pageable);

    /**
     * Get the "id" contenido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Contenido> findOne(Long id);

    /**
     * Delete the "id" contenido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
