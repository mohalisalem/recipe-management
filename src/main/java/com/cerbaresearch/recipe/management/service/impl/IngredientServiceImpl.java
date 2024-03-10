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
import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.service.IngredientService;
import com.cerbaresearch.recipe.management.utils.ComparatorUtil;
import com.cerbaresearch.recipe.management.web.dto.IngredientDTO;
import com.cerbaresearch.recipe.management.web.dto.mapper.IngredientMapper;
import com.cerbaresearch.recipe.management.web.exception.EntityNotFoundException;

@Service
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private IngredientMapper ingredientMapper;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Override
	public IngredientDTO getIngredientById(Long ingredientId) {
		if (ingredientId == null) {
			return null;
		}
		Ingredient ingredient = getIngredientByIdOrThrowException(ingredientId);

		return ingredientMapper.convertIngredientToDTO(ingredient);

	}

	@Override
	public IngredientDTO addIngredient(String name, String quantity) {
		if (name == null || quantity == null) {
			return null;
		}
		Ingredient ingredient = new Ingredient(name, quantity);
		ingredient.setCreationDate(LocalDate.now()	);
		return ingredientMapper.convertIngredientToDTO(ingredientRepository.save(ingredient));
	}

	@Override
	public IngredientDTO editIngredient(Long ingredientId, String name, String quantity) {
		if (name == null || quantity == null || ingredientId == null) {
			return null;
		}
		Ingredient ingredient = getIngredientByIdOrThrowException(ingredientId);

		ingredient.setName(name);
		ingredient.setQuantity(quantity);
		ingredient.setUpdateDate(LocalDate.now());
		return ingredientMapper.convertIngredientToDTO(ingredientRepository.save(ingredient));
	}

	@Override
	public void deleteIngredient(Long ingredientId) {
		ingredientRepository.deleteById(ingredientId);
	}

	@Override
	public List<IngredientDTO> getAllIngredients() {
		Iterable<Ingredient> iterableIngredient = ingredientRepository.findAll();
		return StreamSupport.stream(iterableIngredient.spliterator(), false).sorted(ComparatorUtil.ingredientComparator)
				.map(ingredientMapper::convertIngredientToDTO).collect(Collectors.toList());
	}

	@Override
	public List<IngredientDTO> getIngredientsByRecipe(Long recipeId) {
		List<IngredientDTO> recipeIngredients = new ArrayList<>();
		if (recipeId == null) {
			return recipeIngredients;
		}
		return ingredientRepository.findByRecipe_Id(recipeId).stream().sorted(ComparatorUtil.ingredientComparator)
				.map(ingredientMapper::convertIngredientToDTO).collect(Collectors.toList());
	}

	@Override
	public List<IngredientDTO> getIngredientByName(String name) {
		List<IngredientDTO> recipeIngredients = new ArrayList<>();
		if (name == null) {
			return recipeIngredients;
		}
		return ingredientRepository.findByName(name).stream().sorted(ComparatorUtil.ingredientComparator)
				.map(ingredientMapper::convertIngredientToDTO).collect(Collectors.toList());
	}

	@Override
	public List<IngredientDTO> getIngredientByQuantity(String quantity) {
		List<IngredientDTO> recipeIngredients = new ArrayList<>();
		if (quantity == null) {
			return recipeIngredients;
		}
		return ingredientRepository.findByQuantity(quantity).stream().sorted(ComparatorUtil.ingredientComparator)
				.map(ingredientMapper::convertIngredientToDTO).collect(Collectors.toList());
	}

	@Override
	public List<IngredientDTO> getIngredientByCreationDate(LocalDate creationDate) {
		List<IngredientDTO> recipeIngredients = new ArrayList<>();
		if (creationDate == null) {
			return recipeIngredients;
		}
		return ingredientRepository.findByCreationDate(creationDate).stream()
				.sorted(ComparatorUtil.ingredientComparator).map(ingredientMapper::convertIngredientToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<IngredientDTO> getIngredientByUpdateDate(LocalDate updateDate) {
		List<IngredientDTO> recipeIngredients = new ArrayList<>();
		if (updateDate == null) {
			return recipeIngredients;
		}
		return ingredientRepository.findByUpdateDate(updateDate).stream().sorted(ComparatorUtil.ingredientComparator)
				.map(ingredientMapper::convertIngredientToDTO).collect(Collectors.toList());
	}

	@Override
	public IngredientDTO getIngredientByNameAndQuantity(String name, String quantity) {
		if (name == null || quantity == null) {
			return null;
		}
		Optional<Ingredient> optionalIngredient = ingredientRepository.findByNameAndQuantity(name, quantity);
		if (optionalIngredient.isPresent()) {
			return ingredientMapper.convertIngredientToDTO(optionalIngredient.get());
		}
		return null;
		
	}

	/**
	 * Same code used multiple times, getting ingredient by id or throw not found
	 * exception
	 * 
	 * @param ingredientId
	 * @return
	 */
	private Ingredient getIngredientByIdOrThrowException(Long ingredientId) {
		if (ingredientId == null) {
			return null;
		}
		return ingredientRepository.findById(ingredientId).orElseThrow(
				() -> new EntityNotFoundException(" there is not ingredient with the id :" + ingredientId));
	}


}
