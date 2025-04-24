package com.kafu.kafu.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public static UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setCollegeDegree(entity.getCollegeDegree());
        dto.setJob(entity.getJob());
        dto.setCvUrl(entity.getCvUrl());
        dto.setPhotoUrl(entity.getPhotoUrl());
        dto.setDescription(entity.getDescription());
        dto.setAddressId(entity.getAddress().getId());
        dto.setGovId(entity.getGov().getId());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        updateEntity(entity, dto);
        return entity;
    }

    public static void updateEntity(User entity, UserDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getDateOfBirth() != null) {
            entity.setDateOfBirth(dto.getDateOfBirth());
        }
        if (dto.getCollegeDegree() != null) {
            entity.setCollegeDegree(dto.getCollegeDegree());
        }
        if (dto.getJob() != null) {
            entity.setJob(dto.getJob());
        }
        if (dto.getCvUrl() != null) {
            entity.setCvUrl(dto.getCvUrl());
        }
        if (dto.getPhotoUrl() != null) {
            entity.setPhotoUrl(dto.getPhotoUrl());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
    }
}
