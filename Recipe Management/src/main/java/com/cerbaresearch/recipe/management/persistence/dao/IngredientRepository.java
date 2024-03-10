package com.cerbaresearch.recipe.management.persistence.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cerbaresearch.recipe.management.persistence.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

	List<Ingredient> findByName(String name);

	List<Ingredient> findByQuantity(String quantity);

	Optional<Ingredient> findByNameAndQuantity(String name, String quantity);

	List<Ingredient> findByCreationDate(LocalDate creationDate);

	List<Ingredient> findByUpdateDate(LocalDate updateDate);

	List<Ingredient> findByRecipe_Id(Long recipeId);

}
