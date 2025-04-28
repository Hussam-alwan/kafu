package com.kafu.kafu.solution;

import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.solution.dto.SolutionDTO;
import com.kafu.kafu.solution.dto.SolutionSearchCriteria;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final ProblemService problemService;
    private final UserService userService;

//    public Page<Solution> findAll(Pageable pageable) {
//        return solutionRepository.findAll(pageable);
//    }

    public Page<Solution> search(SolutionSearchCriteria criteria, Pageable pageable) {
        return solutionRepository.findAll(SolutionSpecification.withSearchCriteria(criteria), pageable);
    }

    public Solution findById(Long id) {
        return solutionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solution not found"));
    }

    public List<Solution> findByProposedByUserId(Long userId) {
        return solutionRepository.findByProposedByUserId_Id(userId);
    }

    public List<Solution> findByProblemId(Long problemId) {
        return solutionRepository.findByProblem_Id(problemId);
    }

    @Transactional
    public Solution create(SolutionDTO solutionDTO) {
        Solution solution = new Solution();
        solution.setDescription(solutionDTO.getDescription());
        solution.setEstimatedCost(solutionDTO.getEstimatedCost());
        // Handle relations
        if (solutionDTO.getProblemId() != null) {
            solution.setProblem(problemService.findById(solutionDTO.getProblemId()));
        }

        solution.setProposedByUserId(userService.getCurrentUser());

        if (solutionDTO.getAcceptedByUserId() != null) {
            solution.setAcceptedByUserId(userService.findById(solutionDTO.getAcceptedByUserId()));
        }
        solution.setStatus(SolutionStatus.PENDING_APPROVAL);
        SolutionMapper.toEntity(solutionDTO);
        solution = solutionRepository.save(solution);
        return solution;
    }

    @Transactional
    public Solution update(Long id, SolutionDTO solutionDTO) {
        Solution solution = findById(id);
        // Handle relations
        if (solutionDTO.getProblemId() != null) {
            solution.setProblem(problemService.findById(solutionDTO.getProblemId()));
        }
        if (solutionDTO.getProposedByUserId() != null) {
            solution.setProposedByUserId(userService.findById(solutionDTO.getProposedByUserId()));
        }
        if (solutionDTO.getAcceptedByUserId() != null) {
            solution.setAcceptedByUserId(userService.findById(solutionDTO.getAcceptedByUserId()));
        }
        SolutionMapper.toEntity(solutionDTO);
        solution = solutionRepository.save(solution);
        return solution;
    }

    @Transactional
    public void delete(Long id) {
        solutionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solution not found"));
        
        try {
            solutionRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete solution as it is being referenced by other entities");
        }
    }
}
