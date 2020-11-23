package com.wildlife.fody.repository;

import com.wildlife.fody.domain.EggsAndChick;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EggsAndChick entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EggsAndChickRepository extends JpaRepository<EggsAndChick, Long> {
}
