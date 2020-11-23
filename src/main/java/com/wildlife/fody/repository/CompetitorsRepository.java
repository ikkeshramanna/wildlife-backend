package com.wildlife.fody.repository;

import com.wildlife.fody.domain.Competitors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Competitors entity.
 */
@Repository
public interface CompetitorsRepository extends JpaRepository<Competitors, Long> {

    @Query(value = "select distinct competitors from Competitors competitors left join fetch competitors.competitorImpactOnMks left join fetch competitors.competitorActions",
        countQuery = "select count(distinct competitors) from Competitors competitors")
    Page<Competitors> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct competitors from Competitors competitors left join fetch competitors.competitorImpactOnMks left join fetch competitors.competitorActions")
    List<Competitors> findAllWithEagerRelationships();

    @Query("select competitors from Competitors competitors left join fetch competitors.competitorImpactOnMks left join fetch competitors.competitorActions where competitors.id =:id")
    Optional<Competitors> findOneWithEagerRelationships(@Param("id") Long id);
}
