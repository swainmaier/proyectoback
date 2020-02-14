package com.unrn.redm.service;

import com.unrn.redm.domain.EstadoContenido;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EstadoContenido}.
 */
public interface EstadoContenidoService {

    /**
     * Save a estadoContenido.
     *
     * @param estadoContenido the entity to save.
     * @return the persisted entity.
     */
    EstadoContenido save(EstadoContenido estadoContenido);

    /**
     * Get all the estadoContenidos.
     *
     * @return the list of entities.
     */
    List<EstadoContenido> findAll();

    /**
     * Get the "id" estadoContenido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoContenido> findOne(Long id);

    /**
     * Delete the "id" estadoContenido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
