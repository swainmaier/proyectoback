package com.unrn.redm.service;

import com.unrn.redm.domain.Plataforma;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Plataforma}.
 */
public interface PlataformaService {

    /**
     * Save a plataforma.
     *
     * @param plataforma the entity to save.
     * @return the persisted entity.
     */
    Plataforma save(Plataforma plataforma);

    /**
     * Get all the plataformas.
     *
     * @return the list of entities.
     */
    List<Plataforma> findAll();

    /**
     * Get the "id" plataforma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Plataforma> findOne(Long id);

    /**
     * Delete the "id" plataforma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
