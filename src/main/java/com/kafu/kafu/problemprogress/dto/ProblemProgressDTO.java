package com.kafu.kafu.problemprogress.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProblemProgressDTO {
    private Long id;

    @NotNull(message = "Percentage is required")
    @Min(value = 0, message = "Percentage must be between 0 and 100")
    @Max(value = 100, message = "Percentage must be between 0 and 100")
    private Integer percentage;

    @NotBlank(message = "Comment is required")
    private String comment;

    private LocalDateTime progressDate;

    @NotNull(message = "Problem ID is required")
    private Long problemId;

    @NotNull(message = "Solution ID is required")
    private Long solutionId;

    private List<Long> photoIds;
}