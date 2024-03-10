package com.cerbaresearch.recipe.management.web.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private String quantity;

	private LocalDate creationDate;

	private LocalDate updateDate;

	private Long recipeId;
}
