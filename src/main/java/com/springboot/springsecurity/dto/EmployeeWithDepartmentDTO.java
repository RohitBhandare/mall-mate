package com.springboot.springsecurity.dto;

import com.springboot.springsecurity.models.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EmployeeWithDepartmentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String mobileNumber;
    private String address;
    private String name;
    private String designation;
    private String department;

    // Getters and setters
}
