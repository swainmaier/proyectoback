package com.unrn.redm.service;

import com.unrn.redm.domain.Temporada;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Temporada}.
 */
public interface TemporadaService {

    /**
     * Save a temporada.
     *
     * @param temporada the entity to save.
     * @return the persisted entity.
     */
    Temporada save(Temporada temporada);

    /**
     * Get all the temporadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Temporada> findAll(Pageable pageable);

    /**
     * Get the "id" temporada.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Temporada> findOne(Long id);

    /**
     * Delete the "id" temporada.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
