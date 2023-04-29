package com.epam.repos;

import com.epam.domain.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {
    Long deleteByName(String name);
}