package com.project.schoolmanagementapp.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String password;
}