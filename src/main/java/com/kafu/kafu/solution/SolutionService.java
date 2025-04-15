package com.kafu.kafu.solution;

import com.kafu.kafu.gov.GovService;
import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final SolutionMapper solutionMapper;
    private final ProblemService problemService;
    private final UserService userService;
    private final GovService govService;

    public Page<SolutionDTO> findAll(Pageable pageable) {
        return solutionRepository.findAll(pageable)
                .map(solutionMapper::toDTO);
    }

    public List<SolutionDTO> findByProblemId(Long problemId) {
        return solutionRepository.findByProblemId(problemId)
                .stream()
                .map(solutionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<SolutionDTO> findByProposedById(Long userId) {
        return solutionRepository.findByProposedById(userId)
                .stream()
                .map(solutionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<SolutionDTO> findByAcceptedById(Long govId) {
        return solutionRepository.findByAcceptedById(govId)
                .stream()
                .map(solutionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SolutionDTO findById(Long id) {
        return solutionRepository.findById(id)
                .map(solutionMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solution not found"));
    }

    public Solution getSolutionEntity(Long id) {
        return solutionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solution not found"));
    }

    @Transactional
    public SolutionDTO create(SolutionDTO solutionDTO) {
        if (solutionDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new solution cannot already have an ID");
        }

        // Verify that the problem exists
        problemService.findById(solutionDTO.getProblemId());

        // Verify that the proposing user exists
        userService.findById(solutionDTO.getProposedById());

        // Verify that the accepting gov exists if provided
        if (solutionDTO.getAcceptedById() != null) {
            govService.findById(solutionDTO.getAcceptedById());
        }

        Solution solution = solutionMapper.toEntity(solutionDTO);
        solution = solutionRepository.save(solution);
        return solutionMapper.toDTO(solution);
    }

    @Transactional
    public SolutionDTO update(Long id, SolutionDTO solutionDTO) {
        Solution solution = solutionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solution not found"));

        // Verify that the problem exists if it's being updated
        if (solutionDTO.getProblemId() != null) {
            problemService.findById(solutionDTO.getProblemId());
        }

        // Verify that the proposing user exists if it's being updated
        if (solutionDTO.getProposedById() != null) {
            userService.findById(solutionDTO.getProposedById());
        }

        // Verify that the accepting gov exists if it's being updated
        if (solutionDTO.getAcceptedById() != null) {
            govService.findById(solutionDTO.getAcceptedById());
        }

        solutionMapper.updateEntity(solution, solutionDTO);
        solution = solutionRepository.save(solution);
        return solutionMapper.toDTO(solution);
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
