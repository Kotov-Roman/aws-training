package com.epam.notification;

import com.epam.domain.ImageMetadata;
import com.epam.helpers.S3FilesLinkGenerator;
import com.epam.service.impl.ImageFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationBuilder {
    private final ImageFacadeImpl imageFacade;
    private final S3FilesLinkGenerator s3FilesLinkGenerator;

    public String buildByImageId(Long imageId) {
        ImageMetadata imageMetadata = imageFacade.getImageMetadata(imageId);
        StringBuilder builder = new StringBuilder();
        builder.append("Image has been uploaded or updated:");
        builder.append(System.lineSeparator());
        builder.append(imageMetadata.toString());
        builder.append(System.lineSeparator());
        builder.append("link: " + s3FilesLinkGenerator.generateLink(imageId));

        return builder.toString();
    }
}
