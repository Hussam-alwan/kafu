package com.kafu.kafu.problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, JpaSpecificationExecutor<Problem> {
    
    @Query("SELECT COUNT(p) FROM Problem p")
    Long countAllProblems();
    
    @Query("SELECT COUNT(p) FROM Problem p WHERE p.status = :status")
    Long countProblemByStatus(@Param("status") ProblemStatus status);
}