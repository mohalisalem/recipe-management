package com.cerbaresearch.recipe.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cerbaresearch.recipe.management.persistence.dao.IngredientRepository;
import com.cerbaresearch.recipe.management.persistence.dao.RecipeRepository;
import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.persistence.model.Recipe;
import com.cerbaresearch.recipe.management.service.impl.RecipeServiceImpl;
import com.cerbaresearch.recipe.management.web.dto.IngredientDTO;
import com.cerbaresearch.recipe.management.web.dto.RecipeDTO;
import com.cerbaresearch.recipe.management.web.dto.mapper.IngredientMapper;
import com.cerbaresearch.recipe.management.web.dto.mapper.RecipeMapper;
import com.cerbaresearch.recipe.management.web.exception.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

	@Mock
	private RecipeRepository recipeRepository;

	@Mock
	private IngredientRepository ingredientRepository;

	@Mock
	private RecipeMapper recipeMapper;

	@Mock
	private IngredientMapper ingredientMapper;

	@InjectMocks
	private RecipeServiceImpl recipeService;

	@Test
	 void testGetRecipeById() {
		Long recipeId = 1L;
		Recipe recipe = new Recipe();
		recipe.setId(recipeId);
		when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

		RecipeDTO expectedDto = new RecipeDTO();
		expectedDto.setId(recipeId);

		when(recipeMapper.convertRecipeToDTO(recipe)).thenReturn(expectedDto);

		RecipeDTO resultDto = recipeService.getRecipeById(recipeId);
		assertEquals(expectedDto, resultDto);
	}
	
	@Test
	 void testGetRecipeByIdReturnsNullIfIdNull() {
		Long recipeId = null;
		
		RecipeDTO resultDto = recipeService.getRecipeById(recipeId);
		assertNull(resultDto);
	}
	
	@Test
	 void testGetRecipeByIdThrowsExcpetionIfIdWorng() {
		Long recipeId = 1236L;
		when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> {
			recipeService.getRecipeById(recipeId);
		});
	}

	@Test
	void testAddRecipe() {
		String name = "TestRecipe";
		String description = "TestDescription";
		String cookingInstructions = "TestInstructions";

		Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setDescription(description);
		recipe.setCookingInstructions(cookingInstructions);
		recipe.setCreationDate(LocalDate.now());

		RecipeDTO expectedDto = new RecipeDTO();
		expectedDto.setName(name);
		expectedDto.setDescription(description);
		expectedDto.setCookingInstructions(cookingInstructions);

		when(recipeRepository.save(any())).thenReturn(recipe);
		when(recipeMapper.convertRecipeToDTO(recipe)).thenReturn(expectedDto);

		RecipeDTO resultDto = recipeService.addRecipe(name, description, cookingInstructions);

		assertEquals(expectedDto, resultDto);
	}

	@Test
	 void testEditRecipe() {
		Long recipeId = 1L;
		String name = "UpdatedRecipe";
		String description = "UpdatedDescription";
		String cookingInstructions = "UpdatedInstructions";

		Recipe recipe = new Recipe();
		recipe.setId(recipeId);
		when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

		recipe.setName(name);
		recipe.setDescription(description);
		recipe.setCookingInstructions(cookingInstructions);
		recipe.setUpdateDate(LocalDate.now());

		when(recipeRepository.save(recipe)).thenReturn(recipe);

		RecipeDTO expectedDto = new RecipeDTO();
		expectedDto.setId(recipeId);
		expectedDto.setName(name);
		expectedDto.setDescription(description);
		expectedDto.setCookingInstructions(cookingInstructions);

		when(recipeMapper.convertRecipeToDTO(recipe)).thenReturn(expectedDto);

		RecipeDTO resultDto = recipeService.editRecipe(recipeId, name, description, cookingInstructions);

		assertEquals(expectedDto, resultDto);
	}

	@Test
	 void testDeleteRecipe() {
		Long recipeId = 1L;
		Recipe recipe = new Recipe();
		recipe.setId(recipeId);
		List<Ingredient> ingredients = new ArrayList<>();
		recipe.setIngredients(ingredients);
		when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
		
		recipeService.deleteRecipe(recipeId);

		for (Ingredient ingredient : ingredients) {
			verify(ingredientRepository, times(1)).delete(ingredient);
		}

		verify(recipeRepository, times(1)).deleteById(recipeId);
	}

	@Test
	 void testGetRecipesByIngredient() {
		Long ingredientId = 1L;
		Long recipeId = 2L;
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ingredientId);
		Recipe recipe = new Recipe();
		recipe.setIngredients(Arrays.asList(ingredient));
		recipe.setId(recipeId);
		ingredient.setRecipe(recipe);

		when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

		RecipeDTO expectedDto = new RecipeDTO();
		expectedDto.setId(recipe.getId());

		IngredientDTO ingredientDTO = IngredientDTO.builder().id(ingredientId).recipeId(recipeId).build();
		List<IngredientDTO> ingredientsDTO = Arrays.asList(ingredientDTO);
		expectedDto.setIngredients(ingredientsDTO);
		
		when(recipeMapper.convertRecipeToDTO(recipe)).thenReturn(expectedDto);
		RecipeDTO resultDto = recipeService.getRecipesByIngredient(ingredientId);
		
		assertEquals(expectedDto, resultDto);
	}

	@Test
	void testAddIngredientToRecipe() {
		Long recipeId = 1L;
		Long ingredientId = 2L;

		Recipe recipe = new Recipe();
		recipe.setId(recipeId);

		Ingredient ingredient = new Ingredient();
		ingredient.setId(ingredientId);
		ingredient.setRecipe(recipe);

		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(ingredient);
		recipe.setIngredients(ingredients);

		when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
		when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

		IngredientDTO ingredientDTO = IngredientDTO.builder().id(ingredientId).recipeId(recipeId).build();
		List<IngredientDTO> ingredientsDTO = new ArrayList<>();
		ingredientsDTO.add(ingredientDTO);

		RecipeDTO expectedDto = new RecipeDTO();
		expectedDto.setId(recipeId);
		expectedDto.setIngredients(ingredientsDTO);

		when(recipeMapper.convertRecipeToDTO(recipe)).thenReturn(expectedDto);
		RecipeDTO resultDto = recipeService.addIngredientToRecipe(recipeId, ingredientId);
		assertEquals(expectedDto, resultDto);
	}

	@Test
	void testDeleteIngredientFromRecipe() {
		Long recipeId = 1L;
		Long ingredientId = 2L;
		Recipe recipe = new Recipe();
		Recipe recipe1 = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ingredientId);
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(ingredient);
		recipe.setIngredients(ingredients);
		recipe.setId(recipeId);
		recipe1.setId(recipeId);

		when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
		when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
		when(recipeRepository.save(recipe)).thenReturn(recipe1);

		RecipeDTO expectedDto = new RecipeDTO();
		expectedDto.setId(recipeId);

		when(recipeMapper.convertRecipeToDTO(recipe1)).thenReturn(expectedDto);

		RecipeDTO resultDto = recipeService.deleteIngredientFromRecipe(recipeId, ingredientId);

		assertEquals(expectedDto, resultDto);
	}

	@Test
	void testGetAllRecipes() {
		Recipe recipe1 = new Recipe("Recipe1", "Description1", "Instructions1");
		Recipe recipe2 = new Recipe("Recipe2", "Description2", "Instructions2");
		List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

		when(recipeRepository.findAll()).thenReturn(recipes);

		RecipeDTO recipeDTO1 = RecipeDTO.builder().name("Recipe1").description("Description1")
				.cookingInstructions("Instructions1").build();
		RecipeDTO recipeDTO2 = RecipeDTO.builder().name("Recipe2").description("Description2")
				.cookingInstructions("Instructions2").build();

		List<RecipeDTO> expectedDTOs = Arrays.asList(recipeDTO1, recipeDTO2);

		when(recipeMapper.convertRecipeToDTO(recipe1)).thenReturn(recipeDTO1);
		when(recipeMapper.convertRecipeToDTO(recipe2)).thenReturn(recipeDTO2);

		List<RecipeDTO> resultDTOs = recipeService.getAllRecipes();

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetRecipeByName() {
		String name = "TestRecipe";

		Recipe recipe1 = new Recipe(name, "Description1", "Instructions1");
		Recipe recipe2 = new Recipe(name, "Description2", "Instructions2");
		List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
		when(recipeRepository.findByName(name)).thenReturn(recipes);

		RecipeDTO recipeDTO1 = RecipeDTO.builder().name(name).description("Description1")
				.cookingInstructions("Instructions1").build();
		RecipeDTO recipeDTO2 = RecipeDTO.builder().name(name).description("Description2")
				.cookingInstructions("Instructions2").build();

		List<RecipeDTO> expectedDTOs = Arrays.asList(recipeDTO1, recipeDTO2);

		when(recipeMapper.convertRecipeToDTO(recipe1)).thenReturn(recipeDTO1);
		when(recipeMapper.convertRecipeToDTO(recipe2)).thenReturn(recipeDTO2);

		List<RecipeDTO> resultDTOs = recipeService.getRecipeByName(name);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetRecipeByDescription() {
		String description = "TestDescription";
		Recipe recipe1 = new Recipe("Recipe1", description, "Instructions1");
		Recipe recipe2 = new Recipe("Recipe2", description, "Instructions2");

		List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
		when(recipeRepository.findByDescription(description)).thenReturn(recipes);

		RecipeDTO recipeDTO1 = RecipeDTO.builder().name("Recipe1").description(description)
				.cookingInstructions("Instructions1").build();
		RecipeDTO recipeDTO2 = RecipeDTO.builder().name("Recipe2").description(description)
				.cookingInstructions("Instructions2").build();

		List<RecipeDTO> expectedDTOs = Arrays.asList(recipeDTO1, recipeDTO2);

		when(recipeMapper.convertRecipeToDTO(recipe1)).thenReturn(recipeDTO1);
		when(recipeMapper.convertRecipeToDTO(recipe2)).thenReturn(recipeDTO2);

		List<RecipeDTO> resultDTOs = recipeService.getRecipeByDescription(description);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetRecipeByCookingInsctructions() {
		String cookingInstructions = "TestInstructions";
		Recipe recipe1 = new Recipe("Recipe1", "Description1", cookingInstructions);
		Recipe recipe2 = new Recipe("Recipe2", "Description2", cookingInstructions);
		List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
		when(recipeRepository.findByCookingInstructions(cookingInstructions)).thenReturn(recipes);

		RecipeDTO recipeDTO1 = RecipeDTO.builder().name("Recipe1").description("Description1")
				.cookingInstructions(cookingInstructions).build();
		RecipeDTO recipeDTO2 = RecipeDTO.builder().name("Recipe2").description("Description2")
				.cookingInstructions(cookingInstructions).build();

		List<RecipeDTO> expectedDTOs = Arrays.asList(recipeDTO1, recipeDTO2);

		when(recipeMapper.convertRecipeToDTO(recipe1)).thenReturn(recipeDTO1);
		when(recipeMapper.convertRecipeToDTO(recipe2)).thenReturn(recipeDTO2);

		List<RecipeDTO> resultDTOs = recipeService.getRecipeByCookingInsctructions(cookingInstructions);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetRecipeByCreationDate() {
		LocalDate creationDate = LocalDate.now();

		Recipe recipe1 = new Recipe("Recipe1", "Description1", "Instructions1");
		Recipe recipe2 = new Recipe("Recipe2", "Description2", "Instructions2");
		recipe1.setCreationDate(creationDate);
		recipe2.setCreationDate(creationDate);

		List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

		when(recipeRepository.findByCreationDate(creationDate)).thenReturn(recipes);
		RecipeDTO recipeDTO1 = RecipeDTO.builder().name("Recipe1").description("Description1")
				.cookingInstructions("Instructions1").creationDate(creationDate).build();
		RecipeDTO recipeDTO2 = RecipeDTO.builder().name("Recipe2").description("Description2")
				.cookingInstructions("Instructions2").creationDate(creationDate).build();

		List<RecipeDTO> expectedDTOs = Arrays.asList(recipeDTO1, recipeDTO2);
		when(recipeMapper.convertRecipeToDTO(recipe1)).thenReturn(recipeDTO1);
		when(recipeMapper.convertRecipeToDTO(recipe2)).thenReturn(recipeDTO2);
		List<RecipeDTO> resultDTOs = recipeService.getRecipeByCreationDate(creationDate);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	 void testGetRecipeByUpdateDate() {
		LocalDate updateDate = LocalDate.now();

		Recipe recipe1 = new Recipe("Recipe1", "Description1", "Instructions1");
		Recipe recipe2 = new Recipe("Recipe2", "Description2", "Instructions2");
		recipe1.setUpdateDate(updateDate);
		recipe2.setUpdateDate(updateDate);

		List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

		when(recipeRepository.findByUpdateDate(updateDate)).thenReturn(recipes);

		RecipeDTO recipeDTO1 = RecipeDTO.builder().name("Recipe1").description("Description1")
				.cookingInstructions("Instructions1").updateDate(updateDate).build();
		RecipeDTO recipeDTO2 = RecipeDTO.builder().name("Recipe2").description("Description2")
				.cookingInstructions("Instructions2").updateDate(updateDate).build();

		List<RecipeDTO> expectedDTOs = Arrays.asList(recipeDTO1, recipeDTO2);
		when(recipeMapper.convertRecipeToDTO(recipe1)).thenReturn(recipeDTO1);
		when(recipeMapper.convertRecipeToDTO(recipe2)).thenReturn(recipeDTO2);

		List<RecipeDTO> resultDTOs = recipeService.getRecipeByUpdateDate(updateDate);

		assertEquals(expectedDTOs, resultDTOs);
	}
}
