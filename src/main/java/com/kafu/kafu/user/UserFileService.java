package com.kafu.kafu.user;

import com.kafu.kafu.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFileService {
    private final UserService userService;
    private final S3Service s3Service;

    public String uploadProfilePhoto(Long userId, String contentType) {
        if (!isValidPhotoContentType(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid file type. Allowed types: image/jpeg,image/png,image/jpg");
        }
        User user = userService.findById(userId);
        String key = generateProfilePhotoKey(userId);
        
        // Update user's photo URL
        user.setPhotoUrl(key);
        userService.save(user);
        
        return s3Service.generatePresignedUrl(contentType, key);
    }

    public String uploadCV(Long userId, String contentType) {
        if (!isValidCVContentType(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Invalid file type. Allowed types: application/pdf, application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }

        User user = userService.findById(userId);
        String key = generateCVKey(userId);
        
        // Update user's CV URL
        user.setCvUrl(key);
        userService.save(user);
        
        return s3Service.generatePresignedUrl(contentType, key);
    }

    private String generateProfilePhotoKey(Long userId) {
        return String.format("users/%d/profile-photo/%s", userId, UUID.randomUUID());
    }

    private String generateCVKey(Long userId) {
        return String.format("users/%d/cv/%s", userId, UUID.randomUUID());
    }

    private boolean isValidCVContentType(String contentType) {
        return contentType != null && (
            contentType.equals("application/pdf") ||
            contentType.equals("application/msword") ||
            contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        );
    }
    private boolean isValidPhotoContentType(String contentType) {
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/jpg")
        );
    }
}