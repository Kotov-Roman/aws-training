package com.epam.service;

import com.epam.domain.ImageMetadata;
import com.epam.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageFacade {
    void upload(MultipartFile file);

    Long deleteByName(String name);

    List<ImageMetadata> getAllImagesMetadata();

    ImageMetadata getImageMetadata(Long id);

    ImageDto downloadImage(Long id);
}
