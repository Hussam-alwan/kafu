package com.kafu.kafu.gov;

import com.kafu.kafu.address.Address;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gov")
@Data
public class  Gov {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "logo_url")
    private String logoUrl;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_gov_id")
    private Gov parentGov;
}
