package com.kafu.kafu.address;

import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    
    public AddressDTO toDTO(Address entity) {
        if (entity == null) {
            return null;
        }

        AddressDTO dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setDescription(entity.getDescription());
        dto.setCity(entity.getCity());
        return dto;
    }

    public Address toEntity(AddressDTO dto) {
        if (dto == null) {
            return null;
        }

        Address entity = new Address();
        updateEntity(entity, dto);
        return entity;
    }

    public void updateEntity(Address entity, AddressDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getLatitude() != null) {
            entity.setLatitude(dto.getLatitude());
        }
        if (dto.getLongitude() != null) {
            entity.setLongitude(dto.getLongitude());
        }
        if (dto.getAddress() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getCity() != null) {
            entity.setCity(dto.getCity());
        }
    }
}
