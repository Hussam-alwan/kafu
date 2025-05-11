package com.kafu.kafu.problemphoto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/problem/{problemId}/photos")
@RequiredArgsConstructor
public class ProblemPhotoController {
    private final ProblemPhotoService problemPhotoService;

    @PostMapping
    public ResponseEntity<List<ProblemPhotoDTO>> createPhotos(
            @PathVariable Long problemId,
            @RequestParam(required = false) Long progressId,
            @RequestParam @Min(1) Integer count,
            @RequestParam(defaultValue = "image/jpeg") String contentType) {
        return ResponseEntity.ok(problemPhotoService.createPhotos(problemId, progressId, count, contentType));
    }

    @GetMapping
    public ResponseEntity<List<ProblemPhotoDTO>> findByProblemId(@PathVariable Long problemId) {
        List<ProblemPhoto> photos = problemPhotoService.findByProblemIdWithS3Url(problemId);
        return ResponseEntity.ok(photos.stream().map(ProblemPhotoMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemPhotoDTO> findById(
            @PathVariable Long problemId,
            @PathVariable Long id) {
        ProblemPhoto photo = problemPhotoService.findByIdWithS3Url(id);
        return ResponseEntity.ok(ProblemPhotoMapper.toDTO(photo));
    }
    @Operation(summary = "deleteAllByProblemId",description = "Delete all Photos by problem id")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(@PathVariable Long problemId) {
        problemPhotoService.deleteAllByProblemId(problemId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "deleteById",description = "Delete Photo by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long problemId,
            @PathVariable Long id) {
        problemPhotoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
