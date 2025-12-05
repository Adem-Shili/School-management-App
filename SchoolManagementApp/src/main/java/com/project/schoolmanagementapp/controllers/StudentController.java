package com.project.schoolmanagementapp.controllers;

import com.project.schoolmanagementapp.DTO.request.StudentRequest;
import com.project.schoolmanagementapp.DTO.response.StudentResponse;
import com.project.schoolmanagementapp.entities.enums.Level;
import com.project.schoolmanagementapp.services.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    /**
     * Handles fetching all students with optional search and filter parameters.
     * Delegates filtering, searching, and pagination to a single service method.
     * * @param search Optional search query (maps to 'name' in the service).
     * @param level Optional level filter.
     * @param pageable Pagination and sorting information.
     * @return A Page of StudentResponse DTOs.
     */
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAll(
            @RequestParam(required = false) String search, // Matches Angular's 'searchQuery'
            @RequestParam(required = false) Level level,   // Matches Angular's 'levelFilter'
            Pageable pageable
    ) {
        // Call a single, consolidated service method to handle all three states:
        // 1. No search/filter (default page)
        // 2. Search by name
        // 3. Filter by level
        return ResponseEntity.ok(studentService.getAllStudents(search, level, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /* * The following endpoints are now redundant and removed as their logic has been
     * merged into the primary GET /api/students mapping above.
     * * @GetMapping("/search")
     * public ResponseEntity<Page<StudentResponse>> search(
     * @RequestParam String name,
     * Pageable pageable
     * ) {
     * return ResponseEntity.ok(studentService.search(name, pageable));
     * }
     * * @GetMapping("/filter")
     * public ResponseEntity<Page<StudentResponse>> filterByLevel(
     * @RequestParam Level level,
     * Pageable pageable
     * ) {
     * return ResponseEntity.ok(studentService.filterByLevel(level, pageable));
     * }
     */

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> export() {

        // Get the CSV data stream from the service
        InputStreamResource file = new InputStreamResource(studentService.exportStudentsToCsv());

        // Define the headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/csv")) // Set the content type
                .body(file);
    }
}