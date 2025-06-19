package com.spring.recipe.services;

import com.spring.recipe.commands.IngredientCommand;
import com.spring.recipe.converters.IngredientCommandToIngredient;
import com.spring.recipe.converters.IngredientToIngredientCommand;
import com.spring.recipe.domain.Ingredient;
import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;
import com.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;
    private final IngredientToIngredientCommand typeToCommandConverter;
    private final IngredientCommandToIngredient commandToTypeConverter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository,
                                 IngredientToIngredientCommand typeToCommandConverter,
                                 IngredientCommandToIngredient commandToTypeConverter) {
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
        this.typeToCommandConverter = typeToCommandConverter;
        this.commandToTypeConverter = commandToTypeConverter;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()) {
            log.error("Ingredient (Id: "+ ingredientId + ") not found for recipe id " + recipeId);
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> typeToCommandConverter.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient (Id: "+ ingredientId + ") not found for recipe id " + recipeId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand (IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()) {
            log.error("Recipe not found for id: " + command.getRecipeId());
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        if(ingredientOptional.isPresent()) {
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUom(uomRepository
                    .findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("Uom not found for id: " + command.getUom().getId())));
        } else {
            //add new ingredient
            Ingredient ingredient = commandToTypeConverter.convert(command);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();
        log.debug(savedIngredientOptional.isPresent() ? "IS PRESENT" : "IS NOT PRESENT");

        if (!savedIngredientOptional.isPresent()) {
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                    .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                    .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
                    .findFirst();
        }

        //TODO: Check for fail
        return typeToCommandConverter.convert(savedIngredientOptional.get());
    }
}
