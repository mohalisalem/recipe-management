package com.cerbaresearch.recipe.management.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cerbaresearch.recipe.management.persistence.dao.IngredientRepository;
import com.cerbaresearch.recipe.management.persistence.dao.RecipeRepository;
import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.persistence.model.Recipe;
import com.cerbaresearch.recipe.management.service.RecipeService;
import com.cerbaresearch.recipe.management.utils.ComparatorUtil;
import com.cerbaresearch.recipe.management.web.dto.RecipeDTO;
import com.cerbaresearch.recipe.management.web.dto.mapper.RecipeMapper;
import com.cerbaresearch.recipe.management.web.exception.EntityNotFoundException;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private RecipeMapper recipeMapper;

	@Override
	public RecipeDTO getRecipeById(Long recipeId) {
		if (recipeId == null) {
			return null;
		}
		Recipe recipe = getRecipeByIdOrThrowException(recipeId);
		return recipeMapper.convertRecipeToDTO(recipe);
	}

	@Override
	public RecipeDTO addRecipe(String name, String description, String cookingInstructions) {

		Recipe recipe = new Recipe();
		recipe.setCookingInstructions(cookingInstructions);
		recipe.setDescription(description);
		recipe.setName(name);
		recipe.setCreationDate(LocalDate.now());
		return recipeMapper.convertRecipeToDTO(recipeRepository.save(recipe));
	}

	@Override
	public RecipeDTO editRecipe(Long recipeId, String name, String description, String cookingInstructions) {
		if (recipeId == null || name == null) {
			return null;
		}
		Recipe recipe = getRecipeByIdOrThrowException(recipeId);
		recipe.setName(name);
		recipe.setCookingInstructions(cookingInstructions);
		recipe.setDescription(description);
		recipe.setUpdateDate(LocalDate.now());
		return recipeMapper.convertRecipeToDTO(recipeRepository.save(recipe));
	}

	@Override
	public void deleteRecipe(Long recipeId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if(recipeOptional.isPresent()) {
			List<Ingredient> recipeIngredient = recipeOptional.get().getIngredients();
			for(Ingredient ingredient : recipeIngredient) {
				ingredientRepository.delete(ingredient);
			}
			recipeRepository.deleteById(recipeId);
		}
	}

	@Override
	public List<RecipeDTO> getAllRecipes() {

		Iterable<Recipe> iterableRecipe = recipeRepository.findAll();
		return StreamSupport.stream(iterableRecipe.spliterator(), false).sorted(ComparatorUtil.recipeComparator)
				.map(recipe -> {
					recipe.setIngredients(recipe.getIngredients().stream()
							.sorted(ComparatorUtil.ingredientComparator).collect(Collectors.toList()));
					return recipe;
				}).map(recipeMapper::convertRecipeToDTO).collect(Collectors.toList());
	}

	@Override
	public RecipeDTO getRecipesByIngredient(Long ingredientId) {
		if (ingredientId == null) {
			return null;
		}
		Ingredient ingredient = ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new EntityNotFoundException("The ingredient isn't found with this id" + ingredientId
						+ "  we can't find a recipe related to it !"));
		Recipe recipe = ingredient.getRecipe();
		List<Ingredient> unsortedIngredients = recipe.getIngredients();
		recipe.setIngredients(
				unsortedIngredients.stream().sorted(ComparatorUtil.ingredientComparator).collect(Collectors.toList()));
		return recipeMapper.convertRecipeToDTO(recipe);
	}

	@Override
	public List<RecipeDTO> getRecipeByName(String name) {
		List<RecipeDTO> recipes = new ArrayList<>();
		if (name == null) {
			return recipes;
		}
		return sortAndConvertRecipes(recipeRepository.findByName(name));

	}

	@Override
	public List<RecipeDTO> getRecipeByDescription(String description) {
		List<RecipeDTO> recipes = new ArrayList<>();
		if (description == null) {
			return recipes;
		}
		return sortAndConvertRecipes(recipeRepository.findByDescription(description));
	}

	@Override
	public List<RecipeDTO> getRecipeByCookingInsctructions(String cookingInsctructions) {
		List<RecipeDTO> recipes = new ArrayList<>();
		if (cookingInsctructions == null) {
			return recipes;
		}
		return sortAndConvertRecipes(recipeRepository.findByCookingInstructions(cookingInsctructions));
	}

	@Override
	public List<RecipeDTO> getRecipeByCreationDate(LocalDate creationDate) {
		List<RecipeDTO> recipes = new ArrayList<>();
		if (creationDate == null) {
			return recipes;
		}
		return sortAndConvertRecipes(recipeRepository.findByCreationDate(creationDate));
	}

	@Override
	public List<RecipeDTO> getRecipeByUpdateDate(LocalDate updateDate) {
		List<RecipeDTO> recipes = new ArrayList<>();
		if (updateDate == null) {
			return recipes;
		}
		return sortAndConvertRecipes(recipeRepository.findByUpdateDate(updateDate));
	}

	@Override
	public RecipeDTO addIngredientToRecipe(Long recipeId, Long ingredientId) {
		if (ingredientId == null) {
			return null;
		}
		Ingredient ingredient = ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new EntityNotFoundException("The ingredient with this id" + ingredientId
						+ " isn't found , we can't find a recipe related to it !"));
		Recipe recipe = getRecipeByIdOrThrowException(recipeId);
		if (recipe == null) {
			return null;
		}
		List<Ingredient> ingredients = recipe.getIngredients();
		Optional<Ingredient> optionalIngredient = ingredients.stream().filter(ing -> ing.getId() == ingredient.getId())
				.findAny();
		if (!optionalIngredient.isPresent()) {
			ingredient.setRecipe(recipe);
			ingredients.add(ingredient);
			recipe = recipeRepository.save(recipe);
		}
		List<Ingredient> unsortedIngredients = recipe.getIngredients();
		recipe.setIngredients(
				unsortedIngredients.stream().sorted(ComparatorUtil.ingredientComparator).collect(Collectors.toList()));
		return recipeMapper.convertRecipeToDTO(recipe);
	}

	@Override
	public RecipeDTO deleteIngredientFromRecipe(Long recipeId, Long ingredientId) {
		if (ingredientId == null) {
			return null;
		}
		Ingredient ingredient = ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new EntityNotFoundException("The ingredient with this id" + ingredientId
						+ " isn't found , we can't find a recipe related to it !"));
		Recipe recipe = getRecipeByIdOrThrowException(recipeId);
		if (recipe == null) {
			return null;
		}
		List<Ingredient> ingredients = recipe.getIngredients();
		Optional<Ingredient> optionalIngredient = ingredients.stream().filter(ing -> ing.getId() == ingredient.getId())
				.findAny();
		if (optionalIngredient.isPresent()) {
			ingredient.setRecipe(null);
			ingredients.remove(ingredient);
			recipe = recipeRepository.save(recipe);
			ingredientRepository.delete(ingredient);
		}
		return recipeMapper.convertRecipeToDTO(recipe);
	}

	private Recipe getRecipeByIdOrThrowException(Long recipeId) {
		if (recipeId == null) {
			return null;
		}
		return recipeRepository.findById(recipeId)
				.orElseThrow(() -> new EntityNotFoundException(" there is not recipe with the id :" + recipeId));
	}

	private List<RecipeDTO> sortAndConvertRecipes(List<Recipe> oldRecipes) {
		List<RecipeDTO> recipesDTO = new ArrayList<>();
		if (oldRecipes.isEmpty()) {
			return recipesDTO;
		}
		return oldRecipes.stream().sorted(ComparatorUtil.recipeComparator).map(recipe -> {
			recipe.getIngredients().addAll(recipe.getIngredients().stream().sorted(ComparatorUtil.ingredientComparator)
					.collect(Collectors.toList()));
			return recipe;
		}).map(recipeMapper::convertRecipeToDTO).collect(Collectors.toList());
	}

}
