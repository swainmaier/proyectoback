package com.unrn.redm.service.impl;

import com.unrn.redm.service.GeneroService;
import com.unrn.redm.domain.Genero;
import com.unrn.redm.repository.GeneroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Genero}.
 */
@Service
@Transactional
public class GeneroServiceImpl implements GeneroService {

    private final Logger log = LoggerFactory.getLogger(GeneroServiceImpl.class);

    private final GeneroRepository generoRepository;

    public GeneroServiceImpl(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    /**
     * Save a genero.
     *
     * @param genero the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Genero save(Genero genero) {
        log.debug("Request to save Genero : {}", genero);
        return generoRepository.save(genero);
    }

    /**
     * Get all the generos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Genero> findAll() {
        log.debug("Request to get all Generos");
        return generoRepository.findAll();
    }

    /**
     * Get one genero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Genero> findOne(Long id) {
        log.debug("Request to get Genero : {}", id);
        return generoRepository.findById(id);
    }

    /**
     * Delete the genero by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Genero : {}", id);
        generoRepository.deleteById(id);
    }
}
