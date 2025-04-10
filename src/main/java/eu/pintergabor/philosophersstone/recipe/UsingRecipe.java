package eu.pintergabor.philosophersstone.recipe;

import java.util.AbstractMap;
import java.util.Map;

import eu.pintergabor.philosophersstone.item.ModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;


public class UsingRecipe extends CustomRecipe {
	public static final String PATH = "using_recipe";
	public static final RecipeSerializer<UsingRecipe> SERIALIZER =
		new CustomRecipe.Serializer<>(UsingRecipe::new);
	/**
	 * Input -> Output map.
	 */
	private static final Map<Item, Result> RESULTMAP = Map.ofEntries(
		// Gold generating.
		new AbstractMap.SimpleImmutableEntry<>(Items.RAW_COPPER_BLOCK, new Result(Items.GOLD_BLOCK, 2)),
		new AbstractMap.SimpleImmutableEntry<>(Items.COPPER_BLOCK, new Result(Items.GOLD_BLOCK, 2)),
		new AbstractMap.SimpleImmutableEntry<>(Items.WAXED_COPPER_BLOCK, new Result(Items.GOLD_BLOCK, 2)),
		new AbstractMap.SimpleImmutableEntry<>(Items.COPPER_INGOT, new Result(Items.GOLD_INGOT, 2)),
		new AbstractMap.SimpleImmutableEntry<>(Items.RAW_IRON_BLOCK, new Result(Items.GOLD_BLOCK, 4)),
		new AbstractMap.SimpleImmutableEntry<>(Items.IRON_BLOCK, new Result(Items.GOLD_BLOCK, 4)),
		new AbstractMap.SimpleImmutableEntry<>(Items.IRON_INGOT, new Result(Items.GOLD_INGOT, 4)),
		// Diamond generating.
		new AbstractMap.SimpleImmutableEntry<>(Items.COAL_BLOCK, new Result(Items.DIAMOND_BLOCK, 1)),
		new AbstractMap.SimpleImmutableEntry<>(Items.COAL, new Result(Items.DIAMOND, 1)),
		new AbstractMap.SimpleImmutableEntry<>(Items.CHARCOAL, new Result(Items.DIAMOND, 1)),
		// Misc.
		new AbstractMap.SimpleImmutableEntry<>(Items.REDSTONE_TORCH, new Result(Items.REDSTONE_BLOCK, 1)),
		new AbstractMap.SimpleImmutableEntry<>(Items.STICK, new Result(Items.OAK_LOG, 1))
	);

	/**
	 * Similar to {@link ItemStack}, but lighter.
	 */
	private record Result(Item item, int count) {
	}

	/**
	 * The crafted result.
	 */
	private ItemStack result;

	public UsingRecipe(CraftingBookCategory category) {
		super(category);
	}

	/**
	 * Check if there is a {@link ModItems#PHILOSPHER_STONE_ITEM} at the center.
	 *
	 * @return the {@link ItemStack} of {@link ModItems#PHILOSPHER_STONE_ITEM} on success.
	 */
	@Nullable
	private ItemStack testCenter(CraftingInput input) {
		final ItemStack center = input.getItem(4);
		return center.is(ModItems.PHILOSPHER_STONE_ITEM) ? center : null;
	}

	/**
	 * Check if there are 8 identical items around the center and try to craft the result.
	 *
	 * @return the crafted result.
	 */
	@Nullable
	private Result tryCraft(CraftingInput input) {
		final Item key = input.getItem(0).getItem();
		for (int i = 1; i < 9; i++) {
			if (i != 4) {
				ItemStack itemStack = input.getItem(i);
				if (!itemStack.is(key)) {
					return null;
				}
			}
		}
		return RESULTMAP.get(key);
	}

	/**
	 * Match recipe and craft {@link #result}.
	 * <p>
	 * All recipes consist of 8 identical input items, and the {@link ModItems#PHILOSPHER_STONE_ITEM} at the center.
	 *
	 * @return true on match.
	 */
	@Override
	public boolean matches(CraftingInput input, @NotNull Level level) {
		final int w = input.width();
		final int h = input.height();
		if (w == 3 && h == 3 && testCenter(input) != null) {
			Result r = tryCraft(input);
			if (r != null) {
				result = new ItemStack(r.item, r.count);
				return true;
			}
		}
		return false;
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

	/**
	 * Leave the damaged {@link ModItems#PHILOSPHER_STONE_ITEM} as remainder.
	 */
	@Override
	@NotNull
	public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
		NonNullList<ItemStack> remainder = NonNullList.withSize(input.size(), ItemStack.EMPTY);
		final int w = input.width();
		final int h = input.height();
		if (w == 3 && h == 3) {
			ItemStack center = testCenter(input);
			if (center != null) {
				final int damage = center.getDamageValue();
				if (damage < center.getMaxDamage()) {
					// Have to create a new one
					center = new ItemStack(ModItems.PHILOSPHER_STONE_ITEM.get());
					center.setDamageValue(damage + 1);
					remainder.set(4, center);
				}
			}
		}
		return remainder;
	}

	@Override
	@NotNull
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return SERIALIZER;
	}
}
