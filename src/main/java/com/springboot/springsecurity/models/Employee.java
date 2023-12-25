package com.springboot.springsecurity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String mobileNumber;
    private String address;
    private String name;
    private String designation;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id") // This references the department_id column in the Employee table
    private Department department;
}
