package com.website.Shyne_jewelry.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name="transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;// Paystack reference

    private String email;

    private Double amount;

    private String status;
    // PENDING, SUCCESS, FAILED
    private String sessionId;   // user or guest

    private LocalDateTime createdAt = LocalDateTime.now();
}
