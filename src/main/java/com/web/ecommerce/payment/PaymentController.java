package com.web.ecommerce.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private FawryPaymentService fawryPaymentService;

    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> createCharge(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentResponse paymentResponse = fawryPaymentService.initiatePayment(paymentRequest);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PaymentResponse("FAILED", null,
                    "An error occurred while processing your payment", paymentRequest.getPaymentMethod(), 0,
                    paymentRequest.getCurrency()));
        }
    }
}
