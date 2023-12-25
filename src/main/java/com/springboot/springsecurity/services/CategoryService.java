package com.springboot.springsecurity.services;

import com.springboot.springsecurity.models.Category;
import com.springboot.springsecurity.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Other methods for category-related operations as needed
}
