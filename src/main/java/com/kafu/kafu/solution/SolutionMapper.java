package com.kafu.kafu.solution;

import com.kafu.kafu.solution.dto.SolutionDTO;

public class SolutionMapper {

    public static SolutionDTO toDTO(Solution entity) {
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
        dto.setEndDate(entity.getEndDate());
        dto.setFeedback(entity.getFeedback());
        dto.setRating(entity.getRating());
        dto.setProblemId(entity.getProblem() != null ? entity.getProblem().getId() : null);
        dto.setProposedByUserId(entity.getProposedByUserId() != null ? entity.getProposedByUserId().getId() : null);
        dto.setAcceptedByUserId(entity.getAcceptedByUserId() != null ? entity.getAcceptedByUserId().getId() : null);
        return dto;
    }

    public static Solution toEntity(SolutionDTO dto) {
        if (dto == null) {
            return null;
        }

        Solution entity = new Solution();
        updateEntity(entity, dto);
        return entity;
    }

    public static void updateEntity(Solution entity, SolutionDTO dto) {
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
        if (dto.getEndDate() != null) {
            entity.setEndDate(dto.getEndDate());
        }
        if (dto.getFeedback() != null) {
            entity.setFeedback(dto.getFeedback());
        }
        if (dto.getRating() != null) {
            entity.setRating(dto.getRating());
        }
        // Problem, proposedByUserId, acceptedByUserId handled in service
    }
}
