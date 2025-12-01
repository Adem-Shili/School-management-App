package com.project.schoolmanagementapp.controllers;

import com.project.schoolmanagementapp.DTO.request.AdminRequest;
import com.project.schoolmanagementapp.security.JwtService;
import com.project.schoolmanagementapp.repositories.AdminRepository;
import com.project.schoolmanagementapp.entities.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AdminRequest request) {

        Admin admin = adminRepository.findAll()
                .stream()
                .filter(a -> a.getName().equals(request.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(admin.getName());

        return Map.of("token", token);
    }
}
