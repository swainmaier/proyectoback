package com.unrn.redm.repository;

import com.unrn.redm.domain.Genero;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Genero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

}
