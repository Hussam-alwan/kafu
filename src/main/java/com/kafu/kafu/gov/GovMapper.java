package com.kafu.kafu.gov;

import com.kafu.kafu.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GovMapper {
    private final AddressService addressService;

    public GovDTO toDTO(Gov entity) {
        if (entity == null) {
            return null;
        }

        GovDTO dto = new GovDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPhotoUrl(entity.getLogoUrl());
        dto.setAddressId(entity.getAddress() != null ? entity.getAddress().getId() : null);
        return dto;
    }

    public Gov toEntity(GovDTO dto) {
        if (dto == null) {
            return null;
        }

        Gov entity = new Gov();
        updateEntity(entity, dto);
        return entity;
    }

    public void updateEntity(Gov entity, GovDTO dto) {
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
        if (dto.getPhotoUrl() != null) {
            entity.setLogoUrl(dto.getPhotoUrl());
        }
        if (dto.getAddressId() != null) {
            entity.setAddress(addressService.getAddressEntity(dto.getAddressId()));
        }
    }
}
