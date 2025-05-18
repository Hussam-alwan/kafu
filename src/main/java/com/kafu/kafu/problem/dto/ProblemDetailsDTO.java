package com.kafu.kafu.problem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetailsDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Address ID is required")
    private Long addressId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
