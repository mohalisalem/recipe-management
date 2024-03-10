package com.cerbaresearch.recipe.management.utils;

import java.util.Comparator;

import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.persistence.model.Recipe;

public class ComparatorUtil {

	private ComparatorUtil() {
		super();
	}

	public static final Comparator<Ingredient> ingredientComparator = (first, second) -> first.getName()
			.compareTo(second.getName());

	public static final Comparator<Recipe> recipeComparator = (first, second) -> first.getName()
			.compareTo(second.getName());

}
