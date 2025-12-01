package com.project.schoolmanagementapp.services;

import com.project.schoolmanagementapp.DTO.request.StudentRequest;
import com.project.schoolmanagementapp.DTO.response.StudentResponse;
import com.project.schoolmanagementapp.entities.enums.level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentService {

    StudentResponse create(StudentRequest request);

    StudentResponse update(int id, StudentRequest request);

    StudentResponse getById(int id);

    Page<StudentResponse> getAll(Pageable pageable);

    void delete(int id);

    Page<StudentResponse> search(String username, Pageable pageable);

    Page<StudentResponse> filterByLevel(level level, Pageable pageable);
}
