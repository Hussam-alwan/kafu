package com.kafu.kafu.problem;

import com.kafu.kafu.address.Address;
import com.kafu.kafu.gov.Gov;
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

    @Column(nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User submittedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Gov approvedBy;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ProblemStatus status;
}
