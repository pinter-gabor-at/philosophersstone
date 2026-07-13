package eu.pintergabor.philosophersstone;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.recipe.ModCraftingRecipe;
import eu.pintergabor.philosophersstone.recipe.ModRecipes;
import eu.pintergabor.philosophersstone.recipe.ModUsingRecipe;

import net.fabricmc.api.ModInitializer;


public final class Mod implements ModInitializer {

	@Override
	public void onInitialize() {
		ModItems.register();
		ModCraftingRecipe.init();
		ModUsingRecipe.init();
		ModRecipes.registerRecipes();
	}
}
