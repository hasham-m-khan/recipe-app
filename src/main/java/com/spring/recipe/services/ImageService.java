package com.spring.recipe.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImage(Long recipeId, MultipartFile file) throws IOException;
}
