package com.project.schoolmanagementapp.DTO.request;

import com.project.schoolmanagementapp.entities.enums.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentRequest {

    @NotBlank(message = "Username is required")
    private String name;

    @NotNull(message = "Level is required")
    private Level level;
}
