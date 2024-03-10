package com.cerbaresearch.recipe.management.bootstrap;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cerbaresearch.recipe.management.persistence.dao.IngredientRepository;
import com.cerbaresearch.recipe.management.persistence.dao.RecipeRepository;
import com.cerbaresearch.recipe.management.persistence.model.Ingredient;
import com.cerbaresearch.recipe.management.persistence.model.Recipe;

@Component
public class BootStrapData implements CommandLineRunner {

	private final IngredientRepository ingredientRepository;
	private final RecipeRepository recipeRepository;

	public BootStrapData(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
		super();
		this.ingredientRepository = ingredientRepository;
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		if (ingredientRepository.count() == 0L) {

			addMuffins();
			addCarrotCupcakes();
		}

	}

	private void addMuffins() {
		LocalDate localDate = LocalDate.now();
		
		Ingredient sunflower = new Ingredient("sunflower", "100ml");
		Ingredient flour = new Ingredient("self-raising flour", "250g ");
		Ingredient baking = new Ingredient("baking powder", "1 tsp");
		Ingredient cheese = new Ingredient("grated cheese", "150g ");
		Ingredient eggs = new Ingredient("eggs", "2");
		Ingredient milk = new Ingredient("milk", "250ml");
		Ingredient tomatoes = new Ingredient("cherry tomatoes", "200g");
		Ingredient bacon = new Ingredient("smoked bacon", "200g");

		Recipe muffins = new Recipe("Bacon and tomato muffins",
				"This savoury bake is the best way to use up any bacon and tomatoes in your fridge. Make these vegetarian by replacing the bacon with sweetcorn! ",
				"1.Preheat the oven to gas 6, 200˚C, fan 180˚C.\n" + "\n"
						+ "2. Brush the inside of a 12-hole muffin tin well with 1 tbsp oil.\n" + "\n"
						+ "3. Mix the flour, baking powder and cheese in a bowl.\n" + "\n"
						+ "4. Mix all the wet ingredients in a bowl or jug and stir until well mixed.\n" + "\n"
						+ "5. Pour the wet mixture into the dry and mix until totally combined. Don't overmix, but do check that there’s no flour pockets left.\n"
						+ "\n" + "6. Stir in the cherry tomatoes and bacon.\n" + "\n"
						+ "7. Scoop into the 12 muffin holes and bake for 20-25 mins or until golden brown.");

		List<Ingredient> muffinsIngredient = Arrays.asList(sunflower, flour, baking, cheese, eggs, milk, tomatoes,
				bacon);
		muffins.setIngredients(muffinsIngredient);
		muffins.setCreationDate(localDate);

		sunflower.setRecipe(muffins);
		sunflower.setCreationDate(localDate);
		flour.setRecipe(muffins);
		flour.setCreationDate(localDate);
		baking.setRecipe(muffins);
		baking.setCreationDate(localDate);
		cheese.setRecipe(muffins);
		cheese.setCreationDate(localDate);
		eggs.setRecipe(muffins);
		eggs.setCreationDate(localDate);
		milk.setRecipe(muffins);
		milk.setCreationDate(localDate);
		tomatoes.setRecipe(muffins);
		tomatoes.setCreationDate(localDate);
		bacon.setRecipe(muffins);
		bacon.setCreationDate(localDate);

		ingredientRepository.save(sunflower);
		ingredientRepository.save(flour);
		ingredientRepository.save(baking);
		ingredientRepository.save(cheese);
		ingredientRepository.save(eggs);
		ingredientRepository.save(milk);
		ingredientRepository.save(tomatoes);
		ingredientRepository.save(bacon);

		recipeRepository.save(muffins);
	}

	private void addCarrotCupcakes() {
		LocalDate localDate = LocalDate.now();
		
		Ingredient flour = new Ingredient("plain flour", "250g");
		Ingredient baking = new Ingredient("baking powder", "2 tsp");
		Ingredient cheese = new Ingredient("reduced-fat salad cheese", "100g");
		Ingredient carrots = new Ingredient("carrots", "250g");
		Ingredient onions = new Ingredient("spring onions", "3");
		Ingredient eggs = new Ingredient("eggs", "3");
		Ingredient yogurt = new Ingredient("low-fat natural yogurt", "150g");
		Ingredient oil = new Ingredient("sunflower oil", "3 tbsp");

		Recipe cupcakes = new Recipe();
		cupcakes.setName("Savoury carrot cupcakes");
		cupcakes.setDescription(
				"Make the most of vibrant carrots with this simple veg-patch bake. A savoury take on classic carrot cake with the help of a silky cream cheese, you won't regret making these easy snacks.");
		cupcakes.setCookingInstructions("\n" + "\n"
				+ "    Preheat the oven to gas 4, 180°C, fan 160°C. Line a 12-hole muffin tin with paper cases.\n"
				+ "\n"
				+ "    Mix the flour and baking powder in a large bowl. Stir in the salad cheese, grated carrot and spring onions. In a separate bowl, beat the eggs with the yogurt and oil until combined; pour into the bowl with the dry ingredients. Gently mix.\n"
				+ "\n"
				+ "    Divide between the paper cases, then bake for 20 mins until risen and springy to the touch. Allow the cakes to cool for 5 mins, then place on a wire rack to cool completely.\n"
				+ "\n"
				+ "    Meanwhile, mix the sugar and vinegar in a bowl and add the carrot ribbons. Set aside for 15 mins.\n"
				+ "\n"
				+ "    Mix the cream cheese, yogurt and Marmite to make a spreadable mixture. Spoon over the muffins and top with the pickled carrot ribbons and chives, if using.\n"
				+ "");
		cupcakes.setCreationDate(localDate);

		List<Ingredient> cupcakesIngredients = Arrays.asList(flour, baking, cheese, carrots, onions, eggs, yogurt, oil);
		cupcakes.setIngredients(cupcakesIngredients);

		flour.setRecipe(cupcakes);
		flour.setCreationDate(localDate);
		baking.setRecipe(cupcakes);
		baking.setCreationDate(localDate);
		cheese.setRecipe(cupcakes);
		cheese.setCreationDate(localDate);
		carrots.setRecipe(cupcakes);
		carrots.setCreationDate(localDate);
		onions.setRecipe(cupcakes);
		onions.setCreationDate(localDate);
		eggs.setRecipe(cupcakes);
		eggs.setCreationDate(localDate);
		yogurt.setRecipe(cupcakes);
		yogurt.setCreationDate(localDate);
		oil.setRecipe(cupcakes);
		oil.setCreationDate(localDate);

		ingredientRepository.save(flour);
		ingredientRepository.save(baking);
		ingredientRepository.save(cheese);
		ingredientRepository.save(carrots);
		ingredientRepository.save(onions);
		ingredientRepository.save(eggs);
		ingredientRepository.save(yogurt);
		ingredientRepository.save(oil);

		recipeRepository.save(cupcakes);
	}

}
