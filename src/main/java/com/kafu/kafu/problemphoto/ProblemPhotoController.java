package com.kafu.kafu.problemphoto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/problem/{problemId}/photos")
@RequiredArgsConstructor
public class ProblemPhotoController {
    private final ProblemPhotoService problemPhotoService;

    @GetMapping
    public ResponseEntity<List<ProblemPhotoDTO>> findByProblemId(@PathVariable Long problemId) {
        List<ProblemPhoto> photos = problemPhotoService.findByProblemId(problemId);
        return ResponseEntity.ok(photos.stream().map(ProblemPhotoMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemPhotoDTO> findById(
            @PathVariable Long problemId,
            @PathVariable Long id) {
        ProblemPhoto photo = problemPhotoService.findById(id);
        return ResponseEntity.ok(ProblemPhotoMapper.toDTO(photo));
    }

    @PostMapping
    public ResponseEntity<ProblemPhotoDTO> create(
            @PathVariable Long problemId,
            @Valid @RequestBody ProblemPhotoDTO problemPhotoDTO) {
        problemPhotoDTO.setProblemId(problemId);
        ProblemPhoto created = problemPhotoService.create(problemPhotoDTO);
        return ResponseEntity.ok(ProblemPhotoMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProblemPhotoDTO> update(
            @PathVariable Long problemId,
            @PathVariable Long id,
            @Valid @RequestBody ProblemPhotoDTO problemPhotoDTO) {
        problemPhotoDTO.setProblemId(problemId);
        ProblemPhoto updated = problemPhotoService.update(id, problemPhotoDTO);
        return ResponseEntity.ok(ProblemPhotoMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long problemId,
            @PathVariable Long id) {
        problemPhotoService.deleteByIdAndProblemId(id, problemId);
        return ResponseEntity.ok().build();
    }
}
