package com.kafu.kafu.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/files")
@RequiredArgsConstructor
public class UserFileController {
    private final UserFileService userFileService;

    @PostMapping("/profile-photo")
    public ResponseEntity<String> uploadProfilePhoto(
            @PathVariable Long userId,
            @RequestParam String contentType) {
        return ResponseEntity.ok(userFileService.uploadProfilePhoto(userId, contentType));
    }

    @PostMapping("/cv")
    public ResponseEntity<String> uploadCV(
            @PathVariable Long userId,
            @RequestParam String contentType) {
        return ResponseEntity.ok(userFileService.uploadCV(userId, contentType));
    }
}