package com.unrn.redm.service.impl;

import com.unrn.redm.service.TeaserService;
import com.unrn.redm.domain.Teaser;
import com.unrn.redm.repository.TeaserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Teaser}.
 */
@Service
@Transactional
public class TeaserServiceImpl implements TeaserService {

    private final Logger log = LoggerFactory.getLogger(TeaserServiceImpl.class);

    private final TeaserRepository teaserRepository;

    public TeaserServiceImpl(TeaserRepository teaserRepository) {
        this.teaserRepository = teaserRepository;
    }

    /**
     * Save a teaser.
     *
     * @param teaser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Teaser save(Teaser teaser) {
        log.debug("Request to save Teaser : {}", teaser);
        return teaserRepository.save(teaser);
    }

    /**
     * Get all the teasers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Teaser> findAll() {
        log.debug("Request to get all Teasers");
        return teaserRepository.findAll();
    }

    /**
     * Get one teaser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Teaser> findOne(Long id) {
        log.debug("Request to get Teaser : {}", id);
        return teaserRepository.findById(id);
    }

    /**
     * Delete the teaser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teaser : {}", id);
        teaserRepository.deleteById(id);
    }
}
