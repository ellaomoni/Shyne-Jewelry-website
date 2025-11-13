package com.website.Shyne_jewelry.Service;

import com.website.Shyne_jewelry.dto.PaymentInitResponseDTO;
import io.jsonwebtoken.io.IOException;

public interface PaymentService {
    PaymentInitResponseDTO initializePayment(String email, Double amount, String sessionId) throws IOException;
    String verifyPayment(String reference);
}
