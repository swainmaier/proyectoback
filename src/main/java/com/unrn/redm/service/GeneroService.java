package com.unrn.redm.service;

import com.unrn.redm.domain.Genero;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Genero}.
 */
public interface GeneroService {

    /**
     * Save a genero.
     *
     * @param genero the entity to save.
     * @return the persisted entity.
     */
    Genero save(Genero genero);

    /**
     * Get all the generos.
     *
     * @return the list of entities.
     */
    List<Genero> findAll();

    /**
     * Get the "id" genero.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Genero> findOne(Long id);

    /**
     * Delete the "id" genero.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
