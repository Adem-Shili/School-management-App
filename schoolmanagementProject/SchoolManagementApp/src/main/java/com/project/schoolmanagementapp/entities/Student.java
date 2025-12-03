package com.project.schoolmanagementapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.project.schoolmanagementapp.entities.enums.level;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Student")
public class Student {
    @Id
    private int id;
    private String name;
    private level studentLevel;


}
