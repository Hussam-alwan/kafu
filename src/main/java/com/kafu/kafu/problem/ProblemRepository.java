package com.kafu.kafu.problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, JpaSpecificationExecutor<Problem> {
    List<Problem> findByCategoryId(Long categoryId);
    List<Problem> findByApprovedByUserId(Long userId);
    List<Problem> findBySubmittedByUserId(Long userId);
}
