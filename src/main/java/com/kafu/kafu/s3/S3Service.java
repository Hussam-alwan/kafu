package com.kafu.kafu.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.maxFileSize}")
    private Long maxFileSize;

    @Value("${aws.s3.allowedFileTypes}")
    private String allowedFileTypes;

    public String generatePresignedUrl(String contentType,String key) {
        validateFileType(contentType);
        
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return presignedRequest.url().toExternalForm();
    }

    public String generatePresignedGetUrl(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        GetObjectPresignRequest presignGetRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofHours(1))  // Adjust duration as needed
            .getObjectRequest(getObjectRequest)
            .build();

        return s3Presigner.presignGetObject(presignGetRequest)
            .url()
            .toExternalForm();
    }

    private void validateFileType(String contentType) {
        List<String> allowed = Arrays.asList(allowedFileTypes.split(","));
        if (!allowed.contains(contentType.toLowerCase())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Invalid file type. Allowed types: " + allowedFileTypes
            );
        }
    }

    public String generateUniqueKey() {
        return "problems/" + UUID.randomUUID().toString();
    }

    public void deleteObject(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
                
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete object from S3");
        }
    }
}