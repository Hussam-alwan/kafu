package com.kafu.kafu.problemprogress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemProgressRepository extends JpaRepository<ProblemProgress, Long> {
    List<ProblemProgress> findByProblemId(Long problemId);
    Optional<ProblemProgress> findByIdAndProblemId(Long id, Long problemId);
}
