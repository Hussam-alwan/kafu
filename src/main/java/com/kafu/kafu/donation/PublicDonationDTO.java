package com.kafu.kafu.donation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicDonationDTO {
    private Long donationId;
    private BigDecimal amount;
    private LocalDateTime donationDate;
    private Long donorId;
    private String firstName;
    private String lastName;
    private String currency;
    private DonationStatus status;
}