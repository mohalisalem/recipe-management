package com.cerbaresearch.recipe.management.web.dto.mapper;

import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.persistence.model.Recipe;
import com.cerbaresearch.recipe.management.web.dto.IngredientDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-09T20:39:52+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.22 (Ubuntu)"
)
@Component
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public Ingredient convertIngredientToEntity(IngredientDTO ingredientDTO) {
        if ( ingredientDTO == null ) {
            return null;
        }

        Ingredient ingredient = new Ingredient();

        ingredient.setId( ingredientDTO.getId() );
        ingredient.setName( ingredientDTO.getName() );
        ingredient.setQuantity( ingredientDTO.getQuantity() );
        ingredient.setCreationDate( ingredientDTO.getCreationDate() );
        ingredient.setUpdateDate( ingredientDTO.getUpdateDate() );

        return ingredient;
    }

    @Override
    public IngredientDTO convertIngredientToDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO ingredientDTO = new IngredientDTO();

        ingredientDTO.setRecipeId( ingredientRecipeId( ingredient ) );
        ingredientDTO.setId( ingredient.getId() );
        ingredientDTO.setName( ingredient.getName() );
        ingredientDTO.setQuantity( ingredient.getQuantity() );
        ingredientDTO.setCreationDate( ingredient.getCreationDate() );
        ingredientDTO.setUpdateDate( ingredient.getUpdateDate() );

        return ingredientDTO;
    }

    private Long ingredientRecipeId(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }
        Recipe recipe = ingredient.getRecipe();
        if ( recipe == null ) {
            return null;
        }
        long id = recipe.getId();
        return id;
    }
}
