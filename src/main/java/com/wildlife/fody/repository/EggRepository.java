package com.wildlife.fody.repository;

import com.wildlife.fody.domain.Egg;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Egg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EggRepository extends JpaRepository<Egg, Long> {
}
