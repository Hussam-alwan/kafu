package com.kafu.kafu.problem;

import com.kafu.kafu.address.AddressService;
import com.kafu.kafu.gov.GovService;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemMapper {
    private final AddressService addressService;
    private final UserService userService;
    private final GovService govService;

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
        dto.setSubmittedById(entity.getSubmittedBy().getId());
        dto.setApprovedById(entity.getApprovedBy() != null ? entity.getApprovedBy().getId() : null);
        dto.setAddressId(entity.getAddress().getId());
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
        if (dto.getSubmittedById() != null) {
            entity.setSubmittedBy(userService.getUserEntity(dto.getSubmittedById()));
        }
        if (dto.getApprovedById() != null) {
            entity.setApprovedBy(govService.getGovEntity(dto.getApprovedById()));
        }
        if (dto.getAddressId() != null) {
            entity.setAddress(addressService.getAddressEntity(dto.getAddressId()));
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }
}
