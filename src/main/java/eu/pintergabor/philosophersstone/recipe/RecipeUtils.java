package eu.pintergabor.philosophersstone.recipe;

import java.util.Collection;
import java.util.List;

import net.minecraft.recipe.Ingredient;

/**
 * Utilities for recipe generation
 */
public final class RecipeUtils {

	/**
	 * Filter empty ingredients from a {@link Collection}, or a {@link List} or an Array
	 * @param ingredients input
	 * @return output
	 */
	public static Ingredient[] filterEmptyIngredients(Collection<Ingredient> ingredients) {
		return ingredients.stream()
			.filter((ingredient) ->
				!ingredient.isEmpty()).toArray(Ingredient[]::new);
	}
}
