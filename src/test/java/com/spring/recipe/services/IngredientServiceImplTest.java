package com.spring.recipe.services;

import com.spring.recipe.commands.IngredientCommand;
import com.spring.recipe.commands.RecipeCommand;
import com.spring.recipe.commands.UnitOfMeasureCommand;
import com.spring.recipe.converters.IngredientCommandToIngredient;
import com.spring.recipe.converters.IngredientToIngredientCommand;
import com.spring.recipe.domain.Ingredient;
import com.spring.recipe.domain.Recipe;
import com.spring.recipe.domain.UnitOfMeasure;
import com.spring.recipe.repositories.RecipeRepository;
import com.spring.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
    @Mock
    UnitOfMeasureRepository uomRepository;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientToIngredientCommand typeToCommandConverter;

    @Mock
    IngredientCommandToIngredient commandToTypeConverter;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    final Long RECIPE_ID = 1L;
    final Long INGREDIENT_ID = 2L;
    final Long UNIT_OF_MEASURE_ID = 3L;
    final String INGREDIENT_DESCRIPTION = "Some description";
    final BigDecimal INGREDIENT_QUANTITY = new BigDecimal(10.0);

    @Test
    void findByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setRecipe(recipe);

        recipe.addIngredient(ingredient);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setRecipeId(RECIPE_ID);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(typeToCommandConverter.convert(any(Ingredient.class))).thenReturn(ingredientCommand);


        //when
        IngredientCommand savedCommand = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID);

        //then
        assertNotNull(savedCommand);
        assertEquals(INGREDIENT_ID, savedCommand.getId());
        assertEquals(RECIPE_ID, savedCommand.getRecipeId());
    }

    @Test
    void testSaveIngredientCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(null);
        command.setRecipeId(RECIPE_ID);
        command.setDescription(INGREDIENT_DESCRIPTION);
        command.setAmount(INGREDIENT_QUANTITY);
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(UNIT_OF_MEASURE_ID);
        command.setUom(uomCommand);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UNIT_OF_MEASURE_ID);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setDescription(INGREDIENT_DESCRIPTION);
        ingredient.setAmount(INGREDIENT_QUANTITY);
        ingredient.setUom(uom);

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        when(commandToTypeConverter.convert(any(IngredientCommand.class))).thenReturn(ingredient);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        IngredientCommand savedIngredientCommand = new IngredientCommand();
        savedIngredientCommand.setId(INGREDIENT_ID);
        savedIngredientCommand.setRecipeId(RECIPE_ID);
        savedIngredientCommand.setDescription(INGREDIENT_DESCRIPTION);
        savedIngredientCommand.setAmount(INGREDIENT_QUANTITY);
        savedIngredientCommand.setUom(uomCommand);
        when(typeToCommandConverter.convert(any(Ingredient.class))).thenReturn(savedIngredientCommand);

        //when
        IngredientCommand resultCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertNotNull(resultCommand);
        assertEquals(INGREDIENT_ID, resultCommand.getId());
        assertEquals(RECIPE_ID, resultCommand.getRecipeId());
        assertEquals(INGREDIENT_DESCRIPTION, resultCommand.getDescription());
        assertEquals(INGREDIENT_QUANTITY, resultCommand.getAmount());
        assertEquals(UNIT_OF_MEASURE_ID, resultCommand.getUom().getId());

        verify(recipeRepository, times(1)).findById(RECIPE_ID);
        verify(commandToTypeConverter, times(1)).convert(command);
        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(typeToCommandConverter, times(1)).convert(any(Ingredient.class));
    }

    @Test
    void deleteById() {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}