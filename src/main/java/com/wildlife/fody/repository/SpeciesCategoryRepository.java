package com.wildlife.fody.repository;

import com.wildlife.fody.domain.SpeciesCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SpeciesCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeciesCategoryRepository extends JpaRepository<SpeciesCategory, Long> {
}
