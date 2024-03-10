package com.cerbaresearch.recipe.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cerbaresearch.recipe.management.persistence.dao.IngredientRepository;
import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.service.impl.IngredientServiceImpl;
import com.cerbaresearch.recipe.management.web.dto.IngredientDTO;
import com.cerbaresearch.recipe.management.web.dto.mapper.IngredientMapper;
import com.cerbaresearch.recipe.management.web.dto.mapper.IngredientMapperImpl;
import com.cerbaresearch.recipe.management.web.exception.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

	@Mock
	private IngredientRepository ingredientRepository;

	@Spy
	private IngredientMapper ingredientMapper = new IngredientMapperImpl();

	@InjectMocks
	private IngredientServiceImpl ingredientService;

	@Test
	void testGetIngredientById() {
		Long ingredientId = 1L;
		Ingredient ingredient = new Ingredient("TestIngredient", "TestQuantity");
		ingredient.setCreationDate(LocalDate.now());
		when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

		IngredientDTO expectedDto = new IngredientDTO();
		expectedDto.setName("TestIngredient");
		expectedDto.setQuantity("TestQuantity");

		when(ingredientMapper.convertIngredientToDTO(ingredient)).thenReturn(expectedDto);
		IngredientDTO resultDto = ingredientService.getIngredientById(ingredientId);
		assertEquals(expectedDto, resultDto);
	}

	@Test
	void testetIngredientByIdReturnsNullIfIdNull() {
		Long ingredientId = null;
		IngredientDTO resultDto = ingredientService.getIngredientById(ingredientId);
		assertNull(resultDto);
	}

	@Test
	void testetIngredientByIdthrowsExceptionIfIdWrong() {
		Long ingredientId = 2156L;
		Ingredient ingredient = new Ingredient("TestIngredient", "TestQuantity");
		ingredient.setCreationDate(LocalDate.now());
		when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> {
			ingredientService.getIngredientById(ingredientId);
		});
	}

	@Test
	void testAddIngredient() {
		String name = "TestIngredient";
		String quantity = "TestQuantity";
		Long id = 123L;
		LocalDate localDate = LocalDate.now();

		Ingredient ingredient = new Ingredient(name, quantity);
		ingredient.setCreationDate(localDate);
		ingredient.setId(id);

		when(ingredientRepository.save(any())).thenReturn(ingredient);
		IngredientDTO resultDto = ingredientService.addIngredient(name, quantity);

		IngredientDTO expectedDto = IngredientDTO.builder().name(name).quantity(quantity).id(id).creationDate(localDate)
				.build();

		assertEquals(expectedDto, resultDto);
	}

	@Test
	void testAddIngredientReturnsNullIfNameNull() {
		String name = null;
		String quantity = "TestQuantity";

		Ingredient ingredient = new Ingredient(name, quantity);
		ingredient.setCreationDate(LocalDate.now());
		IngredientDTO resultDto = ingredientService.addIngredient(name, quantity);

		assertNull(resultDto);
	}

	@Test
	void testEditIngredient() {
		Long ingredientId = 1L;
		String name = "TestIngredient";
		String quantity = "TestQuantity";

		Ingredient ingredient = new Ingredient(name, quantity);
		ingredient.setCreationDate(LocalDate.now());
		when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

		String updatedName = "UpdatedIngredient";
		String updatedQuantity = "UpdatedQuantity";

		ingredient.setName(updatedName);
		ingredient.setQuantity(updatedQuantity);
		ingredient.setUpdateDate(LocalDate.now());

		when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

		IngredientDTO expectedDto = new IngredientDTO();
		expectedDto.setName(updatedName);
		expectedDto.setQuantity(updatedQuantity);

		when(ingredientMapper.convertIngredientToDTO(ingredient)).thenReturn(expectedDto);

		IngredientDTO resultDto = ingredientService.editIngredient(ingredientId, updatedName, updatedQuantity);

		assertEquals(expectedDto, resultDto);
	}

	@Test
	void testDeleteIngredient() {
		Long ingredientId = 1L;
		ingredientService.deleteIngredient(ingredientId);
		verify(ingredientRepository, times(1)).deleteById(ingredientId);
	}

	@Test
	void testGetAllIngredients() {
		List<Ingredient> ingredients = Arrays.asList(new Ingredient("Ingredient1", "Quantity1"),
				new Ingredient("Ingredient2", "Quantity2"));
		when(ingredientRepository.findAll()).thenReturn(ingredients);

		List<IngredientDTO> expectedDTOs = Arrays.asList(
				IngredientDTO.builder().name("Ingredient1").quantity("Quantity1").build(),
				IngredientDTO.builder().name("Ingredient2").quantity("Quantity2").build());
		List<IngredientDTO> resultDTOs = ingredientService.getAllIngredients();

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetIngredientsByRecipe() {
		Long recipeId = 1L;
		List<Ingredient> ingredients = Arrays.asList(new Ingredient("Ingredient1", "Quantity1"),
				new Ingredient("Ingredient2", "Quantity2"));
		when(ingredientRepository.findByRecipe_Id(recipeId)).thenReturn(ingredients);

		List<IngredientDTO> expectedDTOs = Arrays.asList(
				IngredientDTO.builder().name("Ingredient1").quantity("Quantity1").build(),
				IngredientDTO.builder().name("Ingredient2").quantity("Quantity2").build());

		List<IngredientDTO> resultDTOs = ingredientService.getIngredientsByRecipe(recipeId);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetIngredientByName() {
		String name = "TestIngredient";
		List<Ingredient> ingredients = Arrays.asList(new Ingredient(name, "Quantity1"),
				new Ingredient(name, "Quantity2"));
		when(ingredientRepository.findByName(name)).thenReturn(ingredients);

		List<IngredientDTO> expectedDTOs = Arrays.asList(
				IngredientDTO.builder().name(name).quantity("Quantity1").build(),
				IngredientDTO.builder().name(name).quantity("Quantity2").build());

		List<IngredientDTO> resultDTOs = ingredientService.getIngredientByName(name);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetIngredientByQuantity() {
		String quantity = "TestQuantity";
		List<Ingredient> ingredients = Arrays.asList(new Ingredient("Ingredient1", quantity),
				new Ingredient("Ingredient2", quantity));
		when(ingredientRepository.findByQuantity(quantity)).thenReturn(ingredients);

		List<IngredientDTO> expectedDTOs = Arrays.asList(
				IngredientDTO.builder().name("Ingredient1").quantity(quantity).build(),
				IngredientDTO.builder().name("Ingredient2").quantity(quantity).build());

		List<IngredientDTO> resultDTOs = ingredientService.getIngredientByQuantity(quantity);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetIngredientByCreationDate() {
		LocalDate creationDate = LocalDate.now();
		List<Ingredient> ingredients = Arrays.asList(new Ingredient("Ingredient1", "Quantity1"),
				new Ingredient("Ingredient2", "Quantity2"));
		when(ingredientRepository.findByCreationDate(creationDate)).thenReturn(ingredients);

		List<IngredientDTO> expectedDTOs = Arrays.asList(
				IngredientDTO.builder().name("Ingredient1").quantity("Quantity1").build(),
				IngredientDTO.builder().name("Ingredient2").quantity("Quantity2").build());

		List<IngredientDTO> resultDTOs = ingredientService.getIngredientByCreationDate(creationDate);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetIngredientByUpdateDate() {
		LocalDate updateDate = LocalDate.now();
		List<Ingredient> ingredients = Arrays.asList(new Ingredient("Ingredient1", "Quantity1"),
				new Ingredient("Ingredient2", "Quantity2"));
		when(ingredientRepository.findByUpdateDate(updateDate)).thenReturn(ingredients);

		List<IngredientDTO> expectedDTOs = Arrays.asList(
				IngredientDTO.builder().name("Ingredient1").quantity("Quantity1").build(),
				IngredientDTO.builder().name("Ingredient2").quantity("Quantity2").build());

		List<IngredientDTO> resultDTOs = ingredientService.getIngredientByUpdateDate(updateDate);

		assertEquals(expectedDTOs, resultDTOs);
	}

	@Test
	void testGetIngredientByNameAndQuantity() {
		String name = "TestIngredient";
		String quantity = "TestQuantity";
		Optional<Ingredient> optionalIngredient = Optional.of(new Ingredient(name, quantity));
		when(ingredientRepository.findByNameAndQuantity(name, quantity)).thenReturn(optionalIngredient);

		IngredientDTO expectedDTO = IngredientDTO.builder().name(name).quantity(quantity).build();
		when(ingredientMapper.convertIngredientToDTO(optionalIngredient.get())).thenReturn(expectedDTO);

		IngredientDTO resultDTO = ingredientService.getIngredientByNameAndQuantity(name, quantity);

		assertEquals(expectedDTO, resultDTO);
	}

}
