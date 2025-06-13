package com.spring.recipe.services;

import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository rp;

    public RecipeServiceImpl(RecipeRepository rp) {
        this.rp = rp;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.info("I'm in the recipe service!");
        Set<Recipe> recipes = new HashSet<>();
        rp.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(long id) {
        Optional<Recipe> recipe = rp.findById(id);

        if (!recipe.isPresent()) {
            throw new RuntimeException("Recipe not found!");
        }
        return recipe.get();
    }
}
