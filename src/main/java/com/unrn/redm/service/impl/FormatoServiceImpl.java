package com.unrn.redm.service.impl;

import com.unrn.redm.service.FormatoService;
import com.unrn.redm.domain.Formato;
import com.unrn.redm.repository.FormatoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Formato}.
 */
@Service
@Transactional
public class FormatoServiceImpl implements FormatoService {

    private final Logger log = LoggerFactory.getLogger(FormatoServiceImpl.class);

    private final FormatoRepository formatoRepository;

    public FormatoServiceImpl(FormatoRepository formatoRepository) {
        this.formatoRepository = formatoRepository;
    }

    /**
     * Save a formato.
     *
     * @param formato the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Formato save(Formato formato) {
        log.debug("Request to save Formato : {}", formato);
        return formatoRepository.save(formato);
    }

    /**
     * Get all the formatoes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Formato> findAll() {
        log.debug("Request to get all Formatoes");
        return formatoRepository.findAll();
    }

    /**
     * Get one formato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Formato> findOne(Long id) {
        log.debug("Request to get Formato : {}", id);
        return formatoRepository.findById(id);
    }

    /**
     * Delete the formato by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Formato : {}", id);
        formatoRepository.deleteById(id);
    }
}
