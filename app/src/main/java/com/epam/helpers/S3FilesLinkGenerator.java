package com.epam.helpers;

import com.epam.controllers.ImageController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3FilesLinkGenerator {

    @Value("${server.port}")
    private int port;

    @Value("${host.dns}")
    private String hostDns;

    public String generateLink(Long id) {

        UriComponentsBuilder uriComponentsBuilder =
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(ImageController.class)
                                        .download(id))
                        .toUriComponentsBuilder();

        return uriComponentsBuilder.scheme("http")
                .host(hostDns)
                .port(port)
                .build()
                .toString();
    }
}
