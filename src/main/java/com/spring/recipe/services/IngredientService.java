package com.spring.recipe.services;

import com.spring.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand (IngredientCommand command);

    void deleteById(Long recipeId, Long id);
}
