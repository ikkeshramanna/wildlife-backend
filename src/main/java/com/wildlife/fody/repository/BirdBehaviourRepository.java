package com.wildlife.fody.repository;

import com.wildlife.fody.domain.BirdBehaviour;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BirdBehaviour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BirdBehaviourRepository extends JpaRepository<BirdBehaviour, Long> {
}
