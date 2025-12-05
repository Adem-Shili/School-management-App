package com.project.schoolmanagementapp.services;

import com.project.schoolmanagementapp.DTO.request.StudentRequest;
import com.project.schoolmanagementapp.DTO.response.StudentResponse;
import com.project.schoolmanagementapp.entities.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

// Note: Ensure this file contains all necessary methods defined in StudentController
public interface IStudentService {
    StudentResponse create(StudentRequest request);
    StudentResponse update(int id, StudentRequest request);
    StudentResponse getById(int id);
    void delete(int id);
    InputStream exportStudentsToCsv();

    // The single consolidated method required by StudentController.java
    Page<StudentResponse> getAllStudents(String search, Level level, Pageable pageable);

    // Old methods (ensure these are still present in your concrete implementation if needed)
    // Page<StudentResponse> getAll(Pageable pageable);
    // Page<StudentResponse> search(String name, Pageable pageable);
    // Page<StudentResponse> filterByLevel(Level level, Pageable pageable);
}