package com.project.schoolmanagementapp.DTO.response;

import com.project.schoolmanagementapp.entities.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private int id;
    private String name;
    private Level Studentlevel;
}
