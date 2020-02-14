package com.unrn.redm.repository;

import com.unrn.redm.domain.Proyecto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Proyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

}
