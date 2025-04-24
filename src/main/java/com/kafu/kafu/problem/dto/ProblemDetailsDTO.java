package com.kafu.kafu.problem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetailsDTO {

    private String title;

    private String description;

    private Long addressId;

    private Long categoryId;
}
