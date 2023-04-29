package com.epam.controllers;

import com.epam.domain.ImageMetadata;
import com.epam.dto.ImageDto;
import com.epam.service.api.ImageFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageFacade imageFacade;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        imageFacade.upload(file);
        return ResponseEntity.ok().body("file received successfully");
    }

    @DeleteMapping()
    public ResponseEntity<Long> delete(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(imageFacade.deleteByName(name));
    }

    @GetMapping()
    public ResponseEntity<List<ImageMetadata>> getAll() {
        return ResponseEntity.ok().body(imageFacade.getAllImagesMetadata());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageMetadata> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(imageFacade.getImageMetadata(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamSource> download(@PathVariable("id") Long id) {
        ImageDto imageDto = imageFacade.downloadImage(id);
        ImageMetadata imageMetadata = imageDto.getImageMetadata();
        InputStreamSource resource = imageDto.getInputStreamSource();

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageMetadata.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(imageMetadata.getSize())
                .contentType(MediaType.parseMediaType(imageMetadata.getContentType()))
                .body(resource);
    }
}
