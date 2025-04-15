package com.kafu.kafu.problem;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<Page<ProblemDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(problemService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProblemDTO> create(@Valid @RequestBody ProblemDTO problemDTO) {
        return ResponseEntity.ok(problemService.create(problemDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProblemDTO> update(@PathVariable Long id, @Valid @RequestBody ProblemDTO problemDTO) {
        return ResponseEntity.ok(problemService.update(id, problemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        problemService.delete(id);
        return ResponseEntity.ok().build();
    }
}
