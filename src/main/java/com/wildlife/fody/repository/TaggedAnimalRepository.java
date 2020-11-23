package com.wildlife.fody.repository;

import com.wildlife.fody.domain.TaggedAnimal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaggedAnimal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaggedAnimalRepository extends JpaRepository<TaggedAnimal, Long> {
}
