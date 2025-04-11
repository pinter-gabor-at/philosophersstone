package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.Global;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;


public class ModRecipes {

	public static void registerRecipes() {
		// The philosophers stone recipe.
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
			Global.modId(CraftingRecipe.PATH),
			CraftingRecipe.SERIALIZER);
		// And its uses.
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
			Global.modId(UsingRecipe.PATH),
			UsingRecipe.SERIALIZER);
	}
}
