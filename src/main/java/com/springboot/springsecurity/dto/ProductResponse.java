package com.springboot.springsecurity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.springsecurity.models.Category;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    
    private String name;
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT") // Adjust column definition based on your database
    private String description;

    private byte[] imageBytes;
    private  String base64Image;
    private double discount;
    private double price;

    @JsonIgnore
    @ManyToOne // Many products can have one category
    private Category category;


}