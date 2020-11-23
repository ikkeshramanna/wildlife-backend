package com.wildlife.fody.repository;

import com.wildlife.fody.domain.Species;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Species entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
}
