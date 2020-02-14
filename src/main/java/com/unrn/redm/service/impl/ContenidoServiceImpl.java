package com.unrn.redm.service.impl;

import com.unrn.redm.service.ContenidoService;
import com.unrn.redm.domain.Contenido;
import com.unrn.redm.repository.ContenidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Contenido}.
 */
@Service
@Transactional
public class ContenidoServiceImpl implements ContenidoService {

    private final Logger log = LoggerFactory.getLogger(ContenidoServiceImpl.class);

    private final ContenidoRepository contenidoRepository;

    public ContenidoServiceImpl(ContenidoRepository contenidoRepository) {
        this.contenidoRepository = contenidoRepository;
    }

    /**
     * Save a contenido.
     *
     * @param contenido the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Contenido save(Contenido contenido) {
        log.debug("Request to save Contenido : {}", contenido);
        return contenidoRepository.save(contenido);
    }

    /**
     * Get all the contenidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Contenido> findAll(Pageable pageable) {
        log.debug("Request to get all Contenidos");
        return contenidoRepository.findAll(pageable);
    }

    /**
     * Get one contenido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Contenido> findOne(Long id) {
        log.debug("Request to get Contenido : {}", id);
        return contenidoRepository.findById(id);
    }

    /**
     * Delete the contenido by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contenido : {}", id);
        contenidoRepository.deleteById(id);
    }
}
