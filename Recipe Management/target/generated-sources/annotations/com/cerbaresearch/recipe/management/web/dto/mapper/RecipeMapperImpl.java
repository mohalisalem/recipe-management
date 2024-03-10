package com.cerbaresearch.recipe.management.web.dto.mapper;

import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.persistence.model.Recipe;
import com.cerbaresearch.recipe.management.web.dto.IngredientDTO;
import com.cerbaresearch.recipe.management.web.dto.RecipeDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-09T20:39:52+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.22 (Ubuntu)"
)
@Component
public class RecipeMapperImpl implements RecipeMapper {

    @Autowired
    private IngredientMapper ingredientMapper;

    @Override
    public Recipe convertRecipeToEntity(RecipeDTO recipeDTO) {
        if ( recipeDTO == null ) {
            return null;
        }

        Recipe recipe = new Recipe();

        recipe.setId( recipeDTO.getId() );
        recipe.setName( recipeDTO.getName() );
        recipe.setDescription( recipeDTO.getDescription() );
        recipe.setCookingInstructions( recipeDTO.getCookingInstructions() );
        recipe.setIngredients( ingredientDTOListToIngredientList( recipeDTO.getIngredients() ) );
        recipe.setCreationDate( recipeDTO.getCreationDate() );
        recipe.setUpdateDate( recipeDTO.getUpdateDate() );

        return recipe;
    }

    @Override
    public RecipeDTO convertRecipeToDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setId( recipe.getId() );
        recipeDTO.setName( recipe.getName() );
        recipeDTO.setDescription( recipe.getDescription() );
        recipeDTO.setCookingInstructions( recipe.getCookingInstructions() );
        recipeDTO.setCreationDate( recipe.getCreationDate() );
        recipeDTO.setUpdateDate( recipe.getUpdateDate() );
        recipeDTO.setIngredients( ingredientListToIngredientDTOList( recipe.getIngredients() ) );

        return recipeDTO;
    }

    protected List<Ingredient> ingredientDTOListToIngredientList(List<IngredientDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Ingredient> list1 = new ArrayList<Ingredient>( list.size() );
        for ( IngredientDTO ingredientDTO : list ) {
            list1.add( ingredientMapper.convertIngredientToEntity( ingredientDTO ) );
        }

        return list1;
    }

    protected List<IngredientDTO> ingredientListToIngredientDTOList(List<Ingredient> list) {
        if ( list == null ) {
            return null;
        }

        List<IngredientDTO> list1 = new ArrayList<IngredientDTO>( list.size() );
        for ( Ingredient ingredient : list ) {
            list1.add( ingredientMapper.convertIngredientToDTO( ingredient ) );
        }

        return list1;
    }
}
