package com.kafu.kafu.problem.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserProblemSearchCriteria extends ProblemSearchCriteria {
    private Long userId;
}