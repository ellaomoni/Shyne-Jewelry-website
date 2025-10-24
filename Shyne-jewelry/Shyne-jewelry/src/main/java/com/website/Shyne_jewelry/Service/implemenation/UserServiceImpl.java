package com.website.Shyne_jewelry.Service.implemenation;

import com.website.Shyne_jewelry.Repos.RoleRepository;
import com.website.Shyne_jewelry.Repos.UserRepository;
import com.website.Shyne_jewelry.Service.UserService;
import com.website.Shyne_jewelry.dto.AuthResponse;
import com.website.Shyne_jewelry.dto.LoginDTO;
import com.website.Shyne_jewelry.dto.RegisterDTO;
import com.website.Shyne_jewelry.entities.Role;
import com.website.Shyne_jewelry.entities.User;
import com.website.Shyne_jewelry.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @Override
    @Transactional
    public AuthResponse registerUser(RegisterDTO dto){
    return  registerUserWithRole(dto, "ROLE_USER");

    }
    @Override
    @Transactional
    public AuthResponse registerAdmin(RegisterDTO dto) {
        return  registerUserWithRole( dto, "ROLE_ADMIN");


    }

    public AuthResponse registerUserWithRole(RegisterDTO dto, String roleName){
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setEmailVerified(false);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));


        // Generate verification token
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(LocalDateTime.now().plusHours(24)); // 24 hours validity

        // Assign role
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.getRoles().add(role);

        // Save user
        User savedUser = userRepository.save(user);

        // Generate JWT token (user can still browse but certain actions require verification)
        String jwt = tokenProvider.generateToken(savedUser.getId());

        // Get role names
        List<String> roles = savedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return new AuthResponse(
                jwt,
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.isEmailVerified(),
                roles
               //verificationToken // Include for testing purposes
        );
    }

    public AuthResponse loginAdmin(LoginDTO dto){
        // Find user by email
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        // Check password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        //Check if the user has ROLE_ADMIN
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new BadCredentialsException("Access denied: Only admins can log in currently.");
        }

        // Generate JWT token
        String jwt = tokenProvider.generateToken(user.getId());

        // Get role names
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return new AuthResponse(
                jwt,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isEmailVerified(),
                roles
        );
    }
}





