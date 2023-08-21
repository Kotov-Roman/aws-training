package com.epam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
public class ImageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long size;
    private String name;
    private String contentType;
    private LocalDateTime lastUpdate;

}
