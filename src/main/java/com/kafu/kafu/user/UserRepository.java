package com.kafu.kafu.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByKeycloakId(String keycloakId);
    boolean existsByKeycloakId(String keycloakId);

    // User statistics methods
    @Query("""
    SELECT MONTH(p.submissionDate) as month, COUNT(p) as count
    FROM Problem p
    WHERE p.submittedByUser.id = :userId
    AND YEAR(p.submissionDate) = :year
    GROUP BY MONTH(p.submissionDate)
""")
    List<Object[]> countProblemsByMonth(@Param("userId") Long userId, @Param("year") Integer year);

    @Query("""
    SELECT MONTH(s.creationDate) as month, COUNT(s) as count
    FROM Solution s
    WHERE s.proposedByUserId.id = :userId
    AND YEAR(s.creationDate) = :year
    GROUP BY MONTH(s.creationDate)
""")
    List<Object[]> countSolutionsByMonth(@Param("userId") Long userId, @Param("year") Integer year);

    @Query("""
    SELECT MONTH(d.donationDate) as month, COUNT(d) as count
    FROM Donation d
    WHERE d.donor.id = :userId
    AND YEAR(d.donationDate) = :year
    GROUP BY MONTH(d.donationDate)
""")
    List<Object[]> countDonationsByMonth(@Param("userId") Long userId, @Param("year") Integer year);

    @Query("SELECT COUNT(p) FROM Problem p WHERE p.submittedByUser.id = :userId AND YEAR(p.submissionDate) = :year")
    Long countProblemsByYear(@Param("userId") Long userId, @Param("year") Integer year);

    @Query("SELECT COUNT(s) FROM Solution s WHERE s.proposedByUserId.id = :userId AND YEAR(s.creationDate) = :year")
    Long countSolutionsByYear(@Param("userId") Long userId, @Param("year") Integer year);

    @Query("SELECT COUNT(d) FROM Donation d WHERE d.donor.id = :userId AND YEAR(d.donationDate) = :year")
    Long countDonationsByYear(@Param("userId") Long userId, @Param("year") Integer year);


    // Admin statistics methods


    @Query("SELECT COUNT(u) FROM User u")
    Long countAllUsers();

}
