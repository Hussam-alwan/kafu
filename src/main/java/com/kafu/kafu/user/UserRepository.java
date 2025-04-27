package com.kafu.kafu.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByKeycloakId(String keycloakId);
    boolean existsByKeycloakId(String keycloakId);

    @Query("SELECT new com.kafu.kafu.user.UserDTO(u.id, u.keycloakId, u.firstName, u.lastName, u.email, u.phone, u.dateOfBirth, u.collegeDegree, u.job, u.cvUrl, u.photoUrl, u.description, u.address.id, u.gov.id) FROM User u WHERE u.id = :id")
    Optional<UserDTO> findDtoById(@Param("id") Long id);

    @Query("SELECT new com.kafu.kafu.user.UserDTO(u.id, u.keycloakId, u.firstName, u.lastName, u.email, u.phone, u.dateOfBirth, u.collegeDegree, u.job, u.cvUrl, u.photoUrl, u.description, u.address.id, u.gov.id) FROM User u")
    Page<UserDTO> findAllDtos(Pageable pageable);


}
