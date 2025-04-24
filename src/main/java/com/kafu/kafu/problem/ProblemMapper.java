package com.kafu.kafu.problem;

import com.kafu.kafu.problem.dto.ProblemDTO;

public class ProblemMapper {

    public static ProblemDTO toDTO(Problem entity) {
        if (entity == null) {
            return null;
        }

        ProblemDTO dto = new ProblemDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setIsReal(entity.getIsReal());
        dto.setForContribution(entity.getForContribution());
        dto.setForDonation(entity.getForDonation());
        dto.setSubmissionDate(entity.getSubmissionDate());
        dto.setAddressId(entity.getAddress() != null ? entity.getAddress().getId() : null);
        dto.setSubmittedByUserId(entity.getSubmittedByUser() != null ? entity.getSubmittedByUser().getId() : null);
        dto.setApprovedByUserId(entity.getApprovedByUser() != null ? entity.getApprovedByUser().getId() : null);
        dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getId() : null);
        dto.setRejectionReason(entity.getRejectionReason());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static Problem toEntity(ProblemDTO dto) {
        if (dto == null) {
            return null;
        }

        Problem entity = new Problem();
        updateEntity(entity, dto);
        return entity;
    }

    public static void updateEntity(Problem entity, ProblemDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getIsReal() != null) {
            entity.setIsReal(dto.getIsReal());
        }
        if (dto.getForContribution() != null) {
            entity.setForContribution(dto.getForContribution());
        }
        if (dto.getForDonation() != null) {
            entity.setForDonation(dto.getForDonation());
        }
        if (dto.getSubmissionDate() != null) {
            entity.setSubmissionDate(dto.getSubmissionDate());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getRejectionReason() != null) {
            entity.setRejectionReason(dto.getRejectionReason());
        }
    }
}
