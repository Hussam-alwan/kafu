package com.kafu.kafu.donation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Page<Donation> findByProblem_Id(Long problemId, Pageable pageable);
    List<Donation> findByDonor_IdAndProblem_Id(Long donorId, Long problemId);
}
