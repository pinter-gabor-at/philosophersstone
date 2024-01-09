package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.util.ModIdentifier;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
	public static void registerRecipes() {
		// The philosophers stone recipe
		Registry.register(Registries.RECIPE_SERIALIZER,
			new ModIdentifier(PhilosophersStoneRecipe.ID),
			PhilosophersStoneRecipe.SERIALIZER);
		// And its uses
		Registry.register(Registries.RECIPE_SERIALIZER,
			new ModIdentifier(UsingRecipe.ID),
			UsingRecipe.SERIALIZER);
	}
}
