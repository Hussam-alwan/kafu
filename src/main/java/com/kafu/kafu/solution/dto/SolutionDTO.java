package com.kafu.kafu.solution.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kafu.kafu.solution.SolutionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolutionDTO {
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Estimated cost is required")
    private BigDecimal estimatedCost;

    private SolutionStatus status;

    private String acceptedReason;
    private LocalDate startDate;
    private LocalDate endDate;
    private String feedback;
    private Integer rating;

    @NotNull(message = "Problem ID is required")
    private Long problemId;

    private Long proposedByUserId;

    private Long acceptedByUserId;
}
