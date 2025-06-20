package com.spring.recipe.services;

import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImage(Long recipeId, MultipartFile file) {
        try{
            Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + recipeId));

            recipe.setImage(file.getBytes());

            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occured", e.getMessage());
            e.printStackTrace();
        }
    }
}
