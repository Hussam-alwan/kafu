package com.kafu.kafu.gov;

import com.kafu.kafu.address.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GovMapper {
    private final AddressMapper addressMapper;

    public GovDTO toDTO(Gov entity) {
        if (entity == null) {
            return null;
        }

        GovDTO dto = new GovDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setLogoUrl(entity.getLogoUrl());
        dto.setAddress(addressMapper.toDTO(entity.getAddress()));
        dto.setParentGovId(entity.getParentGov() != null ? entity.getParentGov().getId() : null);
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
        if (dto.getLogoUrl() != null) {
            entity.setLogoUrl(dto.getLogoUrl());
        }
        // Address and parentGov will be set by the service layer
    }
}
