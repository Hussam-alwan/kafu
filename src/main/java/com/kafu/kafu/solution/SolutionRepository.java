package com.kafu.kafu.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long>, JpaSpecificationExecutor<Solution> {
    List<Solution> findByProblem_Id(Long id);
    List<Solution> findByProposedByUserId_Id(Long proposedByUserId);
}
