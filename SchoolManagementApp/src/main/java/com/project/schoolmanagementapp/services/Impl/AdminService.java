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
public class AdminService implements IAdminService, UserDetailsService {  // ← THIS IS THE FIX

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByName(username)  // ← fixed: was email, now username + correct method
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with name: " + username));
    }

    @Override
    public AdminResponse register(AdminRequest request) {
        return null;
    }

}