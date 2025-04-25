package com.kafu.kafu.solution;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/solutions")
@RequiredArgsConstructor
public class SolutionController {
    private final SolutionService solutionService;

    @GetMapping
    public ResponseEntity<Page<SolutionDTO>> findAll(@ModelAttribute SolutionSearchCriteria criteria,
                                                    @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(solutionService.search(criteria, pageable).map(SolutionMapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolutionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(SolutionMapper.toDTO(solutionService.findById(id)));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<SolutionDTO>> findByUserId(@PathVariable Long userId) {
        List<SolutionDTO> solutions = solutionService.findByProposedByUserId(userId)
                .stream()
                .map(SolutionMapper::toDTO)
                .toList();
        return ResponseEntity.ok(solutions);
    }

    @GetMapping("/by-problem/{problemId}")
    public ResponseEntity<List<SolutionDTO>> findByProblemId(@PathVariable Long problemId) {
        List<SolutionDTO> solutions = solutionService.findByProblemId(problemId)
                .stream()
                .map(SolutionMapper::toDTO)
                .toList();
        return ResponseEntity.ok(solutions);
    }

    @PostMapping
    public ResponseEntity<SolutionDTO> create(@Valid @RequestBody SolutionDTO solutionDTO) {
        return ResponseEntity.ok(SolutionMapper.toDTO(solutionService.create(solutionDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolutionDTO> update(@PathVariable Long id, @Valid @RequestBody SolutionDTO solutionDTO) {
        return ResponseEntity.ok(SolutionMapper.toDTO(solutionService.update(id, solutionDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        solutionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
