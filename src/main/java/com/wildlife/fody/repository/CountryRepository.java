package com.wildlife.fody.repository;

import com.wildlife.fody.domain.Country;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Country entity.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query(value = "select distinct country from Country country left join fetch country.species",
        countQuery = "select count(distinct country) from Country country")
    Page<Country> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct country from Country country left join fetch country.species")
    List<Country> findAllWithEagerRelationships();

    @Query("select country from Country country left join fetch country.species where country.id =:id")
    Optional<Country> findOneWithEagerRelationships(@Param("id") Long id);
}
