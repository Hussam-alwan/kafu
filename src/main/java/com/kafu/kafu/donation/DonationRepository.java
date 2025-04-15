package com.kafu.kafu.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByProblemId(Long problemId);
    List<Donation> findByDonorId(Long donorId);
}
