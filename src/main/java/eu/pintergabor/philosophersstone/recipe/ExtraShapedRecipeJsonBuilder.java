package eu.pintergabor.philosophersstone.recipe;

import java.util.Map;
import java.util.Objects;

import eu.pintergabor.philosophersstone.mixin.ShapedRecipeJsonBuilderAccessor;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

public class ExtraShapedRecipeJsonBuilder extends ShapedRecipeJsonBuilder {

	public ExtraShapedRecipeJsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
		super(category, output, count);
	}

	public static ExtraShapedRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output) {
		return create(category, output, 1);
	}

	public static ExtraShapedRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
		return new ExtraShapedRecipeJsonBuilder(category, output, count);
	}

	@Override
	public void offerTo(RecipeExporter exporter, Identifier recipeId) {
		var parent = (ShapedRecipeJsonBuilderAccessor) this;
		RawShapedRecipe rawShapedRecipe = RawShapedRecipe.create(parent.getInputs(), parent.getPattern());
		Advancement.Builder builder = exporter.getAdvancementBuilder()
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
			.rewards(AdvancementRewards.Builder.recipe(recipeId))
			.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
		Map<String, AdvancementCriterion<?>> var10000 = parent.getCriteria();
		Objects.requireNonNull(builder);
		var10000.forEach(builder::criterion);
		ExtraShapedRecipe shapedRecipe = new ExtraShapedRecipe(
			Objects.requireNonNullElse(parent.getGroup(), ""),
			CraftingRecipeJsonBuilder.toCraftingCategory(parent.getCategory()),
			rawShapedRecipe,
			new ItemStack(parent.getOutput(), parent.getCount()),
			true);
		exporter.accept(recipeId, shapedRecipe,
			builder.build(recipeId.withPrefixedPath("recipes/" + parent.getCategory().getName() + "/")));
	}
}
