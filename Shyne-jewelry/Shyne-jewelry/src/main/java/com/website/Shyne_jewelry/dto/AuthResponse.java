package com.website.Shyne_jewelry.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthResponse {
        private String accessToken;
        private String tokenType = "Bearer";
        private Long userId;
        private String email;
        private String fullName;
        private boolean emailVerified;
        private List<String> roles;
        private String verificationToken; // For registration


    //Constructor for registration
    public AuthResponse(String accessToken, Long userId, String email, String fullName, List<String> roles, String verificationToken, boolean emailVerified) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.emailVerified = emailVerified;
        this.roles = roles;
        this.verificationToken = verificationToken;

    }

    // Constructor for login (no verification token)
    public AuthResponse(String accessToken, Long userId, String fullName, String email,
                        boolean emailVerified, List<String> roles) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.emailVerified = emailVerified;
        this.roles = roles;
    }

    // Backward compatibility constructor
    public AuthResponse(String accessToken, Long userId, String fullName, String email) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.emailVerified = false;
    }

}
