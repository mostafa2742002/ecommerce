package com.web.ecommerce.payment;

import lombok.Data;

@Data
public class PaymentRequest {
    private String userId;
    private double amount;
    private String currency;
    private String paymentMethod; // Typically "Fawry"
    private String merchantCode;
    private String merchantRefNum;
    private String customerProfileId;
    private String paymentMethodDetails; // This might include card number, expiry, etc., depending on integration
}
