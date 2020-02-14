package com.unrn.redm.repository;

import com.unrn.redm.domain.Teaser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Teaser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeaserRepository extends JpaRepository<Teaser, Long> {

}
