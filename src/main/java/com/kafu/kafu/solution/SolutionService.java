package com.kafu.kafu.solution;

import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.user.UserService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final ProblemService problemService;
    private final UserService userService;

    public Page<Solution> findAll(Pageable pageable) {
        return solutionRepository.findAll(pageable);
    }

    public Page<Solution> search(SolutionSearchCriteria criteria, Pageable pageable) {
        Specification<Solution> spec = (root, query, cb) -> {
            Predicate p = cb.conjunction();
            if (criteria.getDescription() != null) {
                p = cb.and(p, cb.like(cb.lower(root.get("description")), "%" + criteria.getDescription().toLowerCase() + "%"));
            }
            if (criteria.getProblemId() != null) {
                p = cb.and(p, cb.equal(root.get("problem").get("id"), criteria.getProblemId()));
            }
            if (criteria.getProposedByUserId() != null) {
                p = cb.and(p, cb.equal(root.get("proposedByUserId").get("id"), criteria.getProposedByUserId()));
            }
            if (criteria.getAcceptedByUserId() != null) {
                p = cb.and(p, cb.equal(root.get("acceptedByUserId").get("id"), criteria.getAcceptedByUserId()));
            }
            return p;
        };
        return solutionRepository.findAll(spec, pageable);
    }

    public Solution findById(Long id) {
        return solutionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solution not found"));
    }

    @Transactional
    public Solution create(SolutionDTO solutionDTO) {
        Solution solution = new Solution();
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
