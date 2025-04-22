package com.kafu.kafu.gov;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GovMapper {

    public static GovDTO toDTO(Gov entity) {
        if (entity == null) {
            return null;
        }
        GovDTO dto = new GovDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setLogoUrl(entity.getLogoUrl());
        dto.setAddressId(entity.getAddress() != null ? entity.getAddress().getId() : null);
        dto.setParentGovId(entity.getParentGov() != null ? entity.getParentGov().getId() : null);
        return dto;
    }

    public static Gov toEntity(GovDTO dto) {
        if (dto == null) {
            return null;
        }
        Gov entity = new Gov();
        updateEntity(entity, dto);
        return entity;
    }

    public static void updateEntity(Gov entity, GovDTO dto) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getLogoUrl() != null) {
            entity.setLogoUrl(dto.getLogoUrl());
        }
        // Address and parentGov will be set by the service layer, ignore here
    }
}
