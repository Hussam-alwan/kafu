package com.kafu.kafu.problemphoto;

import com.kafu.kafu.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemPhotoMapper {
    private final ProblemService problemService;

    public static ProblemPhotoDTO toDTO(ProblemPhoto entity) {
        if (entity == null) {
            return null;
        }

        ProblemPhotoDTO dto = new ProblemPhotoDTO();
        dto.setId(entity.getId());
        dto.setProblemId(entity.getProblem().getId());
        dto.setS3Key(entity.getS3Key());
        dto.setPhotoDate(entity.getPhotoDate());
        return dto;
    }

    public ProblemPhoto toEntity(ProblemPhotoDTO dto) {
        if (dto == null) {
            return null;
        }

        ProblemPhoto entity = new ProblemPhoto();
        updateEntity(entity, dto);
        return entity;
    }

    public void updateEntity(ProblemPhoto entity, ProblemPhotoDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getProblemId() != null) {
            entity.setProblem(problemService.findById(dto.getProblemId()));
        }
        if (dto.getS3Key() != null) {
            entity.setS3Key(dto.getS3Key());
        }
        if (dto.getPhotoDate() != null) {
            entity.setPhotoDate(dto.getPhotoDate());
        }
    }
}
