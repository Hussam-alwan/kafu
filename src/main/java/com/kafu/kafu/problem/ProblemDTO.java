package com.kafu.kafu.problem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProblemDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private Boolean isReal = false;
    private Boolean forContribution = false;
    private Boolean forDonation = false;
    private LocalDateTime submissionDate;

    @NotNull(message = "Submitted by user ID is required")
    private Long submittedById;

    private Long approvedById;

    @NotNull(message = "Address ID is required")
    private Long addressId;

    @NotNull(message = "Status is required")
    private ProblemStatus status;
}
