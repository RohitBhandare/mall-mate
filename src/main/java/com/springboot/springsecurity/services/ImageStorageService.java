package com.springboot.springsecurity.services;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ImageStorageService {

    private static final String IMAGE_DIRECTORY = "src/main/resources/Product_Images"; // Path to your image directory

    public void saveImage(InputStream imageInputStream, String imageName) throws IOException {
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if it doesn't exist
        }

        File imageFile = new File(directory, imageName);
        try (OutputStream outputStream = new FileOutputStream(imageFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = imageInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
