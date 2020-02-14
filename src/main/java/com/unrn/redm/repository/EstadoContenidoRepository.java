package com.unrn.redm.repository;

import com.unrn.redm.domain.EstadoContenido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoContenido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoContenidoRepository extends JpaRepository<EstadoContenido, Long> {

}
