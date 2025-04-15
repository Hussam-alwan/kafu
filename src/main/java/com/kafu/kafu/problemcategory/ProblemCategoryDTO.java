package com.kafu.kafu.problemcategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProblemCategoryDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @NotNull(message = "Gov ID is required")
    private Long govId;
}
