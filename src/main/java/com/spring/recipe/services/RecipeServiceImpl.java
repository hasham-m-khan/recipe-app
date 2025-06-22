package com.spring.recipe.services;

import com.spring.recipe.commands.RecipeCommand;
import com.spring.recipe.converters.RecipeCommandToRecipe;
import com.spring.recipe.converters.RecipeToRecipeCommand;
import com.spring.recipe.domain.Recipe;
import com.spring.recipe.exceptions.NotFoundException;
import com.spring.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository rp;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository rp, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.rp = rp;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.info("---------> Fetching recipes from database");
        Set<Recipe> recipes = new HashSet<>();
        rp.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipe = rp.findById(id);

        if (!recipe.isPresent()) {
            throw new NotFoundException("Recipe not found for Id value: " + id);
        }
        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = rp.save(detachedRecipe);
        log.debug("Saved Recipe Id: " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {
        rp.deleteById(id);
    }
}
