package com.unrn.redm.service.impl;

import com.unrn.redm.service.EstadoContenidoService;
import com.unrn.redm.domain.EstadoContenido;
import com.unrn.redm.repository.EstadoContenidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EstadoContenido}.
 */
@Service
@Transactional
public class EstadoContenidoServiceImpl implements EstadoContenidoService {

    private final Logger log = LoggerFactory.getLogger(EstadoContenidoServiceImpl.class);

    private final EstadoContenidoRepository estadoContenidoRepository;

    public EstadoContenidoServiceImpl(EstadoContenidoRepository estadoContenidoRepository) {
        this.estadoContenidoRepository = estadoContenidoRepository;
    }

    /**
     * Save a estadoContenido.
     *
     * @param estadoContenido the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstadoContenido save(EstadoContenido estadoContenido) {
        log.debug("Request to save EstadoContenido : {}", estadoContenido);
        return estadoContenidoRepository.save(estadoContenido);
    }

    /**
     * Get all the estadoContenidos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EstadoContenido> findAll() {
        log.debug("Request to get all EstadoContenidos");
        return estadoContenidoRepository.findAll();
    }

    /**
     * Get one estadoContenido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoContenido> findOne(Long id) {
        log.debug("Request to get EstadoContenido : {}", id);
        return estadoContenidoRepository.findById(id);
    }

    /**
     * Delete the estadoContenido by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstadoContenido : {}", id);
        estadoContenidoRepository.deleteById(id);
    }
}
