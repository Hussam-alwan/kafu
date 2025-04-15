package com.kafu.kafu.problemcategory;

import com.kafu.kafu.gov.Gov;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "problem_category")
@Data
public class ProblemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gov_id", nullable = false)
    private Gov gov;
}
