package com.springboot.springsecurity.dto;

import com.springboot.springsecurity.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductWithImage {
        private byte[] imageBytes;
        private Product product;

        // Constructors, getters, and setters
    }