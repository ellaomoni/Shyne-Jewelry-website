package com.website.Shyne_jewelry.controller;

import com.website.Shyne_jewelry.Service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initialize")
    public ResponseEntity<?> initializePayment(
            @RequestParam String email,
            @RequestParam Double amount,
            @RequestParam String sessionId) {

        String url = paymentService.initializePayment(email, amount, sessionId);
        return ResponseEntity.ok(Map.of("payment_url", url));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestParam String reference) {
        String status = paymentService.verifyPayment(reference);
        return ResponseEntity.ok(Map.of("status", status));
    }


}
