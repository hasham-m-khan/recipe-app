package com.spring.recipe.commands;

import com.spring.recipe.domain.Difficulty;
import com.spring.recipe.domain.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private Difficulty difficulty;
    private NotesCommand notes;
    private String directions;
    private String description;
    private Integer PrepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
}
