package com.project.schoolmanagementapp.services.Impl;

import com.project.schoolmanagementapp.DTO.request.StudentRequest;
import com.project.schoolmanagementapp.DTO.response.StudentResponse;
import com.project.schoolmanagementapp.entities.Student;
import com.project.schoolmanagementapp.entities.enums.Level;
import com.project.schoolmanagementapp.repositories.StudentRepository;
import com.project.schoolmanagementapp.services.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse create(StudentRequest request) {

        if (studentRepository.existsByName(request.getName())) {
            throw new RuntimeException("Username already exists");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setStudentLevel(request.getLevel());

        Student saved = studentRepository.save(student);

        return mapToResponse(saved);
    }

    @Override
    public StudentResponse update(int id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(request.getName());
        student.setStudentLevel(request.getLevel());

        Student updated = studentRepository.save(student);

        return mapToResponse(updated);
    }

    @Override
    public StudentResponse getById(int id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return mapToResponse(student);
    }

    @Override
    public Page<StudentResponse> getAll(Pageable pageable) {

        return studentRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public void delete(int id) {

        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }

        studentRepository.deleteById(id);
    }

    @Override
    public Page<StudentResponse> search(String name, Pageable pageable) {

        return studentRepository
                .findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<StudentResponse> filterByLevel(Level level, Pageable pageable) {

        return studentRepository
                .findByStudentLevel(level, pageable)
                .map(this::mapToResponse);
    }

    private StudentResponse mapToResponse(Student student) {

        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setStudentlevel(student.getStudentLevel());
        return response;
    }
}
