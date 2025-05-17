package com.kafu.kafu.donation;

public class DonationMapper {

    public static DonationDTO toDTO(Donation entity) {
        if (entity == null) {
            return null;
        }
        DonationDTO dto = new DonationDTO();
        dto.setId(entity.getId());
        dto.setProblemId(entity.getProblem() != null ? entity.getProblem().getId() : null);
        dto.setDonorId(entity.getDonor() != null ? entity.getDonor().getId() : null);
        dto.setAmount(entity.getAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentTransactionId(entity.getPaymentTransactionId());
        dto.setStatus(entity.getStatus());
        dto.setIsAnonymous(entity.getIsAnonymous());
        dto.setDonationDate(entity.getDonationDate());
        dto.setIdempotencyKey(entity.getIdempotencyKey());
        return dto;
    }

    public static Donation toEntity(DonationDTO dto) {
        if (dto == null) {
            return null;
        }
        Donation entity = new Donation();
        updateEntity(entity, dto);
        return entity;
    }

    public static void updateEntity(Donation entity, DonationDTO dto) {
        if (dto == null) {
            return;
        }
        if (dto.getAmount() != null) {
            entity.setAmount(dto.getAmount());
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
        if (dto.getIdempotencyKey() != null) {
            entity.setIdempotencyKey(dto.getIdempotencyKey());
        }
        // Relations (problem, donor) handled in service
    }
}
