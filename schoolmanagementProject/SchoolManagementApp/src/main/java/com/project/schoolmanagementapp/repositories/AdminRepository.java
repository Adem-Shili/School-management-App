package com.project.schoolmanagementapp.repositories;

import com.project.schoolmanagementapp.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    boolean existsByName(String name);
}
