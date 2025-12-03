package com.project.schoolmanagementapp.repositories;

import com.project.schoolmanagementapp.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByName(String name);
    boolean existsByName(String name);
}