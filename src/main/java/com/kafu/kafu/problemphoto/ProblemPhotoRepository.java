package com.kafu.kafu.problemphoto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProblemPhotoRepository extends JpaRepository<ProblemPhoto, Long> {
    List<ProblemPhoto> findByProblemId(Long problemId);
}
