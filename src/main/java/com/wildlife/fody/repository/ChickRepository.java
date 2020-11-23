package com.wildlife.fody.repository;

import com.wildlife.fody.domain.Chick;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Chick entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChickRepository extends JpaRepository<Chick, Long> {
}
