package com.cerbaresearch.recipe.management.service;

import java.time.LocalDate;
import java.util.List;

import com.cerbaresearch.recipe.management.web.dto.IngredientDTO;

public interface IngredientService {

	/**
	 * gets the ingredient by id and convert it to dto 
	 * @param ingredientId
	 * @return a dto or null if the id is null
	 * and throw EntityNotFoundException if ingredient not found
	 */
	public IngredientDTO getIngredientById(Long ingredientId);

	/**
	 * creates an ingredient using given parameters and returns the created entity converted to dto
	 * @param name the name of the ingredient
	 * @param quantity the quantity of the ingredient 
	 * @return a dto or null if name is null
	 */
	public IngredientDTO addIngredient(String name, String quantity);

	/**
	 * edits an existent ingredient and returns the updated ingredient converted to dto
	 * @param ingredientId the id of the ingredient to be updated
	 * @param name the new name of the ingredient
	 * @param quantity the new quantity of the ingredient
	 * @return a dto or null if id is null or name is null
	 * and throw EntityNotFoundException if ingredient not found
	 */
	public IngredientDTO editIngredient(Long ingredientId, String name, String quantity);

	/**
	 * deletes ingredient using the id 
	 * @param ingredientId
	 */
	public void deleteIngredient(Long ingredientId);

	/**
	 * gets all ingredients sorted by the name of the ingredients
	 * @return a list of ingredients converted to dto or empty list
	 */
	public List<IngredientDTO> getAllIngredients();

	/**
	 * gets the list of ingredients by recipe sorted by the name of the ingredients
	 * @param recipeId the id of recipe related to ingredients
	 * @return a list of ingredients converted to dto or empty list
	 */
	public List<IngredientDTO> getIngredientsByRecipe(Long recipeId);

	/**
	 * gets the ingredients by the name
	 * @param name the name of the ingredients
	 * @return a list of ingredients converted to dto or empty list
	 */
	public List<IngredientDTO> getIngredientByName(String name);

	/**
	 * gets the ingredients by the quantity
	 * @param quantity
	 * @return a list of ingredients converted to dto or empty list
	 */
	public List<IngredientDTO> getIngredientByQuantity(String quantity);

	/**
	 * gets the ingredients by the creation date
	 * @param creationDate
	 * @return a list of ingredients converted to dto or empty list
	 */
	public List<IngredientDTO> getIngredientByCreationDate(LocalDate creationDate);

	/**
	 * gets the ingredients by the modification Date
	 * @param updateDate
	 * @return a list of ingredients converted to dto or empty list
	 */
	public List<IngredientDTO> getIngredientByUpdateDate(LocalDate updateDate);
	
	
	/**
	 * gets the ingredient that have the same name and quantity as given as params
	 * @param name
	 * @param quantity
	 * @return a ingredient convert to dto or null if params are null 
	 * and throw EntityNotFoundException if ingredient not found
	 */
	public IngredientDTO getIngredientByNameAndQuantity(String name, String quantity);

}
