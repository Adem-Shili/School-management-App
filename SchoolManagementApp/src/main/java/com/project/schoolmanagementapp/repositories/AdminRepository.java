package com.project.schoolmanagementapp.repositories;

import com.project.schoolmanagementapp.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByName(String name);

}
