package com.kafu.kafu.problem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kafu.kafu.problem.ProblemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private Boolean isReal = false;

    private Boolean forContribution = false;

    private Boolean forDonation = false;

    private LocalDateTime submissionDate;

    private ProblemStatus status;

    private String rejectionReason;

    @NotNull(message = "Address ID is required")
    private Long addressId;

    private Long submittedByUserId;

    private Long approvedByUserId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
