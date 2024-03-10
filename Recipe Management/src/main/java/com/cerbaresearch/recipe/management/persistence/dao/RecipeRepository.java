package com.cerbaresearch.recipe.management.persistence.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cerbaresearch.recipe.management.persistence.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	List<Recipe> findByName(String name);

	List<Recipe> findByDescription(String description);

	List<Recipe> findByCookingInstructions(String cookingInsctructions);

	List<Recipe> findByCreationDate(LocalDate creationDate);

	List<Recipe> findByUpdateDate(LocalDate updateDate);
}
