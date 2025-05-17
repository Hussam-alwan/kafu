package com.kafu.kafu.payment;

import com.kafu.kafu.donation.PaymentMediator;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentMediator paymentMediator;

    @Hidden
    @PostMapping("/stripe")
    public ResponseEntity<String> stripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Stripe-Signature", sigHeader);

        paymentMediator.handleWebhookEvent(payload, headers,PaymentMethod.STRIPE);

        return ResponseEntity.ok().build();
    }
}