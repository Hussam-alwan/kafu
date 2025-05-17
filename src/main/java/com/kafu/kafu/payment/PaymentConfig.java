package com.kafu.kafu.payment;

import com.kafu.kafu.payment.stripe.StripeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.EnumMap;
import java.util.Map;

@Configuration
public class PaymentConfig {

    @Bean
    public Map<PaymentMethod, PaymentService> paymentServices(
            StripeService stripeService) {

        Map<PaymentMethod, PaymentService> map = new EnumMap<>(PaymentMethod.class);
        map.put(PaymentMethod.STRIPE, stripeService);

        return map;
    }
}
