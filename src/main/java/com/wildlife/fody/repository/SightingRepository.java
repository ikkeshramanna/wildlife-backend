package com.wildlife.fody.repository;

import com.wildlife.fody.domain.Sighting;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sighting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SightingRepository extends JpaRepository<Sighting, Long> {
}
