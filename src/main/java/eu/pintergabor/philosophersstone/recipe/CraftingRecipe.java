package eu.pintergabor.philosophersstone.recipe;

import java.util.List;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.util.ModUtil;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;


public class CraftingRecipe extends CustomRecipe {
	public static final String PATH = "crafting_recipe";
	public static final RecipeSerializer<CraftingRecipe> SERIALIZER =
		new CustomRecipe.Serializer<>(CraftingRecipe::new);
	/**
	 * The one and only crafting result.
	 */
	private static final ItemStack result =
		new ItemStack(ModItems.PHILOSPHER_STONE_ITEM.get());
	/**
	 * Used in {@link #matchesPotion(CraftingInput)}.
	 */
	private static final List<Holder<Potion>> potions = List.of(
		Potions.HEALING,
		Potions.STRONG_HEALING,
		Potions.REGENERATION,
		Potions.LONG_REGENERATION);

	public CraftingRecipe(CraftingBookCategory category) {
		super(category);
	}

	/**
	 * There must be exactly two gold and two diamond blocks in cross shape.
	 * <pre>
	 *
	 *   G             D
	 * D + D   or    G + G
	 *   G             D
	 *
	 * G = Gold block
	 * D = Diamond block
	 * + = Potion
	 */
	private static boolean matchesGoldDiamond(CraftingInput input) {
		ItemStack i1 = input.getItem(1);
		ItemStack i3 = input.getItem(3);
		ItemStack i5 = input.getItem(5);
		ItemStack i7 = input.getItem(7);
		Item G = Items.GOLD_BLOCK;
		Item D = Items.DIAMOND_BLOCK;
		return (ModUtil.sameItem(i1, i7) && ModUtil.sameItem(i3, i5)) &&
			((i1.is(G) && i3.is(D)) || (i1.is(D) && i3.is(G)));
	}

	/**
	 * There must be one healing or regeneration potion in the middle.
	 * <p>
	 * See {@link #matchesGoldDiamond(CraftingInput)}.
	 */
	private static boolean matchesPotion(CraftingInput input) {
		ItemStack center = input.getItem(4);
		for (var p : potions) {
			if (ModUtil.isPotion(center, p)) {
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
