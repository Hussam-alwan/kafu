package com.kafu.kafu.problem;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems")
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

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProblemDTO>> findByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(problemService.findByCategoryId(categoryId));
    }

    @GetMapping("/by-approved-gov/{govId}")
    public ResponseEntity<List<ProblemDTO>> findByApprovedByGov(@PathVariable Long govId) {
        return ResponseEntity.ok(problemService.findByApprovedByGovId(govId));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('GOV') or hasRole('ADMIN')")
    public ResponseEntity<ProblemDTO> approve(@PathVariable Long id, @RequestParam Long govId) {
        return ResponseEntity.ok(problemService.approve(id, govId));
    }

    @PutMapping("/{id}/real-fields")
    @PreAuthorize("hasRole('GOV')")
    public ResponseEntity<ProblemDTO> updateRealFields(@PathVariable Long id, @RequestBody ProblemDTO problemDTO) {
        return ResponseEntity.ok(problemService.updateRealFields(id, problemDTO));
    }

    @PutMapping("/{id}/details")
//    @PreAuthorize("@problemSecurity.isSubmitter(#id, authentication)")
    public ResponseEntity<ProblemDTO> updateDetails(@PathVariable Long id, @RequestBody ProblemDTO problemDTO) {
        return ResponseEntity.ok(problemService.updateDetails(id, problemDTO));
    }
}
