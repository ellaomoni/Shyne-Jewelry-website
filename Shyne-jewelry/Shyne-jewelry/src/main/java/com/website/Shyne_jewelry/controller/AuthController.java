package com.website.Shyne_jewelry.controller;

import com.website.Shyne_jewelry.Service.UserService;
import com.website.Shyne_jewelry.dto.ApiResponse;
import com.website.Shyne_jewelry.dto.AuthResponse;
import com.website.Shyne_jewelry.dto.LoginDTO;
import com.website.Shyne_jewelry.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register ( @Valid @RequestBody RegisterDTO registerDTO) {
        try{
            AuthResponse authResponse = userService.registerUser(registerDTO);
            return  ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "Registration successfully.")
            );
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * Register a new ADMIN user
     * Protected endpoint - only existing admins can create new admins
     * For first admin, you'll need to manually insert into database
     */
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Only admins can create other admins
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            AuthResponse authResponse = userService.registerAdmin(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Admin registered successfully.", authResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin (@Valid @RequestBody LoginDTO loginDTO){
        try{
            AuthResponse authResponse = userService.loginAdmin(loginDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Login successfully.", authResponse));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
