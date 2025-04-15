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

    @NotNull(message = "Fee is required")
    @Positive(message = "Fee must be positive")
    private BigDecimal fee;

    @NotNull(message = "Net amount is required")
    @Positive(message = "Net amount must be positive")
    private BigDecimal netAmount;

    @NotNull(message = "Currency is required")
    private String currency = "USD";

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    private String paymentTransactionId;

    @NotNull(message = "Status is required")
    private DonationStatus status;

    private Boolean isAnonymous = false;

    private LocalDateTime donationDate;
}
