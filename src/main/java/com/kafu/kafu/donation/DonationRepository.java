package com.kafu.kafu.donation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Page<Donation> findByProblem_Id(Long problemId, Pageable pageable);
    List<Donation> findByDonor_IdAndProblem_Id(Long donorId, Long problemId);
    List<Donation> findByDonor_Id(Long donorId);
    Optional<Donation> findByPaymentTransactionId(String paymentTransactionId);
    Optional<Donation> findByIdempotencyKey(String idempotencyKey);

    @Query("""
        SELECT new com.kafu.kafu.donation.PublicDonationDTO(
            d.id, d.amount, d.donationDate, donor.id, donor.firstName, donor.lastName, d.currency, d.status
        )
        FROM Donation d
        JOIN d.donor donor
        WHERE d.problem.id = :problemId AND d.isAnonymous = false AND d.status = 'SUCCESS'
    """)
    Page<PublicDonationDTO> findPublicDonationsWithDonorAndStatusSuccess(@Param("problemId") Long problemId, Pageable pageable);
}
