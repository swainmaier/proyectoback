package com.unrn.redm.service.impl;

import com.unrn.redm.service.CapituloService;
import com.unrn.redm.domain.Capitulo;
import com.unrn.redm.repository.CapituloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Capitulo}.
 */
@Service
@Transactional
public class CapituloServiceImpl implements CapituloService {

    private final Logger log = LoggerFactory.getLogger(CapituloServiceImpl.class);

    private final CapituloRepository capituloRepository;

    public CapituloServiceImpl(CapituloRepository capituloRepository) {
        this.capituloRepository = capituloRepository;
    }

    /**
     * Save a capitulo.
     *
     * @param capitulo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Capitulo save(Capitulo capitulo) {
        log.debug("Request to save Capitulo : {}", capitulo);
        return capituloRepository.save(capitulo);
    }

    /**
     * Get all the capitulos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Capitulo> findAll(Pageable pageable) {
        log.debug("Request to get all Capitulos");
        return capituloRepository.findAll(pageable);
    }

    /**
     * Get one capitulo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Capitulo> findOne(Long id) {
        log.debug("Request to get Capitulo : {}", id);
        return capituloRepository.findById(id);
    }

    /**
     * Delete the capitulo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Capitulo : {}", id);
        capituloRepository.deleteById(id);
    }
}
