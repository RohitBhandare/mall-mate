package com.springboot.springsecurity.controllers;

import com.springboot.springsecurity.dto.ProductResponse;
import com.springboot.springsecurity.models.Product;
import com.springboot.springsecurity.services.CategoryService;
import com.springboot.springsecurity.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(
            @RequestParam("name") String name,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("discount") double discount,
            @RequestParam("price") double price,
            @RequestParam("categoryId") Long categoryId
    ) {
        Product product = new Product();
        product.setName(name);
        product.setTitle(title);
        product.setDescription(description);
        product.setDiscount(discount);
        product.setPrice(price);

        Product createdProduct = productService.createProduct(product, categoryId, imageFile);
        if (createdProduct != null) {
            return new ResponseEntity<>("Product added successfully"+product, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping("/{productId}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
//        Product product = productService.getProductById(productId);
//        return new ResponseEntity<>(product, HttpStatus.OK);
//    }

    @GetMapping("/image/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        String imagePath = product.getImage();

        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Path imageFilePath = Paths.get(imagePath);
                byte[] imageBytes = Files.readAllBytes(imageFilePath);

                // Set content type to image/jpeg (adjust according to your image type)
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }





    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(productId, updatedProduct);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
