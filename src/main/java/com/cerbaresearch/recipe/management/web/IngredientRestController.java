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

import com.cerbaresearch.recipe.management.service.IngredientService;
import com.cerbaresearch.recipe.management.web.dto.IngredientDTO;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientRestController {

	@Autowired
	private IngredientService ingredientService;

	@GetMapping("/{id}")
	public IngredientDTO getIngredientById(@PathVariable("id") Long ingredientId) {
		return ingredientService.getIngredientById(ingredientId);

	}

	@PostMapping
	public IngredientDTO addIngredient(@RequestParam("name") String name, @RequestParam("quantity") String quantity) {
		return ingredientService.addIngredient(name, quantity);
	}

	@PutMapping("/{id}")
	public IngredientDTO editIngredient(@PathVariable("id") Long ingredientId, @RequestParam("name") String name,
			@RequestParam("quantity") String quantity) {
		return ingredientService.editIngredient(ingredientId, name, quantity);

	}

	@DeleteMapping("/{id}")
	public void deleteIngredient(@PathVariable("id") Long ingredientId) {
		ingredientService.deleteIngredient(ingredientId);
	}

	@GetMapping("/all")
	public List<IngredientDTO> gelAllIngredients() {
		return ingredientService.getAllIngredients();

	}

	@GetMapping("/recipe")
	public List<IngredientDTO> getIngredientsByRecipe(@RequestParam("recipeId") Long recipeId) {
		return ingredientService.getIngredientsByRecipe(recipeId);
	}

	@GetMapping("/names/{name}")
	public List<IngredientDTO> getIngredientByName(@PathVariable("name") String name) {
		return ingredientService.getIngredientByName(name);
	}

	@GetMapping("/quantities/{quantity}")
	public List<IngredientDTO> getIngredientByQuantity(@PathVariable("quantity") String quantity) {
		return ingredientService.getIngredientByQuantity(quantity);
	}

	@GetMapping("/creationDate/{creationDate}")
	public List<IngredientDTO> getIngredientByCreationDate(@PathVariable("creationDate") LocalDate creationDate) {
		return ingredientService.getIngredientByCreationDate(creationDate);
	}

	@GetMapping("/update/{updateDate}")
	public List<IngredientDTO> getIngredientByUpdateDate(@PathVariable("updateDate") LocalDate updateDate) {
		return ingredientService.getIngredientByCreationDate(updateDate);
	}

	@GetMapping("/name/{name}/quantity/{quantity}")
	public IngredientDTO getIngredientByNameAndQuantity(@PathVariable("name") String name,
			@PathVariable("quantity") String quantity) {
		return ingredientService.getIngredientByNameAndQuantity(name, quantity);
	}

}
