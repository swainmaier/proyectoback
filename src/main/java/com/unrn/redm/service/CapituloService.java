package com.unrn.redm.service;

import com.unrn.redm.domain.Capitulo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Capitulo}.
 */
public interface CapituloService {

    /**
     * Save a capitulo.
     *
     * @param capitulo the entity to save.
     * @return the persisted entity.
     */
    Capitulo save(Capitulo capitulo);

    /**
     * Get all the capitulos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Capitulo> findAll(Pageable pageable);

    /**
     * Get the "id" capitulo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Capitulo> findOne(Long id);

    /**
     * Delete the "id" capitulo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
