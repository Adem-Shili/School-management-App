package com.project.schoolmanagementapp.services.Impl;

import com.project.schoolmanagementapp.DTO.request.AdminRequest;
import com.project.schoolmanagementapp.DTO.response.AdminResponse;
import com.project.schoolmanagementapp.entities.Admin;
import com.project.schoolmanagementapp.repositories.AdminRepository;
import com.project.schoolmanagementapp.services.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse register(AdminRequest request) {

        if (adminRepository.existsByName(request.getName())) {
            throw new RuntimeException("Username already exists");
        }

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        Admin saved = adminRepository.save(admin);

        AdminResponse response = new AdminResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());

        return response;
    }
}
