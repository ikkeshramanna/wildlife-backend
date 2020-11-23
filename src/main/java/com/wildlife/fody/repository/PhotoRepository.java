package com.wildlife.fody.repository;

import com.wildlife.fody.domain.Photo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
