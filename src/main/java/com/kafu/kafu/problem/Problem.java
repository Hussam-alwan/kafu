package com.kafu.kafu.problem;

import com.kafu.kafu.address.Address;
import com.kafu.kafu.problemcategory.ProblemCategory;
import com.kafu.kafu.user.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "problem")
@Data
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_real", nullable = false)
    private Boolean isReal = false;

    @Column(name = "for_contribution", nullable = false)
    private Boolean forContribution = false;

    @Column(name = "for_donation", nullable = false)
    private Boolean forDonation = false;

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemStatus status;

    @Column(name = "rejection_reason", nullable = false)
    private String rejectionReason;

    //relationships

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by_user_id", nullable = false)
    private User submittedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_user_id")
    private User approvedByUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ProblemCategory category;
}
