package com.unrn.redm.repository;

import com.unrn.redm.domain.Capitulo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Capitulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, Long> {

}
