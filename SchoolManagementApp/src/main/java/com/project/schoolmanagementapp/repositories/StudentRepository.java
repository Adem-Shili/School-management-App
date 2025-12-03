package com.project.schoolmanagementapp.repositories;

import com.project.schoolmanagementapp.entities.Student;
import com.project.schoolmanagementapp.entities.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    boolean existsByName(String name);

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Student> findByStudentLevel(Level level, Pageable pageable);
}
