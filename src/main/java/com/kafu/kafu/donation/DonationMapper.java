package com.kafu.kafu.donation;

import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DonationMapper {
    private final ProblemService problemService;
    private final UserService userService;

    public DonationDTO toDTO(Donation entity) {
        if (entity == null) {
            return null;
        }

        DonationDTO dto = new DonationDTO();
        dto.setId(entity.getId());
        dto.setProblemId(entity.getProblem().getId());
        dto.setDonorId(entity.getDonor().getId());
        dto.setAmount(entity.getAmount());
        dto.setFee(entity.getFee());
        dto.setNetAmount(entity.getNetAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentTransactionId(entity.getPaymentTransactionId());
        dto.setStatus(entity.getStatus());
        dto.setIsAnonymous(entity.getIsAnonymous());
        dto.setDonationDate(entity.getDonationDate());
        return dto;
    }

    public Donation toEntity(DonationDTO dto) {
        if (dto == null) {
            return null;
        }

        Donation entity = new Donation();
        updateEntity(entity, dto);
        return entity;
    }

    public void updateEntity(Donation entity, DonationDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getAmount() != null) {
            entity.setAmount(dto.getAmount());
        }
        if (dto.getFee() != null) {
            entity.setFee(dto.getFee());
        }
        if (dto.getNetAmount() != null) {
            entity.setNetAmount(dto.getNetAmount());
        }
        if (dto.getCurrency() != null) {
            entity.setCurrency(dto.getCurrency());
        }
        if (dto.getPaymentMethod() != null) {
            entity.setPaymentMethod(dto.getPaymentMethod());
        }
        if (dto.getPaymentTransactionId() != null) {
            entity.setPaymentTransactionId(dto.getPaymentTransactionId());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getIsAnonymous() != null) {
            entity.setIsAnonymous(dto.getIsAnonymous());
        }
        if (dto.getDonationDate() != null) {
            entity.setDonationDate(dto.getDonationDate());
        }
        if (dto.getProblemId() != null) {
            entity.setProblem(problemService.getProblemEntity(dto.getProblemId()));
        }
        if (dto.getDonorId() != null) {
            entity.setDonor(userService.getUserEntity(dto.getDonorId()));
        }
    }
}
