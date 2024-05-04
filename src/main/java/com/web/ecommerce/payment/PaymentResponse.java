package com.web.ecommerce.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String status;
    private String transactionId;
    private String message;
    private String paymentMethod;
    private double amountPaid;
    private String currency;
}
