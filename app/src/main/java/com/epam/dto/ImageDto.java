package com.epam.dto;

import com.epam.domain.ImageMetadata;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.InputStreamSource;

@Data
@Builder
public class ImageDto {
    ImageMetadata imageMetadata;
    InputStreamSource inputStreamSource;
}
