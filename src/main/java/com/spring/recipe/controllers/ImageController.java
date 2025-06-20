package com.spring.recipe.controllers;

import com.spring.recipe.commands.RecipeCommand;
import com.spring.recipe.services.ImageService;
import com.spring.recipe.services.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

        try {
            imageService.saveImage(recipeId, file);
        } catch(IOException e) {
            log.error(e.getMessage());
        }

        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImage(@PathVariable Long id, HttpServletResponse response, InputStream inputStream) throws IOException {
        RecipeCommand command = recipeService.findCommandById(id);

        if (command.getImage() != null) {
            byte[] byteArray = new byte[command.getImage().length];
            int i = 0;

            for (byte b : command.getImage()) {
                byteArray[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
