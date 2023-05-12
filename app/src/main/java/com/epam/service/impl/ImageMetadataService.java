package com.epam.service.impl;

import com.epam.domain.ImageMetadata;
import com.epam.repos.ImageMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ImageMetadataService {
    private final ImageMetadataRepository imageMetadataRepository;


    public ImageMetadataService(ImageMetadataRepository imageMetadataRepository) {
        this.imageMetadataRepository = imageMetadataRepository;
    }

    @Transactional
    public ImageMetadata findById(Long id) {
        return imageMetadataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found by id=" + id));
    }

    @Transactional
    public List<ImageMetadata> findAll() {
        return imageMetadataRepository.findAll();
    }

    @Transactional
    public ImageMetadata save(ImageMetadata imageMetadata) {
        return imageMetadataRepository.save(imageMetadata);
    }

    @Transactional
    public Long deleteByName(String name) {
        return imageMetadataRepository.deleteByName(name);
    }
}
