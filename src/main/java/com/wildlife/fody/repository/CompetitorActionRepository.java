package com.wildlife.fody.repository;

import com.wildlife.fody.domain.CompetitorAction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompetitorAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitorActionRepository extends JpaRepository<CompetitorAction, Long> {
}
