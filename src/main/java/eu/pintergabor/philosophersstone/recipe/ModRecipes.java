package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.util.ModIdentifier;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
	public static void registerRecipes() {
		Registry.register(Registries.RECIPE_SERIALIZER,
			new ModIdentifier(GemEmpoweringRecipe.Serializer.ID),
			GemEmpoweringRecipe.Serializer.INSTANCE);
		Registry.register(Registries.RECIPE_TYPE,
			new ModIdentifier(GemEmpoweringRecipe.Type.ID),
			GemEmpoweringRecipe.Type.INSTANCE);
		Registry.register(Registries.RECIPE_SERIALIZER,
			new ModIdentifier(ExtraShapedRecipe.Serializer.ID),
			ExtraShapedRecipe.Serializer.INSTANCE);
		Registry.register(Registries.RECIPE_TYPE,
			new ModIdentifier(ExtraShapedRecipe.Type.ID),
			ExtraShapedRecipe.Type.INSTANCE);
	}
}
