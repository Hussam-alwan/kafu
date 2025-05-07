package com.kafu.kafu.problemcategory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import com.kafu.kafu.problemcategory.dto.ProblemCategorySearchCriteria;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/v1/problem-categories")
@RequiredArgsConstructor
public class ProblemCategoryController {
    private final ProblemCategoryService problemCategoryService;

    @GetMapping
    public ResponseEntity<Page<ProblemCategoryDTO>> search(
            @ModelAttribute ProblemCategorySearchCriteria criteria,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(problemCategoryService.search(criteria, pageable)
                .map(ProblemCategoryMapper::toDTO));
    }

    @GetMapping("/by-gov/{govId}")
    public ResponseEntity<List<ProblemCategoryDTO>> findByGovId(@PathVariable Long govId) {
        return ResponseEntity.ok(problemCategoryService.
                findByGovId(govId)
                .stream()
                .map(ProblemCategoryMapper::toDTO)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemCategoryDTO> findById(@PathVariable Long id) {
        ProblemCategory problemCategory = problemCategoryService.findById(id);
        return ResponseEntity.ok(ProblemCategoryMapper.toDTO(problemCategory));
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemCategoryDTO> create(@Valid @RequestBody ProblemCategoryDTO problemCategoryDTO) {
        ProblemCategory created = problemCategoryService.create(problemCategoryDTO);
        return ResponseEntity
                .created(URI.create("/api/v1/problem-categories/" + created.getId()))
                .body(ProblemCategoryMapper.toDTO(created));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
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
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        problemCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
