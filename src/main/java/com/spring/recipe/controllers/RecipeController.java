package com.spring.recipe.controllers;

import com.spring.recipe.commands.RecipeCommand;
import com.spring.recipe.exceptions.NotFoundException;
import com.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"/{id}/show", "/{id}/show/"})
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));

        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));

        return "recipe/recipeform";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("---------> Deleting recipe with id {}", id);

        recipeService.deleteById(Long.parseLong(id));
        return "redirect:/";
    }

    @PostMapping("/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("🚨️ Handling Not Found exception");
        log.error("️   -> " + exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setViewName("exceptions/404");

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception) {
        log.error("🚨️ Handling Number Format Exception  --->  " + exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setViewName("exceptions/400");

        return modelAndView;
    }
}
