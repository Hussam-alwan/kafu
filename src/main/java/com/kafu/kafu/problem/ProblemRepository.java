package com.kafu.kafu.problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByCategoryId(Long categoryId);
    List<Problem> findByApprovedByGovId(Long govId);
}
