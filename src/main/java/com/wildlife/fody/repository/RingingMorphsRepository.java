package com.wildlife.fody.repository;

import com.wildlife.fody.domain.RingingMorphs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RingingMorphs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RingingMorphsRepository extends JpaRepository<RingingMorphs, Long> {
}
