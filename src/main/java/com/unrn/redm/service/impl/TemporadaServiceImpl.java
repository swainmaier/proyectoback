package com.unrn.redm.service.impl;

import com.unrn.redm.service.TemporadaService;
import com.unrn.redm.domain.Temporada;
import com.unrn.redm.repository.TemporadaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Temporada}.
 */
@Service
@Transactional
public class TemporadaServiceImpl implements TemporadaService {

    private final Logger log = LoggerFactory.getLogger(TemporadaServiceImpl.class);

    private final TemporadaRepository temporadaRepository;

    public TemporadaServiceImpl(TemporadaRepository temporadaRepository) {
        this.temporadaRepository = temporadaRepository;
    }

    /**
     * Save a temporada.
     *
     * @param temporada the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Temporada save(Temporada temporada) {
        log.debug("Request to save Temporada : {}", temporada);
        return temporadaRepository.save(temporada);
    }

    /**
     * Get all the temporadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Temporada> findAll(Pageable pageable) {
        log.debug("Request to get all Temporadas");
        return temporadaRepository.findAll(pageable);
    }

    /**
     * Get one temporada by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Temporada> findOne(Long id) {
        log.debug("Request to get Temporada : {}", id);
        return temporadaRepository.findById(id);
    }

    /**
     * Delete the temporada by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Temporada : {}", id);
        temporadaRepository.deleteById(id);
    }
}
