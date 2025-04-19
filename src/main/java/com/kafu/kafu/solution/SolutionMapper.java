package com.kafu.kafu.solution;

import com.kafu.kafu.gov.GovService;
import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolutionMapper {
    private final ProblemService problemService;
    private final UserService userService;
    private final GovService govService;

    public SolutionDTO toDTO(Solution entity) {
        if (entity == null) {
            return null;
        }

        SolutionDTO dto = new SolutionDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setEstimatedCost(entity.getEstimatedCost());
        dto.setStatus(entity.getStatus());
        dto.setAcceptedReason(entity.getAcceptedReason());
        dto.setStartDate(entity.getStartDate());
        dto.setCompletionDate(entity.getCompletionDate());
        dto.setFeedback(entity.getFeedback());
        dto.setRating(entity.getRating());
        dto.setProblemId(entity.getProblem().getId());
        dto.setProposedById(entity.getProposedBy().getId());
        dto.setAcceptedByGovId(entity.getAcceptedByGovId() != null ? entity.getAcceptedByGovId().getId() : null);
        return dto;
    }

    public Solution toEntity(SolutionDTO dto) {
        if (dto == null) {
            return null;
        }

        Solution entity = new Solution();
        updateEntity(entity, dto);
        return entity;
    }

    public void updateEntity(Solution entity, SolutionDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getEstimatedCost() != null) {
            entity.setEstimatedCost(dto.getEstimatedCost());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getAcceptedReason() != null) {
            entity.setAcceptedReason(dto.getAcceptedReason());
        }
        if (dto.getStartDate() != null) {
            entity.setStartDate(dto.getStartDate());
        }
        if (dto.getCompletionDate() != null) {
            entity.setCompletionDate(dto.getCompletionDate());
        }
        if (dto.getFeedback() != null) {
            entity.setFeedback(dto.getFeedback());
        }
        if (dto.getRating() != null) {
            entity.setRating(dto.getRating());
        }
        if (dto.getProblemId() != null) {
            entity.setProblem(problemService.getProblemEntity(dto.getProblemId()));
        }
        if (dto.getProposedById() != null) {
            entity.setProposedBy(userService.getUserEntity(dto.getProposedById()));
        }
        if (dto.getAcceptedByGovId() != null) {
            entity.setAcceptedByGovId(govService.getGovEntity(dto.getAcceptedByGovId()));
        }
    }
}
