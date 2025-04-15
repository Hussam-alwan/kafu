package com.kafu.kafu.solution;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/solutions")
@RequiredArgsConstructor
public class SolutionController {
    private final SolutionService solutionService;

    @GetMapping
    public ResponseEntity<Page<SolutionDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(solutionService.findAll(pageable));
    }

    @GetMapping("/by-problem/{problemId}")
    public ResponseEntity<List<SolutionDTO>> findByProblemId(@PathVariable Long problemId) {
        return ResponseEntity.ok(solutionService.findByProblemId(problemId));
    }

    @GetMapping("/by-proposer/{userId}")
    public ResponseEntity<List<SolutionDTO>> findByProposedById(@PathVariable Long userId) {
        return ResponseEntity.ok(solutionService.findByProposedById(userId));
    }

    @GetMapping("/by-acceptor/{govId}")
    public ResponseEntity<List<SolutionDTO>> findByAcceptedById(@PathVariable Long govId) {
        return ResponseEntity.ok(solutionService.findByAcceptedById(govId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolutionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(solutionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SolutionDTO> create(@Valid @RequestBody SolutionDTO solutionDTO) {
        return ResponseEntity.ok(solutionService.create(solutionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolutionDTO> update(@PathVariable Long id, @Valid @RequestBody SolutionDTO solutionDTO) {
        return ResponseEntity.ok(solutionService.update(id, solutionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        solutionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
