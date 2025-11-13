package com.website.Shyne_jewelry.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseDTO {

    private String authorizationUrl;
    private String reference;
}
