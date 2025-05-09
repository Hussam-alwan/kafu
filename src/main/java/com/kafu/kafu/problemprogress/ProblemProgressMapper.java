package com.kafu.kafu.problemprogress;

import com.kafu.kafu.problemprogress.dto.ProblemProgressDTO;
import com.kafu.kafu.problemphoto.ProblemPhoto;
import java.util.stream.Collectors;

public class ProblemProgressMapper {
    public static ProblemProgressDTO toDTO(ProblemProgress entity) {
        if (entity == null) return null;
        
        ProblemProgressDTO dto = new ProblemProgressDTO();
        dto.setId(entity.getId());
        dto.setPercentage(entity.getPercentage());
        dto.setComment(entity.getComment());
        dto.setProgressDate(entity.getProgressDate());
        dto.setProblemId(entity.getProblem()!=null? entity.getProblem().getId():null);
        dto.setSolutionId(entity.getSolution()!=null? entity.getSolution().getId():null);
        
        if (entity.getPhotos() != null) {
            dto.setPhotoIds(entity.getPhotos().stream()
                .map(ProblemPhoto::getId)
                .collect(Collectors.toList()));
        }
        return dto;
    }
}