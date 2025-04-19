package com.kafu.kafu.problem;

import com.kafu.kafu.address.AddressMapper;
import com.kafu.kafu.gov.GovService;
import com.kafu.kafu.problemcategory.ProblemCategoryService;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemMapper {
    private final AddressMapper addressMapper;
    private final UserService userService;
    private final GovService govService;
    private final ProblemCategoryService problemCategoryService;

    public ProblemDTO toDTO(Problem entity) {
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
        dto.setAddress(addressMapper.toDTO(entity.getAddress()));
        dto.setSubmittedByUserId(entity.getSubmittedByUser() != null ? entity.getSubmittedByUser().getId() : null);
        dto.setApprovedByGovId(entity.getApprovedByGov() != null ? entity.getApprovedByGov().getId() : null);
        dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getId() : null);
        dto.setSubmittedAt(entity.getSubmittedAt());
        dto.setApprovedAt(entity.getApprovedAt());
        dto.setRejectedAt(entity.getRejectedAt());
        dto.setResolvedAt(entity.getResolvedAt());
        dto.setRejectionReason(entity.getRejectionReason());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public Problem toEntity(ProblemDTO dto) {
        if (dto == null) {
            return null;
        }

        Problem entity = new Problem();
        updateEntity(entity, dto);
        return entity;
    }

    public void updateEntity(Problem entity, ProblemDTO dto) {
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
        if (dto.getAddress() != null) {
            entity.setAddress(addressMapper.toEntity(dto.getAddress()));
        }
        if (dto.getSubmittedByUserId() != null) {
            entity.setSubmittedByUser(userService.getUserEntity(dto.getSubmittedByUserId()));
        }
        if (dto.getApprovedByGovId() != null) {
            entity.setApprovedByGov(govService.getGovEntity(dto.getApprovedByGovId()));
        }
        if (dto.getCategoryId() != null) {
            entity.setCategory(problemCategoryService.findById(dto.getCategoryId()));
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }
}
