package com.springboot.springsecurity.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private  String description;

    @OneToMany(mappedBy = "category") // Refers to the "category" field in the Product entity
    private List<Product> products;
    

}