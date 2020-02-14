package com.unrn.redm.service;

import com.unrn.redm.domain.Target;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Target}.
 */
public interface TargetService {

    /**
     * Save a target.
     *
     * @param target the entity to save.
     * @return the persisted entity.
     */
    Target save(Target target);

    /**
     * Get all the targets.
     *
     * @return the list of entities.
     */
    List<Target> findAll();

    /**
     * Get the "id" target.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Target> findOne(Long id);

    /**
     * Delete the "id" target.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
