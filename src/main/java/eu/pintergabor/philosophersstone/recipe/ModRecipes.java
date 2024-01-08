package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.util.ModIdentifier;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
	public static void registerRecipes() {
		Registry.register(Registries.RECIPE_SERIALIZER,
			new ModIdentifier(PhilosophersStoneRecipe.ID),
			PhilosophersStoneRecipe.SERIALIZER);
		Registry.register(Registries.RECIPE_SERIALIZER,
			new ModIdentifier(UsingRecipe.ID),
			UsingRecipe.SERIALIZER);
	}
}
