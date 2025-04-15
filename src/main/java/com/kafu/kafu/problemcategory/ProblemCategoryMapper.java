package com.kafu.kafu.problemcategory;

import com.kafu.kafu.gov.GovService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemCategoryMapper {
    private final GovService govService;

    public ProblemCategoryDTO toDTO(ProblemCategory entity) {
        if (entity == null) {
            return null;
        }

        ProblemCategoryDTO dto = new ProblemCategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setGovId(entity.getGov() != null ? entity.getGov().getId() : null);
        return dto;
    }

    public ProblemCategory toEntity(ProblemCategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        ProblemCategory entity = new ProblemCategory();
        updateEntity(entity, dto);
        return entity;
    }

    public void updateEntity(ProblemCategory entity, ProblemCategoryDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getGovId() != null) {
            entity.setGov(govService.getGovEntity(dto.getGovId()));
        }
    }
}
