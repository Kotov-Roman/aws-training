package com.epam.controllers;

import com.epam.dto.HealthCheckDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.internal.util.EC2MetadataUtils;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<HealthCheckDto> check(){
        String region = EC2MetadataUtils.getEC2InstanceRegion();
        String availabilityZone = EC2MetadataUtils.getAvailabilityZone();
        return ResponseEntity.ok(new HealthCheckDto(region, availabilityZone));
    }
    @GetMapping("/roma")
    public ResponseEntity<String> roma(){

        return ResponseEntity.ok("running");
    }
}
