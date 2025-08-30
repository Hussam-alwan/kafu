package com.kafu.kafu.gov;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GovRepository extends JpaRepository<Gov, Long> {
    Optional<Gov> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("""
    SELECT MONTH(p.submissionDate) as month, COUNT(p) as count
    FROM Problem p
    JOIN p.category pc
    WHERE pc.gov.id = :govId
    AND YEAR(p.submissionDate) = :year
    GROUP BY MONTH(p.submissionDate)
""")
    List<Object[]> countReceivedProblemsByMonth(@Param("govId") Long govId, @Param("year") Integer year);

    @Query("""
    SELECT MONTH(p.submissionDate) as month, COUNT(p) as count
    FROM Problem p
    JOIN p.category pc
    WHERE pc.gov.id = :govId
    AND p.status = 'RESOLVED'
    AND YEAR(p.submissionDate) = :year
    GROUP BY MONTH(p.submissionDate)
""")
    List<Object[]> countCompletedProblemsByMonth(@Param("govId") Long govId, @Param("year") Integer year);

    @Query("""
    SELECT MONTH(p.submissionDate) as month, COUNT(p) as count
    FROM Problem p
    JOIN p.category pc
    WHERE pc.gov.id = :govId
    AND (p.forContribution = true OR p.forDonation = true)
    AND p.status IN ('APPROVED', 'PENDING_CONRIBUTIONS', 'PENDING_FUNDING', 'WORK_IN_PROGRESS')
    AND YEAR(p.submissionDate) = :year
    GROUP BY MONTH(p.submissionDate)
""")
    List<Object[]> countAuctionsByMonth(@Param("govId") Long govId, @Param("year") Integer year);

    // Yearly totals
    @Query("""
    SELECT COUNT(p)
    FROM Problem p
    JOIN p.category pc
    WHERE pc.gov.id = :govId
    AND YEAR(p.submissionDate) = :year
""")
    Long countReceivedProblemsByYear(@Param("govId") Long govId, @Param("year") Integer year);

    @Query("""
    SELECT COUNT(p)
    FROM Problem p
    JOIN p.category pc
    WHERE pc.gov.id = :govId
    AND p.status = 'RESOLVED'
    AND YEAR(p.submissionDate) = :year
""")
    Long countCompletedProblemsByYear(@Param("govId") Long govId, @Param("year") Integer year);

    @Query("""
    SELECT COUNT(p)
    FROM Problem p
    JOIN p.category pc
    WHERE pc.gov.id = :govId
    AND (p.forContribution = true OR p.forDonation = true)
    AND p.status IN ('APPROVED', 'PENDING_CONRIBUTIONS', 'PENDING_FUNDING', 'WORK_IN_PROGRESS')
    AND YEAR(p.submissionDate) = :year
""")
    Long countAuctionsByYear(@Param("govId") Long govId, @Param("year") Integer year);


    @Query("SELECT COUNT(g) FROM Gov g")
    Long countAllGov();
}
