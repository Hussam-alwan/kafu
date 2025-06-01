package com.kafu.kafu.solution;

import com.kafu.kafu.solution.dto.SolutionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/solutions")
@RequiredArgsConstructor
public class UserSolutionController {
    private final SolutionService solutionService;

    @GetMapping("/me")
    public ResponseEntity<List<SolutionDTO>> getAllMySolutions() {
        List<SolutionDTO> solutions = solutionService.findAllSolutionsProposedByCurrentUser()
            .stream()
            .map(SolutionMapper::toDTO)
            .toList();
        return ResponseEntity.ok(solutions);
    }
}
