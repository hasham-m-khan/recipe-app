package com.spring.recipe.controllers;

import com.spring.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService rs;

    public IndexController(RecipeService rs) {
        this.rs = rs;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model theModel) {
        theModel.addAttribute("recipes", rs.getRecipes());
        return "index";
    }
}
