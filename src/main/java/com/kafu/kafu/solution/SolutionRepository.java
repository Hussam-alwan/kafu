package com.kafu.kafu.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findByProblemId(Long problemId);
    List<Solution> findByProposedById(Long userId);
    List<Solution> findByAcceptedById(Long govId);
}
