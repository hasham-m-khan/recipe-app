package com.spring.recipe.services;

import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @InjectMocks
    ImageServiceImpl imageService;

    @Mock
    RecipeRepository recipeRepository;

    final Long RECIPE_ID = 1L;

    @Test
    void saveImage() throws IOException {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        MultipartFile multipartFile = new MockMultipartFile(
                "imagefile", "testing.txt", "text/plain",
                "This is a test".getBytes());

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImage(RECIPE_ID, multipartFile);

        //then
        verify(recipeRepository, times(1)).save(captor.capture());
        Recipe capturedRecipe = captor.getValue();

        assertEquals(multipartFile.getBytes().length, capturedRecipe.getImage().length);
    }
}