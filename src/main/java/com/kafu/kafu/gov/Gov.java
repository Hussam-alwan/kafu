package com.kafu.kafu.gov;

import com.kafu.kafu.address.Address;
import com.kafu.kafu.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gov")
@Data
public class Gov {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
