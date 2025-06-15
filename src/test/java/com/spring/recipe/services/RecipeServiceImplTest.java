package com.spring.recipe.services;

import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() throws Exception {
        // Enables Mockito annotations.
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        // When recipeRepository.findAll() is called, then return back recipeSet.
        when(recipeRepository.findAll()).thenReturn(recipeSet);

        // recipeService.getRecipes() calls the recipeRepository.findAll() which will return recipeSet.
        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());

        // Verifies that in the recipeRepository, the findAll() method is called only once.
        verify(recipeRepository, times(1)).findAll();
    }
}