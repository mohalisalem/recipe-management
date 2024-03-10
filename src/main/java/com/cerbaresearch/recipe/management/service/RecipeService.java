package com.cerbaresearch.recipe.management.service;

import java.time.LocalDate;
import java.util.List;

import com.cerbaresearch.recipe.management.web.dto.RecipeDTO;

public interface RecipeService {

	/**
	 * gets the recipe by id and convert it to dto 
	 * @param recipeId
	 * @return a dto or null if the id is null
	 * and throw EntityNotFoundException if recipe not found
	 */
	public RecipeDTO getRecipeById(Long recipeId);

	/**
	 * creates an recipe using given parameters and returns the created entity converted to dto
	 * @param name the name of the recipe
	 * @param description the description of the recipe 
	 * @param cookingInstructions the instructions of cooking
	 * @return a dto or null if name is null and throw EntityAlreadyExistsException if recipe with same name and description
	 */
	public RecipeDTO addRecipe(String name, String description, String cookingInstructions);
	
	/**
	 * add ingredient to a recipe
	 * @param recipeId the id of the recipe
	 * @param ingredient the id of ingredient to be added
	 * @return the recipe after update and being converted to dto 
	 */
	public RecipeDTO addIngredientToRecipe(Long recipeId, Long ingredientId);
	

	/**
	 * edits an existent recipe and returns the updated recipe converted to dto
	 * @param recipeId the id of the recipe to be updated
	 * @param name the new name of the recipe
	 * @param description the new description of the recipe
	 * @param cookingInstructions the instructions of cooking
	 * @return a dto or null if id is null or name is null
	 * and throw EntityNotFoundException if recipe not found
	 */
	public RecipeDTO editRecipe(Long recipeId, String name, String description, String cookingInstructions);

	/**
	 * deletes recipe using the id 
	 * @param recipeId
	 */
	public void deleteRecipe(Long recipeId);
	
	/**
	 * removes an ingredient from a recipe
	 * @param recipeId
	 * @param ingredientId
	 * @return
	 */
	public RecipeDTO deleteIngredientFromRecipe(Long recipeId, Long ingredientId);

	/**
	 * gets all recipes sorted by the name of the recipes
	 * @return a list of recipes converted to dto or empty list
	 */
	public List<RecipeDTO> getAllRecipes();

	/**
	 * gets the list of recipes by recipe sorted by the name of the recipes
	 * @param ingredientId the id of ingrdient related to recipes
	 * @returna dto or null if id is null or name is null
	 * and throw EntityNotFoundException if recipe not found
	 */
	public RecipeDTO getRecipesByIngredient(Long ingredientId);

	/**
	 * gets the recipes by the name
	 * @param name the name of the recipes
	 * @return a list of recipes converted to dto or empty list
	 */
	public List<RecipeDTO> getRecipeByName(String name);

	/**
	 * gets the recipes by the description
	 * @param description
	 * @return a list of recipes converted to dto or empty list
	 */
	public List<RecipeDTO> getRecipeByDescription(String description);
	
	
	/**
	 * gets the recipes by the description
	 * @param cookingInsctructions
	 * @return a list of recipes converted to dto or empty list
	 */
	public List<RecipeDTO> getRecipeByCookingInsctructions(String cookingInsctructions);

	/**
	 * gets the recipes by the creation date
	 * @param creationDate
	 * @return a list of recipes converted to dto or empty list
	 */
	public List<RecipeDTO> getRecipeByCreationDate(LocalDate creationDate);

	/**
	 * gets the recipes by the modification Date
	 * @param updateDate
	 * @return a list of recipes converted to dto or empty list
	 */
	public List<RecipeDTO> getRecipeByUpdateDate(LocalDate updateDate);
	

}
