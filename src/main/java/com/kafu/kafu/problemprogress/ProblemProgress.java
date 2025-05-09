package com.kafu.kafu.problemprogress;

import com.kafu.kafu.problem.Problem;
import com.kafu.kafu.problemphoto.ProblemPhoto;
import com.kafu.kafu.solution.Solution;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problem_progress")
@Data
public class ProblemProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Integer percentage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @Column(name = "progress_date", nullable = false)
    private LocalDateTime progressDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;

    @OneToMany(mappedBy = "progress", fetch = FetchType.LAZY)
    private List<ProblemPhoto> photos = new ArrayList<>();
}