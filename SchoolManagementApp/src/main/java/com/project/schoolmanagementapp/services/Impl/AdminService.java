package com.project.schoolmanagementapp.services.Impl;

import com.project.schoolmanagementapp.DTO.request.AdminRequest;
import com.project.schoolmanagementapp.DTO.response.AdminResponse;
import com.project.schoolmanagementapp.entities.Admin;
import com.project.schoolmanagementapp.repositories.AdminRepository;
import com.project.schoolmanagementapp.services.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService, UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        return User.withUsername(admin.getName())
                .password(admin.getPassword())
                .authorities("ADMIN")
                .build();
    }

    @Override
    public AdminResponse register(AdminRequest request) {
        if (adminRepository.existsByName(request.getName())) {
            throw new RuntimeException("Name already exists");
        }

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        Admin saved = adminRepository.save(admin);

        return new AdminResponse(saved.getId(), saved.getName());
    }
}