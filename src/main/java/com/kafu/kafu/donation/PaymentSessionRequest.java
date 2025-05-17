package com.kafu.kafu.donation;

import com.kafu.kafu.payment.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentSessionRequest {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotNull
    private Boolean isAnonymous = false;

    @NotBlank(message = "successUrl is required")
    private String successUrl;

    @NotBlank(message = "cancelUrl is required")
    private String cancelUrl;

    @NotBlank(message = "idempotencyKey is required")
    private String idempotencyKey;
}