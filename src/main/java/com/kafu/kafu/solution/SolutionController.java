package com.kafu.kafu.solution;

import com.kafu.kafu.solution.dto.SolutionDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/problems/{problemId}/solutions")
@RequiredArgsConstructor
public class SolutionController {
    private final SolutionService solutionService;

    @GetMapping("/{id}")
    public ResponseEntity<SolutionDTO> findById(@PathVariable Long problemId , @PathVariable Long id) {
        return ResponseEntity.ok(SolutionMapper.toDTO(solutionService.findById(id)));
    }

    @GetMapping("/me")
    public ResponseEntity<List<SolutionDTO>> findByUserId(@PathVariable Long problemId) {
        List<SolutionDTO> solutions = solutionService.findSolutionsProposedByCurrentUser()
                .stream()
                .map(SolutionMapper::toDTO)
                .toList();
        return ResponseEntity.ok(solutions);
    }

    @GetMapping
    public ResponseEntity<List<SolutionDTO>> findByProblemId(@PathVariable Long problemId) {
        List<SolutionDTO> solutions = solutionService.findByProblemId(problemId)
                .stream()
                .map(SolutionMapper::toDTO)
                .toList();
        return ResponseEntity.ok(solutions);
    }

    @PostMapping
    public ResponseEntity<SolutionDTO> create(@PathVariable Long problemId,@Valid @RequestBody SolutionDTO solutionDTO) {
        solutionDTO.setProblemId(problemId);
        return ResponseEntity.ok(SolutionMapper.toDTO(solutionService.create(solutionDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolutionDTO> update(@PathVariable Long problemId,@PathVariable Long id, @Valid @RequestBody SolutionDTO solutionDTO) {
        return ResponseEntity.ok(SolutionMapper.toDTO(solutionService.update(id, solutionDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long problemId,@PathVariable Long id) {
        solutionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
