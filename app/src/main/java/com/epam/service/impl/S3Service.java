package com.epam.service.impl;

import com.epam.domain.ImageMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Response;

import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    @Value("${s3.bucket.name}")
    private String bucketName;

    public void upload(ImageMetadata imageMetadata, InputStream fileInputStream) {
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(imageMetadata.getName())
                .build();

        S3Response response =
                s3Client.putObject(putOb, RequestBody.fromInputStream(fileInputStream, imageMetadata.getSize()));
        String successMsg = "Successfully placed " + imageMetadata.getName() + " into bucket " + bucketName;
        logResponseResult(successMsg, response, HttpStatusCode.OK);
    }

    public InputStreamSource download(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> s3ObjectStream = s3Client.getObject(request);
        return new InputStreamResource(s3ObjectStream);
    }

    public void delete(String name) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(name)
                .build();

        S3Response response = s3Client.deleteObject(deleteObjectRequest);
        String successMsg = "Successfully deleted " + name + " from bucket " + bucketName;
        logResponseResult(successMsg, response, HttpStatusCode.NO_CONTENT);
    }

    private void logResponseResult(String successMsg, S3Response response, int expectedStatus) {
        int statusCode = response.sdkHttpResponse().statusCode();
        if (statusCode == expectedStatus) {
            log.info(successMsg);
        } else {
            log.error(statusCode + " smth is wrong");
        }
    }
}
