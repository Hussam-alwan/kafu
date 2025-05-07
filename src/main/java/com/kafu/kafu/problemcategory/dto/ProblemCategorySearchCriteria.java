package com.kafu.kafu.problemcategory.dto;

import lombok.Data;

@Data
public class ProblemCategorySearchCriteria {
    private String name;
    private String govName;
    private Long govId;
}