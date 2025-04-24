package com.kafu.kafu.problem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProblemRejectionDTO {
    @NotNull(message = "isReal field is required")
    private Boolean isReal;

    @NotBlank(message = "Rejection reason is required")
    private String rejectionReason;
}
