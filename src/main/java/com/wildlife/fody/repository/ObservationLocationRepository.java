package com.wildlife.fody.repository;

import com.wildlife.fody.domain.ObservationLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ObservationLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservationLocationRepository extends JpaRepository<ObservationLocation, Long> {
}
