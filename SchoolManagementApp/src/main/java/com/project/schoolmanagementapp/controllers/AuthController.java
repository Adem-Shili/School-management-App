package com.project.schoolmanagementapp.controllers;

import com.project.schoolmanagementapp.DTO.request.AdminRequest;
import com.project.schoolmanagementapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // Removed AdminRepository and PasswordEncoder as AuthenticationManager handles them
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AdminRequest request) {

        // 1. Authenticate the user credentials against the configured AuthenticationProvider (AdminService)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
        );

        // 2. If authentication is successful, generate the token
        String username = authentication.getName();
        String token = jwtService.generateToken(username);

        return Map.of("token", token);
    }
}