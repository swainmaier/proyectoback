package com.unrn.redm.service;

import com.unrn.redm.domain.Teaser;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Teaser}.
 */
public interface TeaserService {

    /**
     * Save a teaser.
     *
     * @param teaser the entity to save.
     * @return the persisted entity.
     */
    Teaser save(Teaser teaser);

    /**
     * Get all the teasers.
     *
     * @return the list of entities.
     */
    List<Teaser> findAll();

    /**
     * Get the "id" teaser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Teaser> findOne(Long id);

    /**
     * Delete the "id" teaser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
