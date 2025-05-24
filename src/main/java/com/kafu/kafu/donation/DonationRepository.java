package com.kafu.kafu.donation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonor_IdAndProblem_Id(Long donorId, Long problemId);
    Optional<Donation> findByPaymentTransactionId(String paymentTransactionId);
    Optional<Donation> findByIdempotencyKey(String idempotencyKey);

    Page<Donation> findByProblem_IdAndIsAnonymous(Long id, Boolean isAnonymous, Pageable pageable);
}
