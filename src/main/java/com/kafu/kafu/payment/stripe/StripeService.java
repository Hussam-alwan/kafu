package com.kafu.kafu.payment.stripe;

import com.kafu.kafu.donation.*;
import com.kafu.kafu.payment.*;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeService implements PaymentService {

    @Value("${payment.stripe.apiKey}")
    private String stripeApiKey;


    @Value("${payment.stripe.webhookSecret}")
    private String stripeEndpointSecret;

    @Override
    @Transactional
    public PaymentSessionResponse createPaymentSession(PaymentSessionRequest request) {

        Stripe.apiKey = stripeApiKey;

        // Create Stripe Checkout Session
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(request.getSuccessUrl())
                .setCancelUrl(request.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(request.getCurrency())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Product Name").build())
                                .setUnitAmount(request.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                                .build())

                        .build())
                .putMetadata("orderId", request.getIdempotencyKey()) // For webhook lookup
                .build();

        // Use idempotency key
        RequestOptions options = RequestOptions.builder()
                .setIdempotencyKey(request.getIdempotencyKey())
                .build();

        Session session = null;
        try {
            session = Session.create(params, options);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        return new PaymentSessionResponse(session.getId(),session.getUrl());
    }


    @Override
    @Transactional
    public WebhookEvent getEvent(String payload, Map<String, String> headers) {

        Event event = null;
        try {
            event = Webhook.constructEvent(payload, headers.get("Stripe-Signature"), stripeEndpointSecret);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException("Invalid Stripe signature");
        }

        // Extract relevant data based on event type
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = null;
            try {
                session = (Session) event.getDataObjectDeserializer().getObject().orElseThrow(
                        () -> new IllegalStateException("Failed to get Stripe Session")
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize Stripe Session");
            }
            return new WebhookEvent(session.getId(),DonationStatus.SUCCESS);
        }
        else if ("charge.failed".equals(event.getType())) {
            Charge charge = null;
            try {
                charge = (Charge) event.getDataObjectDeserializer().getObject().orElseThrow(
                        () -> new IllegalStateException("Failed to get Stripe Session")
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize Stripe Session");
            }
            return new WebhookEvent(charge.getPaymentIntent(),DonationStatus.FAILED);
        }
        else {
            log.warn("Unhandled Stripe event type: {}", event.getType());
            return new WebhookEvent("",DonationStatus.FAILED);
        }
    }
}
