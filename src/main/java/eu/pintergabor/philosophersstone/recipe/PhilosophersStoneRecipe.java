package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.util.PotionUtil;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;


public class PhilosophersStoneRecipe extends CustomRecipe {
	public static final String ID = "crafting_recipe";
	public static final RecipeSerializer<PhilosophersStoneRecipe> SERIALIZER =
		new CustomRecipe.Serializer<>(PhilosophersStoneRecipe::new);
	/**
	 * The one and only crafting result.
	 */
	private static final ItemStack result = new ItemStack(ModItems.PHILOSPHER_STONE_ITEM);

	public PhilosophersStoneRecipe(CraftingBookCategory category) {
		super(category);
	}

	/**
	 * There is only one recipe, and the result is always the {@link ModItems#PHILOSPHER_STONE_ITEM}.
	 */
	@Override
	public boolean matches(CraftingInput input, Level level) {
		final int w = input.width();
		final int h = input.height();
		return w == 3 && h == 3 &&
			input.getItem(1).is(Items.DIAMOND_BLOCK) &&
			input.getItem(7).is(Items.DIAMOND_BLOCK) &&
			input.getItem(3).is(Items.GOLD_BLOCK) &&
			input.getItem(5).is(Items.GOLD_BLOCK) &&
			PotionUtil.matchPotion(input.getItem(4), Potions.HEALING);
	}

	/**
	 * @return the already crafted {@link #result}.
	 */
	@Override
	@NotNull
	public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
		return result;
	}

	@Override
	@NotNull
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return SERIALIZER;
	}
}
