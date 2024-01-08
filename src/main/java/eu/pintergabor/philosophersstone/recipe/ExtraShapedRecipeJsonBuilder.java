package eu.pintergabor.philosophersstone.recipe;

import java.util.Objects;

import eu.pintergabor.philosophersstone.mixin.ShapedRecipeJsonBuilderAccessor;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

public class ExtraShapedRecipeJsonBuilder extends ShapedRecipeJsonBuilder {

	public ExtraShapedRecipeJsonBuilder(
		RecipeCategory category, ItemConvertible output, int count) {
		super(category, output, count);
	}

	/**
	 * Same as its parent, but with {@link ExtraShapedRecipe} instead of {@link ShapedRecipe}
	 */
	public static ExtraShapedRecipeJsonBuilder create(
		RecipeCategory category, ItemConvertible output) {
		return create(category, output, 1);
	}

	/**
	 * Same as its parent, but with {@link ExtraShapedRecipe} instead of {@link ShapedRecipe}
	 */
	public static ExtraShapedRecipeJsonBuilder create(
		RecipeCategory category, ItemConvertible output, int count) {
		return new ExtraShapedRecipeJsonBuilder(category, output, count);
	}

	/**
	 * Almost the same as its parent, but with {@link ExtraShapedRecipe} instead of {@link ShapedRecipe}
	 */
	@Override
	public void offerTo(RecipeExporter exporter, Identifier recipeId) {
		final var r = (ShapedRecipeJsonBuilderAccessor) this;
		final RawShapedRecipe rawShapedRecipe = RawShapedRecipe.create(r.getInputs(), r.getPattern());
		AdvancementEntry advancement = null;
		Advancement.Builder builder = exporter.getAdvancementBuilder()
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
			.rewards(AdvancementRewards.Builder.recipe(recipeId))
			.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
		if (builder != null) {
			r.getCriteria().forEach(builder::criterion);
			advancement = builder.build(recipeId.withPrefixedPath(
				"recipes/" + r.getCategory().getName() + "/"));
		}
		ExtraShapedRecipe recipe = new ExtraShapedRecipe(
			Objects.requireNonNullElse(r.getGroup(), ""),
			CraftingRecipeJsonBuilder.toCraftingCategory(r.getCategory()),
			rawShapedRecipe,
			new ItemStack(r.getOutput(), r.getCount()));
		exporter.accept(recipeId, recipe, advancement);
	}
}
