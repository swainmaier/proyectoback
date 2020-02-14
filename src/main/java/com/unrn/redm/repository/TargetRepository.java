package com.unrn.redm.repository;

import com.unrn.redm.domain.Target;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Target entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {

}
