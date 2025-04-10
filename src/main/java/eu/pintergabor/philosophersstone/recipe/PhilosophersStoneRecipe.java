package eu.pintergabor.philosophersstone.recipe;

import java.util.List;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.util.PotionUtil;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;


public class PhilosophersStoneRecipe extends CustomRecipe {
	public static final String PATH = "crafting_recipe";
	public static final RecipeSerializer<PhilosophersStoneRecipe> SERIALIZER =
		new CustomRecipe.Serializer<>(PhilosophersStoneRecipe::new);
	/**
	 * The one and only crafting result.
	 */
	private static final ItemStack result =
		new ItemStack(ModItems.PHILOSPHER_STONE_ITEM.get());
	/**
	 * Used in {@link #matchesGoldDiamond(CraftingInput)}.
	 */
	private static final int[] slots = {1, 3, 5, 7};
	/**
	 * Used in {@link #matchesPotion(CraftingInput)}.
	 */
	private static final List<Holder<Potion>> potions = List.of(
		Potions.HEALING,
		Potions.STRONG_HEALING,
		Potions.REGENERATION,
		Potions.LONG_REGENERATION);

	public PhilosophersStoneRecipe(CraftingBookCategory category) {
		super(category);
	}

	/**
	 * There must be exactly two gold and two diamond blocks in cross shape.
	 * <pre>
	 *
	 *   *
	 * * p *
	 *   *
	 */
	private static boolean matchesGoldDiamond(CraftingInput input) {
		int countGoldBlocks = 0;
		int countDiamondBlocks = 0;
		for (int i : slots) {
			final ItemStack itemStack = input.getItem(i);
			if (itemStack.is(Items.GOLD_BLOCK)) {
				countGoldBlocks++;
			} else if (itemStack.is(Items.DIAMOND_BLOCK)) {
				countDiamondBlocks++;
			}
		}
		return countGoldBlocks == 2 && countDiamondBlocks == 2;
	}

	/**
	 * There must be one healing or regeneration potion in the middle.
	 */
	private static boolean matchesPotion(CraftingInput input) {
		ItemStack potion = input.getItem(4);
		for (var p : potions) {
			if (PotionUtil.matchPotion(potion, p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * There is only one recipe, and the result is always the {@link ModItems#PHILOSPHER_STONE_ITEM}.
	 */
	@Override
	public boolean matches(CraftingInput input, @NotNull Level level) {
		final int w = input.width();
		final int h = input.height();
		return w == 3 && h == 3 &&
			matchesGoldDiamond(input) && matchesPotion(input);
	}

	/**
	 * @return the already crafted {@link #result}.
	 */
	@Override
	@NotNull
	public ItemStack assemble(
		@NotNull CraftingInput input, @NotNull HolderLookup.Provider registries) {
		return result;
	}

	@Override
	@NotNull
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return SERIALIZER;
	}
}
