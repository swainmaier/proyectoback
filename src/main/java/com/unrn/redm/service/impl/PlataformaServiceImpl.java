package com.unrn.redm.service.impl;

import com.unrn.redm.service.PlataformaService;
import com.unrn.redm.domain.Plataforma;
import com.unrn.redm.repository.PlataformaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Plataforma}.
 */
@Service
@Transactional
public class PlataformaServiceImpl implements PlataformaService {

    private final Logger log = LoggerFactory.getLogger(PlataformaServiceImpl.class);

    private final PlataformaRepository plataformaRepository;

    public PlataformaServiceImpl(PlataformaRepository plataformaRepository) {
        this.plataformaRepository = plataformaRepository;
    }

    /**
     * Save a plataforma.
     *
     * @param plataforma the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Plataforma save(Plataforma plataforma) {
        log.debug("Request to save Plataforma : {}", plataforma);
        return plataformaRepository.save(plataforma);
    }

    /**
     * Get all the plataformas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Plataforma> findAll() {
        log.debug("Request to get all Plataformas");
        return plataformaRepository.findAll();
    }

    /**
     * Get one plataforma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Plataforma> findOne(Long id) {
        log.debug("Request to get Plataforma : {}", id);
        return plataformaRepository.findById(id);
    }

    /**
     * Delete the plataforma by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plataforma : {}", id);
        plataformaRepository.deleteById(id);
    }
}
