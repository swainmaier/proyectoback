package com.unrn.redm.service;

import com.unrn.redm.domain.Formato;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Formato}.
 */
public interface FormatoService {

    /**
     * Save a formato.
     *
     * @param formato the entity to save.
     * @return the persisted entity.
     */
    Formato save(Formato formato);

    /**
     * Get all the formatoes.
     *
     * @return the list of entities.
     */
    List<Formato> findAll();

    /**
     * Get the "id" formato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Formato> findOne(Long id);

    /**
     * Delete the "id" formato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
