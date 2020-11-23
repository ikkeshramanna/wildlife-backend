package com.wildlife.fody.repository;

import com.wildlife.fody.domain.BirdsIdentified;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the BirdsIdentified entity.
 */
@Repository
public interface BirdsIdentifiedRepository extends JpaRepository<BirdsIdentified, Long> {

    @Query(value = "select distinct birdsIdentified from BirdsIdentified birdsIdentified left join fetch birdsIdentified.birdBehaviours",
        countQuery = "select count(distinct birdsIdentified) from BirdsIdentified birdsIdentified")
    Page<BirdsIdentified> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct birdsIdentified from BirdsIdentified birdsIdentified left join fetch birdsIdentified.birdBehaviours")
    List<BirdsIdentified> findAllWithEagerRelationships();

    @Query("select birdsIdentified from BirdsIdentified birdsIdentified left join fetch birdsIdentified.birdBehaviours where birdsIdentified.id =:id")
    Optional<BirdsIdentified> findOneWithEagerRelationships(@Param("id") Long id);
}
