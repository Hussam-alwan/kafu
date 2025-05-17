package com.kafu.kafu.payment;

import com.kafu.kafu.donation.DonationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebhookEvent {
    private String gatewayOrderId;
    private DonationStatus success;
}
