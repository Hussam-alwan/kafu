package com.kafu.kafu.problemphoto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/problem-photos")
@RequiredArgsConstructor
public class ProblemPhotoController {
    private final ProblemPhotoService problemPhotoService;

    @GetMapping
    public ResponseEntity<Page<ProblemPhotoDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(problemPhotoService.findAll(pageable));
    }

    @GetMapping("/by-problem/{problemId}")
    public ResponseEntity<List<ProblemPhotoDTO>> findByProblemId(@PathVariable Long problemId) {
        return ResponseEntity.ok(problemPhotoService.findByProblemId(problemId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemPhotoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(problemPhotoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProblemPhotoDTO> create(@Valid @RequestBody ProblemPhotoDTO problemPhotoDTO) {
        return ResponseEntity.ok(problemPhotoService.create(problemPhotoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProblemPhotoDTO> update(@PathVariable Long id, @Valid @RequestBody ProblemPhotoDTO problemPhotoDTO) {
        return ResponseEntity.ok(problemPhotoService.update(id, problemPhotoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        problemPhotoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
