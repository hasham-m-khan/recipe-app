package com.spring.recipe.converters;

import com.spring.recipe.commands.IngredientCommand;
import com.spring.recipe.commands.RecipeCommand;
import com.spring.recipe.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = 5;
    public static final String DESCRIPTION = "This is a test";
    public static final Difficulty difficulty = Difficulty.EASY;
    public static final String DIRECTIONS = "Some directions";
    public static final Integer PREP_TIME = 20;
    public static final Integer SERVINGS = 5;
    public static final String SOURCE = "Some source";
    public static final String URL = "https://www.someurl.com";
    public static final Long INGREDIENT_ID_1 = 2L;
    public static final Long INGREDIENT_ID_2 = 3L;
    public static final Long NOTES_ID = 4L;
    public static final Long CATEGORY_ID_1 = 5L;
    public static final Long CATEGORY_ID_2 = 6L;

    NotesToNotesCommand notesConverter;
    CategoryToCategoryCommand categoryConverter;
    IngredientToIngredientCommand ingredientConverter;
    UnitOfMeasureToUnitOfMeasureCommand uomConverter;
    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        notesConverter = new NotesToNotesCommand();
        categoryConverter = new CategoryToCategoryCommand();
        uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
        ingredientConverter = new IngredientToIngredientCommand(uomConverter);
        converter = new RecipeToRecipeCommand(notesConverter, categoryConverter, ingredientConverter);
    }

    @Test
    void convertNullTest() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyTest() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convertEmptyIngredientTest() throws Exception {
        //given
        Recipe recipe = new Recipe();

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertEquals(0, recipeCommand.getIngredients().size());
    }

    @Test
    void convertEmptyCategoryTest() throws Exception {
        //given
        Recipe recipe = new Recipe();

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertEquals(0, recipeCommand.getCategories().size());
    }

    @Test
    void convertNullNotesTest() throws Exception {
        //given
        Recipe recipe = new Recipe();

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertNull(recipeCommand.getNotes());
    }

    @Test
    void convertNullRecipeTest() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    void convertTest() throws Exception {
        //when
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(difficulty);
        recipe.setDirections(DIRECTIONS);
        recipe.setPrepTime(PREP_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID_1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID_2);

        Category category1 = new Category();
        category1.setId(CATEGORY_ID_1);
        Category category2 = new Category();
        category2.setId(CATEGORY_ID_2);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);
        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);
        recipe.setNotes(notes);

        //given
        RecipeCommand command = converter.convert(recipe);

        //then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(difficulty, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(NOTES_ID, command.getNotes().getId());
        assertEquals(2, command.getIngredients().size());
        assertEquals(2, command.getCategories().size());
    }
}