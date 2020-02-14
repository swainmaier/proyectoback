package com.unrn.redm.repository;

import com.unrn.redm.domain.EstadoProyecto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoProyectoRepository extends JpaRepository<EstadoProyecto, Long> {

}
