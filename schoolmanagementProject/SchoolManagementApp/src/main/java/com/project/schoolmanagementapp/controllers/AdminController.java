package com.project.schoolmanagementapp.controllers;

import com.project.schoolmanagementapp.DTO.request.AdminRequest;
import com.project.schoolmanagementapp.DTO.response.AdminResponse;
import com.project.schoolmanagementapp.services.IAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<AdminResponse> register(@Valid @RequestBody AdminRequest request) {
        AdminResponse response = adminService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
