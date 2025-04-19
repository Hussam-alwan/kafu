package com.kafu.kafu.donation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DonationDTO {
    private Long id;

    @NotNull(message = "Problem ID is required")
    private Long problemId;

    @NotNull(message = "Donor ID is required")
    private Long donorId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private BigDecimal fee;

    private BigDecimal netAmount;

    @NotNull(message = "Currency is required")
    private String currency = "USD";

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private String paymentTransactionId;

    private DonationStatus status;

    private Boolean isAnonymous = false;

    private LocalDateTime donationDate;
}
