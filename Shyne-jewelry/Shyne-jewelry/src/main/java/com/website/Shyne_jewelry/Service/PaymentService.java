package com.website.Shyne_jewelry.Service;

public interface PaymentService {
    String initializePayment(String email, Double amount, String sessionId);
    String verifyPayment(String reference);
}
