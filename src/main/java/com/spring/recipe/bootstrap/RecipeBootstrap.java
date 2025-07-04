package com.spring.recipe.bootstrap;

import com.spring.recipe.domain.*;
import com.spring.recipe.repositories.CategoryRepository;
import com.spring.recipe.repositories.RecipeRepository;
import com.spring.recipe.repositories.UnitOfMeasureRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class RecipeBootstrap implements CommandLineRunner {

    private final RecipeRepository rr;
    private final CategoryRepository cr;
    private final UnitOfMeasureRepository uomr;

    public RecipeBootstrap(RecipeRepository rr, CategoryRepository cr, UnitOfMeasureRepository uomr) {
        this.rr = rr;
        this.cr = cr;
        this.uomr = uomr;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        rr.saveAll(getRecipes());
        log.debug("---------> Loading Bootstrap Data.");
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<Recipe>();

        // Get UoMs
        HashMap<String, UnitOfMeasure> uomMap = new HashMap<>();
        ArrayList<UnitOfMeasure> uomsArray = (ArrayList<UnitOfMeasure>) uomr.findAll();

        for (UnitOfMeasure uomTemp: uomsArray) {
            uomMap.put(uomTemp.getDescription(), uomTemp);
        }

        // Get Categories
        HashMap<String, Category> catsMap = new HashMap<String, Category>();
        ArrayList<Category> crArray = (ArrayList<Category>)cr.findAll();

        for (Category crTemp: crArray) {
            catsMap.put(crTemp.getDescription(), crTemp);
        }

        //Ingredients for Recipes
        String[] recipeUOMs = {"Each", "Teaspoon", "Tablespoon", "Dash", "Pint", "Cup"};
        for (String recipeUOM : recipeUOMs) {
            if (!uomMap.containsKey(recipeUOM)) {
                throw new RuntimeException("Expected UoM not found!");
            }
        }

        //Categories for Recipes
        String[] recipeCats = {"American", "Mexican"};
        for (String recipeCat : recipeCats) {
            if (!catsMap.containsKey(recipeCat)) {
                throw new RuntimeException("Expected Category not found!");
            }
        }


        // Perfect Guacamole Recipe
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections(
            "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
            "\n" +
            "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
            "\n" +
            "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
            "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
            "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
            "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
            "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
            "\n" +
            "\n" +
            "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");
        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes(
            "For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
            "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
            "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
            "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
            "\n" +
            "\n" +
            "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws"
        );
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), uomMap.get("Each")));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), uomMap.get("Teaspoon")));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), uomMap.get("Tablespoon")));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), uomMap.get("Tablespoon")));
        guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), uomMap.get("Each")));
        guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), uomMap.get("Tablespoon")));
        guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), uomMap.get("Dash")));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), uomMap.get("Each")));

        guacRecipe.getCategories().add(catsMap.get("American"));
        guacRecipe.getCategories().add(catsMap.get("Mexican"));

        guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setServings(4);
        guacRecipe.setSource("Simply Recipes");

        recipes.add(guacRecipe);

        // Spicy Grilled Taco Recipe
        Recipe spicyTaco = new Recipe();
        spicyTaco.setDescription("Spicy Grilled Chicken Taco");
        spicyTaco.setPrepTime(9);
        spicyTaco.setCookTime(20);
        spicyTaco.setDifficulty(Difficulty.MODERATE);
        spicyTaco.setDirections(
            "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
            "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
            "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
            "\n" +
            "\n" +
            "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
            "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
            "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
            "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
            "\n" +
            "\n" +
            "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm"
        );
        Notes spicyNotes = new Notes();
        spicyNotes.setRecipeNotes(
            "We have a family motto and it is this: Everything goes better in a tortilla.\n" +
            "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
            "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
            "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
            "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
            "\n" +
            "\n" +
            "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ"
        );
        spicyTaco.setNotes(spicyNotes);

        spicyTaco.addIngredient(new Ingredient("Ancho Chilli Powder", new BigDecimal(2), uomMap.get("Tablespoon")));
        spicyTaco.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), uomMap.get("Tablespoon")));
        spicyTaco.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), uomMap.get("Teaspoon")));
        spicyTaco.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), uomMap.get("Teaspoon")));
        spicyTaco.addIngredient(new Ingredient("Sugar", new BigDecimal(1), uomMap.get("Teaspoon")));
        spicyTaco.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), uomMap.get("Teaspoon")));
        spicyTaco.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), uomMap.get("Each")));
        spicyTaco.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), uomMap.get("Tablespoon")));
        spicyTaco.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), uomMap.get("Tablespoon")));
        spicyTaco.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), uomMap.get("Tablespoon")));
        spicyTaco.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), uomMap.get("Tablespoon")));
        spicyTaco.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), uomMap.get("Each")));
        spicyTaco.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), uomMap.get("Cup")));
        spicyTaco.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), uomMap.get("Each")));
        spicyTaco.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), uomMap.get("Each")));
        spicyTaco.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), uomMap.get("Pint")));
        spicyTaco.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), uomMap.get("Each")));
        spicyTaco.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), uomMap.get("Each")));
        spicyTaco.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), uomMap.get("Cup")));
        spicyTaco.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), uomMap.get("Each")));

        spicyTaco.getCategories().add(catsMap.get("American"));
        spicyTaco.getCategories().add(catsMap.get("Mexican"));

        spicyTaco.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        spicyTaco.setServings(6);
        spicyTaco.setSource("Simply Recipes");

        recipes.add(spicyTaco);

        return recipes;
    }
}
