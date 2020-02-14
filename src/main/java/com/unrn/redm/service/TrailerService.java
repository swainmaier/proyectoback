package com.unrn.redm.service;

import com.unrn.redm.domain.Trailer;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Trailer}.
 */
public interface TrailerService {

    /**
     * Save a trailer.
     *
     * @param trailer the entity to save.
     * @return the persisted entity.
     */
    Trailer save(Trailer trailer);

    /**
     * Get all the trailers.
     *
     * @return the list of entities.
     */
    List<Trailer> findAll();

    /**
     * Get the "id" trailer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trailer> findOne(Long id);

    /**
     * Delete the "id" trailer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
