package com.springboot.springsecurity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    
    private String name;
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description; // Store long description as text or CLOB

//    @Column(columnDefinition = "LONGBLOB")
//    Store the image as byte[] (binary data)
    private String image;

    private double discount;
    private double price;

    @JsonIgnore
    @ManyToOne // Many products can have one category
    private Category category;


}
