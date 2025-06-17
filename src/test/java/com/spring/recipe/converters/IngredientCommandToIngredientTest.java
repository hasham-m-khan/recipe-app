package com.spring.recipe.converters;

import com.spring.recipe.commands.IngredientCommand;
import com.spring.recipe.commands.UnitOfMeasureCommand;
import com.spring.recipe.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    public static final Long LONG_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    public static final BigDecimal BIG_DECIMAL_VALUE = new BigDecimal("100.00");
    public static final Long UOM_LONG_VALUE = 2L;

    IngredientCommandToIngredient converter;
    UnitOfMeasureCommandToUnitOfMeasure uomConverter;
    IngredientCommand command;

    @BeforeEach
    void setUp() {
        uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();
        converter = new IngredientCommandToIngredient(uomConverter);

        command = new IngredientCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);
        command.setAmount(BIG_DECIMAL_VALUE);
    }

    @Test
    void convertNullTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyTest() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convertNullUomTest() {
        //given
        command.setUom(null);

        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertEquals(LONG_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(BIG_DECIMAL_VALUE, ingredient.getAmount());
        assertNull(ingredient.getUom());
    }

    @Test
    void convertWithUomTest() {
        //given
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(UOM_LONG_VALUE);

        command.setUom(uomCommand);

        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertEquals(LONG_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(BIG_DECIMAL_VALUE, ingredient.getAmount());
        assertNotNull(ingredient.getUom());
    }
}