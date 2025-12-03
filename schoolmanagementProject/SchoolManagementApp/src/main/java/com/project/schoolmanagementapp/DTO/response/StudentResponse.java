package com.project.schoolmanagementapp.DTO.response;

import com.project.schoolmanagementapp.entities.enums.level;
import lombok.Data;

@Data
public class StudentResponse {

    private int id;
    private String name;
    private level level;
}
