package com.unrn.redm.service;

import com.unrn.redm.domain.SubGenero;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SubGenero}.
 */
public interface SubGeneroService {

    /**
     * Save a subGenero.
     *
     * @param subGenero the entity to save.
     * @return the persisted entity.
     */
    SubGenero save(SubGenero subGenero);

    /**
     * Get all the subGeneros.
     *
     * @return the list of entities.
     */
    List<SubGenero> findAll();

    /**
     * Get the "id" subGenero.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubGenero> findOne(Long id);

    /**
     * Delete the "id" subGenero.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
