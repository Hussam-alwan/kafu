package com.kafu.kafu.solution;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/solutions")
@RequiredArgsConstructor
public class SolutionController {
    private final SolutionService solutionService;

    @GetMapping
    public ResponseEntity<Page<SolutionDTO>> findAll(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long problemId,
            @RequestParam(required = false) Long proposedByUserId,
            @RequestParam(required = false) Long acceptedByUserId,
            Pageable pageable) {
        SolutionSearchCriteria criteria = new SolutionSearchCriteria();
        criteria.setDescription(description);
        criteria.setProblemId(problemId);
        criteria.setProposedByUserId(proposedByUserId);
        criteria.setAcceptedByUserId(acceptedByUserId);
        return ResponseEntity.ok(solutionService.search(criteria, pageable).map(SolutionMapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolutionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(SolutionMapper.toDTO(solutionService.findById(id)));
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
