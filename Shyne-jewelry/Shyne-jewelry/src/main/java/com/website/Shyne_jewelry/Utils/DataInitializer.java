package com.website.Shyne_jewelry.Utils;
import com.website.Shyne_jewelry.Repos.RoleRepository;
import com.website.Shyne_jewelry.Repos.UserRepository;
import com.website.Shyne_jewelry.entities.Role;
import com.website.Shyne_jewelry.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        initializeRoles();

        // Optionally create a default admin user
        createDefaultAdminIfNotExists();
    }

    private void initializeRoles() {
        if (!roleRepository.existsByName("ROLE_USER")) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println("✓ Created ROLE_USER");
        }

        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            System.out.println("✓ Created ROLE_ADMIN");
        }
    }

    private void createDefaultAdminIfNotExists() {
        String defaultAdminEmail = "shynejewelry.ng@gmail.com";

        if (!userRepository.existsByEmail(defaultAdminEmail)) {
            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail(defaultAdminEmail);
            admin.setPassword(passwordEncoder.encode("Om0ni$3E")); // Change this!
            admin.setCreatedAt(LocalDateTime.now());

            // Assign admin role
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            admin.getRoles().add(adminRole);

            userRepository.save(admin);
            System.out.println("✓ Created default admin user");
            System.out.println("  Email: " + defaultAdminEmail);
            System.out.println("  Password: Admin@123 (CHANGE THIS IMMEDIATELY!)");
        }
    }
}