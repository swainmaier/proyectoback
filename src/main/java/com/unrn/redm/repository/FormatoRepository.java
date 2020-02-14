package com.unrn.redm.repository;

import com.unrn.redm.domain.Formato;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Formato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormatoRepository extends JpaRepository<Formato, Long> {

}
