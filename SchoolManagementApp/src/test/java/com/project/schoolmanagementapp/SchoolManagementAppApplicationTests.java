package com.project.schoolmanagementapp;

import com.project.schoolmanagementapp.DTO.request.AdminRequest;
import com.project.schoolmanagementapp.DTO.request.StudentRequest;
import com.project.schoolmanagementapp.entities.enums.Level;
import com.project.schoolmanagementapp.repositories.AdminRepository;
import com.project.schoolmanagementapp.repositories.StudentRepository;
import com.project.schoolmanagementapp.services.Impl.AdminService;
import com.project.schoolmanagementapp.services.Impl.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SchoolManagementAppApplicationTests {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        // Simple test to ensure the Spring application context loads correctly
        assertThat(adminService).isNotNull();
        assertThat(studentService).isNotNull();
    }

    // ----------------------------------------------------------------------

    /**
     * Test case for Admin registration and UserDetails loading.
     * @Transactional ensures the data is rolled back after the test.
     */
    @Test
    @Transactional
    void testRegisterAdminAndLoadUser() {
        // 1. Initiate the Admin entity data for testing
        String testUsername = "admin";
        String testPassword = "admin123";
        AdminRequest request = new AdminRequest();
        request.setName(testUsername);
        request.setPassword(testPassword);


        // 2. Perform the action (Register the Admin via the Service)
        var response = adminService.register(request);

        // 3. Verify the registration response
        assertNotNull(response.getId(), "Admin response ID should not be null after registration.");
        assertEquals(testUsername, response.getName(), "Admin response name should match the request.");

        // 4. Verify the Admin was saved to the database with an encoded password
        var savedAdmin = adminRepository.findByName(testUsername).orElse(null);

        assertNotNull(savedAdmin, "Admin should be found in the database.");
        assertTrue(passwordEncoder.matches(testPassword, savedAdmin.getPassword()),
                "Encoded password must match the raw password.");

        // 5. Test the loadUserByUsername method used by Spring Security
        UserDetails userDetails = adminService.loadUserByUsername(testUsername);

        assertNotNull(userDetails, "UserDetails should be loaded.");
        assertEquals(testUsername, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")),
                "User should have 'ADMIN' authority.");
    }

    // ----------------------------------------------------------------------

    /**
     * Test case for Student creation.
     */
    @Test
    @Transactional
    void testStudentCreation() {
        // 1. Initiate the Student entity data for testing
        String testStudentName = "adem shili";
        Level testLevel = Level.commun;
        StudentRequest request = new StudentRequest();
        request.setName(testStudentName);
        request.setLevel(testLevel);

        // 2. Perform the action (Create the Student via the Service)
        var response = studentService.create(request);

        // 3. Verify the creation response
        assertEquals(testStudentName, response.getName(), "Student name should match the request.");
        assertEquals(testLevel, response.getStudentlevel(), "Student level should match the request.");

        // 4. Verify the Student was saved to the database
        var savedStudent = studentRepository.findById(response.getId()).orElse(null);

        assertNotNull(savedStudent, "Student should be found in the database.");
        assertEquals(testStudentName, savedStudent.getName());
        assertEquals(testLevel, savedStudent.getStudentLevel());
    }
}