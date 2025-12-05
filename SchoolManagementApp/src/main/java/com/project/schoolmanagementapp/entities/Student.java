package com.project.schoolmanagementapp.entities;

import jakarta.persistence.*;
import lombok.*;
import com.project.schoolmanagementapp.entities.enums.Level;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Level studentLevel;


}
