package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.Global;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;


public class ModRecipes {
	private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
		DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Global.MODID);

	public static void init(IEventBus modEventBus) {
		// The philosophers stone recipe.
		RECIPE_SERIALIZERS.register(
			ModCraftingRecipe.PATH, () ->
				ModCraftingRecipe.SERIALIZER);
		// And its uses.
		RECIPE_SERIALIZERS.register(
			ModUsingRecipe.PATH, () ->
				ModUsingRecipe.SERIALIZER);
		RECIPE_SERIALIZERS.register(modEventBus);
	}
}
