package com.kafu.kafu.solution;

import com.kafu.kafu.exception.ApplicationErrorEnum;
import com.kafu.kafu.exception.BusinessException;
import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.solution.dto.SolutionDTO;
import com.kafu.kafu.solution.dto.SolutionWithSubmitterDTO;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final ProblemService problemService;
    private final UserService userService;

    public Solution findById(Long id) {
        return solutionRepository.findById(id).orElseThrow(() -> new BusinessException(ApplicationErrorEnum.SOLUTION_NOT_FOUND));
    }

    public List<Solution> findSolutionsProposedByCurrentUser() {
        Long userId = userService.getCurrentUser().getId();
        return solutionRepository.findByProposedByUserId_Id(userId);
    }

    public List<Solution> findByProblemId(Long problemId) {
        return solutionRepository.findByProblem_Id(problemId);
    }

    public List<SolutionWithSubmitterDTO> findSolutionWithSubmitterByProblemId(Long problemId) {
        return solutionRepository.findSolutionWithSubmitterByProblemId(problemId);
    }

    @Transactional
    public Solution create(SolutionDTO solutionDTO) {
        Solution solution = new Solution();
        solution.setDescription(solutionDTO.getDescription());
        solution.setEstimatedCost(solutionDTO.getEstimatedCost());
        solution.setCreationDate(LocalDate.now());
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
        SolutionMapper.updateEntity(solution,solutionDTO);
        solution = solutionRepository.save(solution);
        return solution;
    }

    @Transactional
    public void delete(Long id) {
        solutionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.SOLUTION_NOT_FOUND));
        
        try {
            solutionRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete solution as it is being referenced by other entities");
        }
    }

    @Transactional
    public Solution updateStatus(Long id, SolutionStatus newStatus) {
        Solution solution = findById(id);
        SolutionStatus current = solution.getStatus();

        switch (newStatus) {
            case APPROVED -> {
                if (current != SolutionStatus.PENDING_APPROVAL)
                    throw new BusinessException(ApplicationErrorEnum.INVALID_PROBLEM_STATUS);
                solution.setStatus(SolutionStatus.APPROVED);
                solution.setAcceptedByUserId(userService.getCurrentUser());
            }
            case REJECTED -> {
                if (current != SolutionStatus.PENDING_APPROVAL)
                    throw new BusinessException(ApplicationErrorEnum.INVALID_PROBLEM_STATUS);
                solution.setStatus(SolutionStatus.REJECTED);
                solution.setAcceptedByUserId(userService.getCurrentUser());
            }
            case PENDING_FUNDING ->
                solution.setStatus(SolutionStatus.PENDING_FUNDING);
            case WORK_IN_PROGRESS ->
                solution.setStatus(SolutionStatus.WORK_IN_PROGRESS);
            case RESOLVED ->
                solution.setStatus(SolutionStatus.RESOLVED);
            default -> throw new BusinessException(ApplicationErrorEnum.INVALID_PROBLEM_STATUS);
        }
        return solutionRepository.save(solution);
    }
}
