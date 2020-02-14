package com.unrn.redm.repository;

import com.unrn.redm.domain.Trailer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Trailer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Long> {

}
