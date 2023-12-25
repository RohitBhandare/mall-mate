package com.springboot.springsecurity.services;

import com.springboot.springsecurity.dto.ProductResponse;
import com.springboot.springsecurity.models.Category;
import com.springboot.springsecurity.models.Product;
import com.springboot.springsecurity.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Value("${image.directory}")
    private String imageDirectory;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private  CategoryService categoryService;

    public Product createProduct(Product product, Long categoryId, MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageAddress = saveImageAndGetAddress(imageFile);
                product.setImage(imageAddress);
            } else {
                // Handle the case where no image is provided
            }

            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                product.setCategory(category);
            } else {
                // Handle the case where category is not found
            }

            return productRepository.save(product);
        } catch (IOException e) {
            // Handle the exception
            return null;
        }
    }

    private String saveImageAndGetAddress(MultipartFile imageFile) throws IOException {
        String imageName = imageFile.getOriginalFilename();
        Path directory = Paths.get(imageDirectory);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        Path imagePath = Paths.get(imageDirectory, imageName);
        Files.write(imagePath, imageFile.getBytes());

        return imagePath.toString();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::populateProductResponseWithImage)
                .collect(Collectors.toList());
//        return Flux.fromIterable(productRepository.findAll());
    }

    private ProductResponse populateProductResponseWithImage(Product product) {
        String imagePath = product.getImage();
        ProductResponse productResponse = new ProductResponse();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Path imageFilePath = Paths.get(imagePath);
                byte[] imageBytes = Files.readAllBytes(imageFilePath);

                productResponse.setImageBytes(imageBytes);
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                productResponse.setBase64Image(base64Image);
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        }

        // Set other fields in productResponse using product data
        productResponse.setName(product.getName());
        productResponse.setTitle(product.getTitle());
        productResponse.setDescription(product.getDescription());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setPrice(product.getPrice());

        // Set other fields as needed

        return productResponse;
    }


    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = getProductById(productId);
        // Update fields of existingProduct with values from updatedProduct
        // Make sure to handle the update logic according to your requirements
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setImage(updatedProduct.getImage());
        existingProduct.setDiscount(updatedProduct.getDiscount());
        existingProduct.setPrice(updatedProduct.getPrice());
        //existingProduct.setCategories(updatedProduct.getCategories());
        
        return productRepository.save(existingProduct);
    }
}
