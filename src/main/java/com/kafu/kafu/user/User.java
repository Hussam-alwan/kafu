package com.kafu.kafu.user;

import com.kafu.kafu.address.Address;
import com.kafu.kafu.gov.Gov;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String keycloakId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    private LocalDate dateOfBirth;

    @Column(length = 100)
    private String collegeDegree;

    @Column(length = 50)
    private String job;

    private String cvUrl;
    private String photoUrl;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gov_id")
    private Gov gov;
}
