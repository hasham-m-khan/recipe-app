package com.spring.recipe.converters;

import com.spring.recipe.commands.NotesCommand;
import com.spring.recipe.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    public static final Long LONG_VALUE = 1L;
    public static final String RECIPE_NOTES = "recipeNotes";

    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void convertNullTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyTest() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    void convertTest() {
        //given
        Notes notes = new Notes();
        notes.setId(LONG_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand command = converter.convert(notes);

        //then
        assertNotNull(command);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(RECIPE_NOTES, command.getRecipeNotes());
    }
}