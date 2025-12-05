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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse create(StudentRequest request) {
        if (studentRepository.existsByName(request.getName())) {
            throw new RuntimeException("Student already exists");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setStudentLevel(request.getLevel());

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse update(int id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(request.getName());
        student.setStudentLevel(request.getLevel());

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse getById(int id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return mapToResponse(student);
    }

    @Override
    public void delete(int id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }
        studentRepository.deleteById(id);
    }

    // ✅ REQUIRED BY CONTROLLER
    @Override
    public Page<StudentResponse> getAllStudents(String search, Level level, Pageable pageable) {

        if (search != null && level != null) {
            return studentRepository
                    .findByNameContainingIgnoreCase(search, pageable)
                    .map(this::mapToResponse);
        }

        if (search != null) {
            return studentRepository
                    .findByNameContainingIgnoreCase(search, pageable)
                    .map(this::mapToResponse);
        }

        if (level != null) {
            return studentRepository
                    .findByStudentLevel(level, pageable)
                    .map(this::mapToResponse);
        }

        return studentRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // ✅ REQUIRED BY EXPORT ENDPOINT
    @Override
    public InputStream exportStudentsToCsv() {

        StringBuilder csv = new StringBuilder("ID,Name,Level\n");

        for (Student s : studentRepository.findAll()) {
            csv.append(s.getId())
                    .append(",")
                    .append(s.getName())
                    .append(",")
                    .append(s.getStudentLevel())
                    .append("\n");
        }

        return new ByteArrayInputStream(csv.toString().getBytes(StandardCharsets.UTF_8));
    }

    private StudentResponse mapToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setStudentlevel(student.getStudentLevel());
        return response;
    }

}
