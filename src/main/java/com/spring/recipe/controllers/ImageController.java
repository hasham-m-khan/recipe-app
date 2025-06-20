package com.spring.recipe.controllers;

import com.spring.recipe.services.ImageService;
import com.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("recipe/{recipeId}/image")
    public String updateImageForm(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/imageform";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImage(recipeId, file);

        return "redirect:/recipe/" + recipeId + "/show";
    }
}
