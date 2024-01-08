package eu.pintergabor.philosophersstone.item;

import eu.pintergabor.philosophersstone.recipe.ExtraShapedRecipeJsonBuilder;

import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

public class ModRecipeGenerator extends FabricRecipeProvider {
	public ModRecipeGenerator(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate(RecipeExporter exporter) {
		ExtraShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PHILOSPHER_STONE_ITEM)
			.pattern(" D ")
			.pattern("GOG")
			.pattern(" D ")
			.input('O', Items.POTION)
			.input('D', Items.DIAMOND)
			.input('G', Items.GOLD_INGOT)
			.criterion("has_" + Items.DIAMOND, conditionsFromItem(Items.DIAMOND))
			.criterion("has_" + Items.GOLD_INGOT, conditionsFromItem(Items.GOLD_INGOT))
			.offerTo(exporter, new Identifier(getRecipeName(ModItems.PHILOSPHER_STONE_ITEM)));
	}
}
