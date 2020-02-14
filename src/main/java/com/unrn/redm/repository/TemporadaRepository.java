package com.unrn.redm.repository;

import com.unrn.redm.domain.Temporada;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Temporada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {

}
