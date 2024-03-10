package com.cerbaresearch.recipe.management.web.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private String description;

	private String cookingInstructions;

	private LocalDate creationDate;

	private LocalDate updateDate;

	private List<IngredientDTO> ingredients;

}
