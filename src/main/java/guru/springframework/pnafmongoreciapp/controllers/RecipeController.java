package guru.springframework.pnafmongoreciapp.controllers;

import guru.springframework.pnafmongoreciapp.commands.RecipeCommand;
import guru.springframework.pnafmongoreciapp.exceptions.NotFoundException;
import guru.springframework.pnafmongoreciapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeController {
    public static final String RECIPE_RECIPE_FORM = "recipe/recipeForm";
    public static final String RECIPE_SHOW = "recipe/show";
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));

        return RECIPE_SHOW;
    }

    @RequestMapping({"recipe/new"})
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPE_FORM;
    }

    @RequestMapping({"recipe/{id}/update"})
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return RECIPE_RECIPE_FORM;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Validated @ModelAttribute RecipeCommand command, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach( objectError -> {
                log.debug(objectError.toString());
            });

            return RECIPE_RECIPE_FORM;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id:" + id);

        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
