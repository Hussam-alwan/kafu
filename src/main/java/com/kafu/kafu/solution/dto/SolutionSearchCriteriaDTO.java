package com.kafu.kafu.solution.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class SolutionSearchCriteriaDTO {
    private String description;
    private Long problemId;
    private Long proposedByUserId;
    private Long acceptedByUserId;
    private Pageable pageable;
}
