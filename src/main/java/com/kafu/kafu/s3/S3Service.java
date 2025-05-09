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
import java.net.URL;
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
                .contentLength(maxFileSize)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        
        // Return both the presigned URL and the permanent object key
        return presignedRequest.url().toString();
    }

    // Add method to get permanent object URL
    public String getObjectUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", 
            bucketName, 
            s3Client.serviceClientConfiguration().region(), 
            key);
    }

    // Add method to generate presigned GET URL when needed
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
            .toString();
    }

//    // Extract key from URL
//    public String extractKeyFromUrl(String url) {
//        try {
//            URL parsedUrl = new URL(url);
//            return parsedUrl.getPath().substring(1); // Remove leading '/'
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid S3 URL");
//        }
//    }

    public boolean validatePresignedUrl(String presignedUrl) {
        try {
            URL url = new URL(presignedUrl);
            String host = url.getHost();
            String path = url.getPath();
            
            // Check basic structure
            if (!host.contains(bucketName) || !host.contains("amazonaws.com")) {
                return false;
            }
            
            // Validate path structure
            if (!path.startsWith("/problems/")) {
                return false;
            }
            
            // Check for UUID pattern in the path
            String[] pathParts = path.split("/");
            if (pathParts.length != 3) { // Should be ["", "problems", "uuid"]
                return false;
            }
            
            // Validate UUID format
            try {
                UUID.fromString(pathParts[2]);
            } catch (IllegalArgumentException e) {
                return false;
            }
            
            // Validate protocol
            if (!url.getProtocol().equals("https")) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    public List<String> generateMultiplePresignedUrls(String contentType, int count) {
//        List<String> urls = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            urls.add(generatePresignedUrl(contentType));
//        }
//        return urls;
//    }

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