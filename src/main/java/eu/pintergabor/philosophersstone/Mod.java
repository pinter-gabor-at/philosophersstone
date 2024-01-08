package eu.pintergabor.philosophersstone;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.recipe.ModRecipes;

import net.fabricmc.api.ModInitializer;

public final class Mod implements ModInitializer {

	@Override
	public void onInitialize() {
		ModItems.register();
		ModRecipes.registerRecipes();
	}
}
