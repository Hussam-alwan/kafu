package com.kafu.kafu.solution;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class SolutionDTO {
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Estimated cost is required")
    private BigDecimal estimatedCost;

    @NotNull(message = "Status is required")
    private SolutionStatus status;

    private String acceptedReason;
    private LocalDate startDate;
    private LocalDate completionDate;
    private String feedback;
    private Integer rating;

    @NotNull(message = "Problem ID is required")
    private Long problemId;

    @NotNull(message = "Proposed by user ID is required")
    private Long proposedById;

    private Long acceptedById;
}
