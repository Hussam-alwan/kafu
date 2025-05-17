package com.kafu.kafu.donation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentSessionResponse {
    String sessionId;
    String sessionUrl;
}
