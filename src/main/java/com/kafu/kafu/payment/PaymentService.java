package com.kafu.kafu.payment;

import com.kafu.kafu.donation.PaymentSessionRequest;
import com.kafu.kafu.donation.PaymentSessionResponse;

import java.util.Map;

public interface PaymentService {
    PaymentSessionResponse createPaymentSession(PaymentSessionRequest request);
    WebhookEvent getEvent(String payload, Map<String, String> headers);
}
