package com.kafu.kafu.problem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kafu.kafu.address.AddressDTO;
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

    @NotNull(message = "Address is required")
    private AddressDTO address;

    @NotNull(message = "Submitted by user ID is required")
    private Long submittedByUserId;
    private Long approvedByGovId;

    private LocalDateTime submittedAt;
 
    private LocalDateTime approvedAt;
  
    private LocalDateTime rejectedAt;
    
    private LocalDateTime resolvedAt;

    private String rejectionReason;

    @NotNull(message = "Status is required")
    private ProblemStatus status;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
