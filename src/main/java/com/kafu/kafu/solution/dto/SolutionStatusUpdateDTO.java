package com.kafu.kafu.solution.dto;

import com.kafu.kafu.solution.SolutionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolutionStatusUpdateDTO {
    @NotNull
    private SolutionStatus status;
}
