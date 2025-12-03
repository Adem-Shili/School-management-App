package com.project.schoolmanagementapp.controllers;

import com.project.schoolmanagementapp.DTO.request.StudentRequest;
import com.project.schoolmanagementapp.DTO.response.StudentResponse;
import com.project.schoolmanagementapp.entities.enums.Level;
import com.project.schoolmanagementapp.services.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final IStudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        return new ResponseEntity<>(
                studentService.create(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(
            @PathVariable int id,
            @Valid @RequestBody StudentRequest request
    ) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(studentService.getAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponse>> search(
            @RequestParam String name,
            Pageable pageable
    ) {
        return ResponseEntity.ok(studentService.search(name, pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<StudentResponse>> filterByLevel(
            @RequestParam Level level,
            Pageable pageable
    ) {
        return ResponseEntity.ok(studentService.filterByLevel(level, pageable));
    }
}
