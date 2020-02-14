package com.unrn.redm.service.impl;

import com.unrn.redm.service.TrailerService;
import com.unrn.redm.domain.Trailer;
import com.unrn.redm.repository.TrailerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Trailer}.
 */
@Service
@Transactional
public class TrailerServiceImpl implements TrailerService {

    private final Logger log = LoggerFactory.getLogger(TrailerServiceImpl.class);

    private final TrailerRepository trailerRepository;

    public TrailerServiceImpl(TrailerRepository trailerRepository) {
        this.trailerRepository = trailerRepository;
    }

    /**
     * Save a trailer.
     *
     * @param trailer the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Trailer save(Trailer trailer) {
        log.debug("Request to save Trailer : {}", trailer);
        return trailerRepository.save(trailer);
    }

    /**
     * Get all the trailers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Trailer> findAll() {
        log.debug("Request to get all Trailers");
        return trailerRepository.findAll();
    }

    /**
     * Get one trailer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Trailer> findOne(Long id) {
        log.debug("Request to get Trailer : {}", id);
        return trailerRepository.findById(id);
    }

    /**
     * Delete the trailer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trailer : {}", id);
        trailerRepository.deleteById(id);
    }
}
