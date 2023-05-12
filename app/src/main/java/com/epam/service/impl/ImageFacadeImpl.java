package com.epam.service.impl;

import com.epam.domain.ImageMetadata;
import com.epam.dto.ImageDto;
import com.epam.messaging.SqsMessageProducer;
import com.epam.service.ImageFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ImageFacadeImpl implements ImageFacade {
    private final ImageMetadataService imageMetadataService;
    private final S3Service s3Service;
    private final SqsMessageProducer messageProducer;

    @Override
    public void upload(MultipartFile file) {
        LocalDateTime uploadDate = LocalDateTime.now();
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();
        InputStream fileInputStream;
        try {
            fileInputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("failed to get file input stream");
        }
        ImageMetadata imageMetadata = ImageMetadata.builder()
                .name(originalFilename)
                .lastUpdate(uploadDate)
                .contentType(contentType)
                .size(size)
                .build();

        imageMetadataService.save(imageMetadata);
        s3Service.upload(imageMetadata, fileInputStream);
        messageProducer.sendMessage(imageMetadata.getId().toString());
    }

    @Override
    public Long deleteByName(String name) {
        s3Service.delete(name);
        return imageMetadataService.deleteByName(name);
    }

    @Override
    public List<ImageMetadata> getAllImagesMetadata() {
        return imageMetadataService.findAll();
    }

    @Override
    public ImageMetadata getImageMetadata(Long id) {
        return imageMetadataService.findById(id);
    }

    @Override
    public ImageDto downloadImage(Long id) {
        ImageMetadata imageMetadata = this.getImageMetadata(id);
        InputStreamSource streamSource = s3Service.download(imageMetadata.getName());

        return ImageDto.builder()
                .imageMetadata(imageMetadata)
                .inputStreamSource(streamSource)
                .build();
    }
}
