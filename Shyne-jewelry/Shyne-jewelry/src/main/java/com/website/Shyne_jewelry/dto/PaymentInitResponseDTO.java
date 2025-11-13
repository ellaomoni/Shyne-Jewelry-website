package com.website.Shyne_jewelry.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInitResponseDTO {

    private String status;
    private String authorizationUrl;
    private String reference;
}
