package com.kafu.kafu.problem;

import com.kafu.kafu.problem.dto.ProblemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, JpaSpecificationExecutor<Problem> {
    @Query("SELECT new com.kafu.kafu.problem.dto.ProblemDTO(p.id, p.title, p.description, p.isReal, p.forContribution, p.forDonation, p.submissionDate, p.status, p.rejectionReason, p.address.id, p.submittedByUser.id, p.approvedByUser.id, p.category.id) FROM Problem p")
    Page<ProblemDTO> findAllDtos(Pageable pageable);

    @Query("SELECT new com.kafu.kafu.problem.dto.ProblemDTO(p.id, p.title, p.description, p.isReal, p.forContribution, p.forDonation, p.submissionDate, p.status, p.rejectionReason, p.address.id, p.submittedByUser.id, p.approvedByUser.id, p.category.id) FROM Problem p WHERE p.id = :id")
    Optional<ProblemDTO> findDtoById(@Param("id") Long id);

    @Query("SELECT new com.kafu.kafu.problem.dto.ProblemDTO(p.id, p.title, p.description, p.isReal, p.forContribution, p.forDonation, p.submissionDate, p.status, p.rejectionReason, p.address.id, p.submittedByUser.id, p.approvedByUser.id, p.category.id) FROM Problem p WHERE p.submittedByUser.id = :userId")
    List<ProblemDTO> findDtosBySubmittedByUserId(@Param("userId") Long userId);

    List<Problem> findByCategoryId(Long categoryId);
    List<Problem> findByApprovedByUserId(Long userId);
    List<Problem> findBySubmittedByUserId(Long userId);
}
