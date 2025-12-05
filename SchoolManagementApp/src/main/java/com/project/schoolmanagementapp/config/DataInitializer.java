package com.project.schoolmanagementapp.config;

import com.project.schoolmanagementapp.entities.Admin;
import com.project.schoolmanagementapp.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Define the default user details
            final String defaultUsername = "admin";
            final String defaultPassword = "admin123";

            // Check if the default admin already exists to prevent duplicate entries
            if (adminRepository.findByName(defaultUsername).isEmpty()) {

                // You must ensure that the Admin entity has a constructor or setters
                // for name and password, or use a builder pattern if defined.
                Admin defaultAdmin = new Admin();
                defaultAdmin.setName(defaultUsername);

                // This is the crucial step: Encode the password before saving
                defaultAdmin.setPassword(passwordEncoder.encode(defaultPassword));

                adminRepository.save(defaultAdmin);
                System.out.println("Default admin user '" + defaultUsername + "' created with password '" + defaultPassword + "'");
            }
        };
    }
}