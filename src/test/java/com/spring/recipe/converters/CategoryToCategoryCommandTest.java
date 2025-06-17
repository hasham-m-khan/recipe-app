package com.spring.recipe.converters;

import com.spring.recipe.commands.CategoryCommand;
import com.spring.recipe.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    public static final Long LONG_VALUE = 1L;
    public static final String DESCRIPTION = "Description";

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void convertNullTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyTest() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convertTest() {
        //given
        Category category = new Category();
        category.setId(LONG_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand command = converter.convert(category);

        //then
        assertNotNull(command);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}