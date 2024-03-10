package com.cerbaresearch.recipe.management.web.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cerbaresearch.recipe.management.persistence.model.Recipe;
import com.cerbaresearch.recipe.management.web.dto.RecipeDTO;

@Mapper(uses = {IngredientMapper.class}, componentModel = "spring")
public interface RecipeMapper {
	
	RecipeMapper  INSTANCE = Mappers.getMapper(RecipeMapper.class);

	Recipe convertRecipeToEntity(RecipeDTO recipeDTO);
	
	RecipeDTO convertRecipeToDTO(Recipe recipe);
}
