package com.unrn.redm.service.impl;

import com.unrn.redm.service.SubGeneroService;
import com.unrn.redm.domain.SubGenero;
import com.unrn.redm.repository.SubGeneroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SubGenero}.
 */
@Service
@Transactional
public class SubGeneroServiceImpl implements SubGeneroService {

    private final Logger log = LoggerFactory.getLogger(SubGeneroServiceImpl.class);

    private final SubGeneroRepository subGeneroRepository;

    public SubGeneroServiceImpl(SubGeneroRepository subGeneroRepository) {
        this.subGeneroRepository = subGeneroRepository;
    }

    /**
     * Save a subGenero.
     *
     * @param subGenero the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubGenero save(SubGenero subGenero) {
        log.debug("Request to save SubGenero : {}", subGenero);
        return subGeneroRepository.save(subGenero);
    }

    /**
     * Get all the subGeneros.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubGenero> findAll() {
        log.debug("Request to get all SubGeneros");
        return subGeneroRepository.findAll();
    }

    /**
     * Get one subGenero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubGenero> findOne(Long id) {
        log.debug("Request to get SubGenero : {}", id);
        return subGeneroRepository.findById(id);
    }

    /**
     * Delete the subGenero by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubGenero : {}", id);
        subGeneroRepository.deleteById(id);
    }
}
