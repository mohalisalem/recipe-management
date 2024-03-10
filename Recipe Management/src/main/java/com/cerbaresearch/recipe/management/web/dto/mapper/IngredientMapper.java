package com.cerbaresearch.recipe.management.web.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.web.dto.IngredientDTO;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
	
	IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

	Ingredient convertIngredientToEntity(IngredientDTO ingredientDTO);

	@Mapping(target = "recipeId", source = "recipe.id")
	IngredientDTO convertIngredientToDTO(Ingredient ingredient);

}
