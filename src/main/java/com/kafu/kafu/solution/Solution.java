package com.kafu.kafu.solution;

import com.kafu.kafu.gov.Gov;
import com.kafu.kafu.problem.Problem;
import com.kafu.kafu.user.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "solution")
@Data
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "estimated_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal estimatedCost;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private SolutionStatus status;

    @Column(name = "accepted_reason", columnDefinition = "TEXT")
    private String acceptedReason;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposed_by", nullable = false)
    private User proposedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accepted_by")
    private Gov acceptedByGovId;
}
