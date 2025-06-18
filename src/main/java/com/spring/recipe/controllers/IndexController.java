package com.spring.recipe.controllers;

import com.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService rs;

    public IndexController(RecipeService rs) {
        this.rs = rs;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model theModel) {
        log.debug("---------> Fetching Index view.");

        theModel.addAttribute("recipes", rs.getRecipes());
        return "index";
    }
}
