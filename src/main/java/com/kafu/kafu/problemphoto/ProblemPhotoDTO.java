package com.kafu.kafu.problemphoto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String s3Key;

    private LocalDateTime photoDate;
    
    private Long progressId;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String presignedUrl;
}
