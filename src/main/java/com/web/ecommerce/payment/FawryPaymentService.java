package com.web.ecommerce.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class FawryPaymentService {

    private final RestTemplate restTemplate;
    private final String fawryApiUrl = "https://fawry.com/api/payments";

    @Autowired
    public FawryPaymentService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public PaymentResponse initiatePayment(PaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);

        try {
            ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(fawryApiUrl + "/charge", entity,
                    PaymentResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return new PaymentResponse("FAILED", null, e.getResponseBodyAsString(), paymentRequest.getPaymentMethod(),
                    0, paymentRequest.getCurrency());
        }
    }
}
