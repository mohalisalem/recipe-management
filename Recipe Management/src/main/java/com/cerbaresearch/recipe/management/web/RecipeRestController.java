package com.cerbaresearch.recipe.management.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cerbaresearch.recipe.management.service.RecipeService;
import com.cerbaresearch.recipe.management.web.dto.RecipeDTO;

@RestController
@RequestMapping("/api/recipe")
public class RecipeRestController {

	@Autowired
	private RecipeService recipeService;

	@GetMapping("/{id}")
	public RecipeDTO getRecipeById(@PathVariable("id") Long recipeId) {
		return recipeService.getRecipeById(recipeId);

	}

	@PostMapping
	public RecipeDTO addRecipe(@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("cookingInstructions") String cookingInstructions) {
		return recipeService.addRecipe(name, description, cookingInstructions);
	}

	@PostMapping("/add/ingredient")
	public RecipeDTO addIngredientToRecipe(@RequestParam("recipeId") Long recipeId,
			@RequestParam("ingredientId") Long ingredientId) {
		return recipeService.addIngredientToRecipe(recipeId, ingredientId);
	}

	@PutMapping
	public RecipeDTO editRecipe(@RequestParam("recipeId") Long recipeId, @RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam("cookingInstructions") String cookingInstructions) {
		return recipeService.editRecipe(recipeId, name, description, cookingInstructions);
	}

	@DeleteMapping("/{id}")
	public void deleteRecipe(@PathVariable("id") Long recipeId) {
		recipeService.deleteRecipe(recipeId);
	}

	@DeleteMapping("delete/ingredient")
	public RecipeDTO deleteIngredientFromRecipe(@RequestParam("recipeId") Long recipeId,
			@RequestParam("ingredientId") Long ingredientId) {

		return recipeService.deleteIngredientFromRecipe(recipeId, ingredientId);
	}

	@GetMapping("/all")
	public List<RecipeDTO> gelAllRecipes() {
		return recipeService.getAllRecipes();
	}

	@GetMapping("/ingredient/{id}")
	public RecipeDTO getRecipesByIngredient(@PathVariable("id") Long ingredientId) {
		return recipeService.getRecipesByIngredient(ingredientId);
	}

	@GetMapping("/names/{name}")
	public List<RecipeDTO> getRecipeByName(@PathVariable("name") String name) {
		return recipeService.getRecipeByName(name);
	}

	@GetMapping("/descriptions/{description}")
	public List<RecipeDTO> getRecipeByDescription(@PathVariable("description") String description) {
		return recipeService.getRecipeByDescription(description);
	}

	@GetMapping("/cookingInsctructions/{cookingInsctructions}")
	public List<RecipeDTO> getRecipeByCookingInsctructions(
			@PathVariable("cookingInsctructions") String cookingInsctructions) {
		return recipeService.getRecipeByCookingInsctructions(cookingInsctructions);
	}

	@GetMapping("/creationDate/{creationDate}")
	public List<RecipeDTO> getRecipeByCreationDate(@PathVariable("creationDate") LocalDate creationDate) {
		return recipeService.getRecipeByCreationDate(creationDate);
	}

	@GetMapping("/updateDate/{updateDate}")
	public List<RecipeDTO> getRecipeByUpdateDate(LocalDate updateDate) {
		return recipeService.getRecipeByUpdateDate(updateDate);
	}

}
