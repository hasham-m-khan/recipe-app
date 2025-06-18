package com.spring.recipe.services;

import com.spring.recipe.commands.RecipeCommand;
import com.spring.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(long id);
    RecipeCommand findCommandById(long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
