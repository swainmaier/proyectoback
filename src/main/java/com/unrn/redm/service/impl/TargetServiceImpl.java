package com.unrn.redm.service.impl;

import com.unrn.redm.service.TargetService;
import com.unrn.redm.domain.Target;
import com.unrn.redm.repository.TargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Target}.
 */
@Service
@Transactional
public class TargetServiceImpl implements TargetService {

    private final Logger log = LoggerFactory.getLogger(TargetServiceImpl.class);

    private final TargetRepository targetRepository;

    public TargetServiceImpl(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    /**
     * Save a target.
     *
     * @param target the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Target save(Target target) {
        log.debug("Request to save Target : {}", target);
        return targetRepository.save(target);
    }

    /**
     * Get all the targets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Target> findAll() {
        log.debug("Request to get all Targets");
        return targetRepository.findAll();
    }

    /**
     * Get one target by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Target> findOne(Long id) {
        log.debug("Request to get Target : {}", id);
        return targetRepository.findById(id);
    }

    /**
     * Delete the target by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Target : {}", id);
        targetRepository.deleteById(id);
    }
}
