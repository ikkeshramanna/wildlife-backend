package com.wildlife.fody.repository;

import com.wildlife.fody.domain.CompetitorImpactOnMk;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompetitorImpactOnMk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitorImpactOnMkRepository extends JpaRepository<CompetitorImpactOnMk, Long> {
}
