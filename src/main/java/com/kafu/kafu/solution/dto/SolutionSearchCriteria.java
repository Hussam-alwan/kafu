package com.kafu.kafu.solution.dto;

import lombok.Data;

@Data
public class SolutionSearchCriteria {
    private String description;
    private Long problemId;
    private Long proposedByUserId;
    private Long acceptedByUserId;
}
