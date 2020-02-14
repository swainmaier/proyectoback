package com.unrn.redm.repository;

import com.unrn.redm.domain.SubGenero;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SubGenero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubGeneroRepository extends JpaRepository<SubGenero, Long> {

}
