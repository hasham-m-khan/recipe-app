package com.spring.recipe.converters;

import com.spring.recipe.commands.IngredientCommand;
import com.spring.recipe.domain.Ingredient;
import com.spring.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
class IngredientToIngredientCommandTest {

    public static final Long LONG_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    public static final BigDecimal BIG_DECIMAL_VALUE = new BigDecimal("100.00");
    public static final Long UOM_LONG_VALUE = 2L;

    IngredientToIngredientCommand converter;
    UnitOfMeasureToUnitOfMeasureCommand uomConverter;
    Ingredient ingredient;

    @BeforeEach
    void setUp() {
        uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
        converter = new IngredientToIngredientCommand(uomConverter);

        ingredient = new Ingredient();
        ingredient.setId(LONG_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(BIG_DECIMAL_VALUE);
    }

    @Test
    void convertNullTest() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyTest() throws Exception {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void convertNullUomTest() throws Exception {
        //given
        ingredient.setUom(null);

        //when
        IngredientCommand command = converter.convert(ingredient);

        //then
        assertNull(command.getUom());
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(BIG_DECIMAL_VALUE, command.getAmount());
    }

    @Test
    void convertWithUomTest() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_LONG_VALUE);

        ingredient.setUom(uom);

        //when
        IngredientCommand command = converter.convert(ingredient);

        //then
        assertNotNull(command);
        assertEquals(ingredient.getId(), command.getId());
        assertNotNull(command.getUom());
        assertEquals(UOM_LONG_VALUE, command.getUom().getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(BIG_DECIMAL_VALUE, command.getAmount());
    }
}