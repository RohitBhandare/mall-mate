package com.springboot.springsecurity.repositories;


import com.springboot.springsecurity.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can add custom query methods here if needed
}
