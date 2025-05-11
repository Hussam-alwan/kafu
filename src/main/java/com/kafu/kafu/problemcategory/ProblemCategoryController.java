package com.kafu.kafu.problemcategory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import com.kafu.kafu.problemcategory.dto.ProblemCategorySearchCriteria;

@RestController
@RequestMapping("/api/v1/problem-categories")
@RequiredArgsConstructor
public class ProblemCategoryController {
    private final ProblemCategoryService problemCategoryService;

    @GetMapping
    public ResponseEntity<List<ProblemCategoryDTO>> search(
            @ModelAttribute ProblemCategorySearchCriteria criteria) {
        return ResponseEntity.ok(problemCategoryService.search(criteria).stream()
                .map(ProblemCategoryMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemCategoryDTO> findById(@PathVariable Long id) {
        ProblemCategory problemCategory = problemCategoryService.findById(id);
        return ResponseEntity.ok(ProblemCategoryMapper.toDTO(problemCategory));
    }

    @PostMapping
    public ResponseEntity<ProblemCategoryDTO> create(@Valid @RequestBody ProblemCategoryDTO problemCategoryDTO) {
        ProblemCategory created = problemCategoryService.create(problemCategoryDTO);
        return ResponseEntity
                .created(URI.create("/api/v1/problem-categories/" + created.getId()))
                .body(ProblemCategoryMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProblemCategoryDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProblemCategoryDTO problemCategoryDTO) {
        return ResponseEntity.ok(
                ProblemCategoryMapper.toDTO(
                    problemCategoryService.update(id, problemCategoryDTO)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        problemCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
