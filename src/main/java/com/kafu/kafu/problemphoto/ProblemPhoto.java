package com.kafu.kafu.problemphoto;

import com.kafu.kafu.problem.Problem;
import com.kafu.kafu.problemprogress.ProblemProgress;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "problem_photo")
@Data
public class ProblemPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(name = "s3_Key", nullable = false, length = 1024)
    private String s3Key;

    @Column(name = "photo_date", nullable = false)
    private LocalDateTime photoDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "progress_id")
    private ProblemProgress progress;
}
