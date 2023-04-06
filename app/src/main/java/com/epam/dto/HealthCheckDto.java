package com.epam.dto;

import lombok.Data;

@Data
public class HealthCheckDto {
    private final String region;
    private final String availabilityZone;
}
