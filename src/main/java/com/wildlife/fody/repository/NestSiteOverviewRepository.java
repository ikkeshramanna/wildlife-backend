package com.wildlife.fody.repository;

import com.wildlife.fody.domain.NestSiteOverview;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NestSiteOverview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NestSiteOverviewRepository extends JpaRepository<NestSiteOverview, Long> {
}
