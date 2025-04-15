package com.kafu.kafu.gov;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GovRepository extends JpaRepository<Gov, Long> {
    Optional<Gov> findByEmail(String email);
    boolean existsByEmail(String email);
}
