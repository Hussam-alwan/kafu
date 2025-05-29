package com.kafu.kafu.solution.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.kafu.kafu.solution.SolutionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionWithSubmitterDTO {
    private Long id;
    private String description;
    private BigDecimal estimatedCost;
    private LocalDate creationDate;
    private Long problemId;
    private Long proposedByUserId;
    private String submitterFirstName;
    private String submitterLastName;
    private SolutionStatus status;
}