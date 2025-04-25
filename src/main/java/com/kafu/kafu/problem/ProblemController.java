package com.kafu.kafu.problem;

import com.kafu.kafu.problem.dto.ProblemDTO;
import com.kafu.kafu.problem.dto.ProblemDetailsDTO;
import com.kafu.kafu.problem.dto.ProblemRejectionDTO;
import com.kafu.kafu.problem.dto.ProblemSearchCriteria;
import com.kafu.kafu.problem.dto.RealFieldsDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<Page<ProblemDTO>> findAll(@ModelAttribute ProblemSearchCriteria criteria,
                                                    @PageableDefault(size = 20) Pageable pageable) {
        criteria.setPageable(pageable);
        return ResponseEntity.ok(problemService.search(criteria).map(ProblemMapper::toDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProblemDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ProblemMapper.toDTO(problemService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ProblemDTO> create(@Valid @RequestBody ProblemDTO problemDTO) {
        return ResponseEntity.ok(ProblemMapper.toDTO(problemService.create(problemDTO)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProblemDTO> update(@PathVariable Long id, @Valid @RequestBody ProblemDTO problemDTO) {
        return ResponseEntity.ok(ProblemMapper.toDTO(problemService.update(id, problemDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        problemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ProblemDTO> approve(@PathVariable Long id) {
        return ResponseEntity.ok(ProblemMapper.toDTO(problemService.approve(id)));
    }

    @PatchMapping("/{id}/real-fields")
    public ResponseEntity<ProblemDTO> updateRealFields(@PathVariable Long id, @RequestBody RealFieldsDTO realFieldsDTO) {
        return ResponseEntity.ok(ProblemMapper.toDTO(problemService.updateRealFields(id, realFieldsDTO)));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<ProblemDTO> reject(@PathVariable Long id, @Valid @RequestBody ProblemRejectionDTO rejectionDTO) {
        return ResponseEntity.ok(ProblemMapper.toDTO(problemService.reject(id, rejectionDTO)));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<ProblemDTO> updateDetails(@PathVariable Long id, @RequestBody ProblemDetailsDTO detailsDTO) {
        return ResponseEntity.ok(ProblemMapper.toDTO(problemService.updateDetails(id, detailsDTO)));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<ProblemDTO>> findByUserId(@PathVariable Long userId) {
        List<ProblemDTO> problems = problemService.findBySubmittedByUserId(userId)
                .stream()
                .map(ProblemMapper::toDTO)
                .toList();
        return ResponseEntity.ok(problems);
    }
}
