package com.epam.service.impl;

import com.epam.domain.ImageMetadata;
import com.epam.repos.ImageMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageMetadataService {
    private final ImageMetadataRepository imageMetadataRepository;

    public ImageMetadataService(ImageMetadataRepository imageMetadataRepository) {
        this.imageMetadataRepository = imageMetadataRepository;
    }

    public ImageMetadata save(ImageMetadata imageMetadata) {
        return imageMetadataRepository.save(imageMetadata);
    }

    public Long deleteByName(String name) {
        return imageMetadataRepository.deleteByName(name);
    }
}
