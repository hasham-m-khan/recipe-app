package com.spring.recipe.services;

import com.spring.recipe.commands.RecipeCommand;
import com.spring.recipe.converters.RecipeCommandToRecipe;
import com.spring.recipe.converters.RecipeToRecipeCommand;
import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    void testGetRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optRecipe);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(any());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void testGetRecipeCommandById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(1L);

        assertNotNull(commandById, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void testGetRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        // When recipeRepository.findAll() is called, then return back recipeSet.
        when(recipeRepository.findAll()).thenReturn(recipeSet);

        // recipeService.getRecipesTest() calls the recipeRepository.findAll() which will return recipeSet.
        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());

        // Verifies that in the recipeRepository, the findAll() method is called only once.
        verify(recipeRepository, times(1)).findAll();

        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void testDeleteRecipeById() {
        //given
        final Long ID_TO_DELETE = 2L;

        //when
        recipeService.deleteById(ID_TO_DELETE);

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}