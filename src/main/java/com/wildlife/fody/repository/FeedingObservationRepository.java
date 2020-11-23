package com.wildlife.fody.repository;

import com.wildlife.fody.domain.FeedingObservation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FeedingObservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedingObservationRepository extends JpaRepository<FeedingObservation, Long> {
}
