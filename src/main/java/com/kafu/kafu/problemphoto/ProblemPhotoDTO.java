package com.kafu.kafu.problemphoto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProblemPhotoDTO {
    private Long id;

    @NotNull(message = "Problem ID is required")
    private Long problemId;

    @NotBlank(message = "Photo URL is required")
    private String photoUrl;

    private LocalDateTime photoDate;
}
