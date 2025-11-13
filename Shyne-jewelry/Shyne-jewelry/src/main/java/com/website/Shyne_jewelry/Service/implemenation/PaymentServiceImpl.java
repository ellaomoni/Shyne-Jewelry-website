package com.website.Shyne_jewelry.Service.implemenation;

import com.website.Shyne_jewelry.Repos.TransactionRepository;
import com.website.Shyne_jewelry.Service.PaymentService;
import com.website.Shyne_jewelry.entities.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${paystack.secret.key}")
    private String paystackSecretKey;

    private final RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;

    public PaymentServiceImpl(RestTemplate restTemplate, TransactionRepository transactionRepository) {
        this.restTemplate = restTemplate;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public String initializePayment(String email, Double amount, String sessionId) {
        String url = "https://api.paystack.co/transaction/initialize";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("amount", (int)(amount * 100)); // Paystack uses kobo
        body.put("callback_url", "http://localhost:8080/api/payments/verify");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map data = (Map) response.getBody().get("data");
            String reference = (String) data.get("reference");
            String authorizationUrl = (String) data.get("authorization_url");

            Transaction tx = new Transaction();
            tx.setReference(reference);
            tx.setEmail(email);
            tx.setAmount(amount);
            tx.setSessionId(sessionId);
            tx.setStatus("PENDING");
            transactionRepository.save(tx);

            return authorizationUrl;
        }
        throw new RuntimeException("Payment initialization failed");
    }

    @Override
    public String verifyPayment(String reference) {
        String url = "https://api.paystack.co/transaction/verify/" + reference;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map data = (Map) response.getBody().get("data");
            String status = (String) data.get("status");

            Transaction tx = transactionRepository.findByReference(reference)
                    .orElseThrow(() -> new RuntimeException("Transaction not found"));
            tx.setStatus(status.toUpperCase());
            transactionRepository.save(tx);

            return status;
        }

        throw new RuntimeException("Payment verification failed");
    }


}
