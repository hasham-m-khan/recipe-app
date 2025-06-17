package com.spring.recipe.converters;

import com.spring.recipe.commands.UnitOfMeasureCommand;
import com.spring.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final Long LONG_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void ConvertNullTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void ConvertEmptyTest() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void convertTest() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        //when
        UnitOfMeasureCommand command = converter.convert(uom);

        //then
        assertNotNull(command);
        assertEquals(uom.getId(), command.getId());
        assertEquals(uom.getDescription(), command.getDescription());
    }
}