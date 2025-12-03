// File: com/project/schoolmanagementapp/controllers/TestController.java

package com.project.schoolmanagementapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String testConnection() {
        return "Backend is running and reachable!";
    }
}