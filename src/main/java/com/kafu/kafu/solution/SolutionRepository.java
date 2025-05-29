package com.kafu.kafu.solution;

import com.kafu.kafu.solution.dto.SolutionWithSubmitterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long>, JpaSpecificationExecutor<Solution> {
    List<Solution> findByProblem_Id(Long id);
    List<Solution> findByProposedByUserId_Id(Long proposedByUserId);

    @Query("""
        SELECT new com.kafu.kafu.solution.dto.SolutionWithSubmitterDTO(
            s.id, s.description, s.estimatedCost, s.creationDate, s.problem.id,
            u.id, u.firstName, u.lastName, s.status
        )
        FROM Solution s
        JOIN s.proposedByUserId u
        WHERE s.problem.id = :problemId
    """)
    List<SolutionWithSubmitterDTO> findSolutionWithSubmitterByProblemId(Long problemId);
}
