package eu.pintergabor.philosophersstone.recipe;

import java.util.AbstractMap;
import java.util.Map;

import eu.pintergabor.philosophersstone.item.ModItems;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;


public class UsingRecipe extends SpecialCraftingRecipe {
	public static final String ID = "using_recipe";
	public static final RecipeSerializer<UsingRecipe> SERIALIZER =
		new SpecialRecipeSerializer<>(UsingRecipe::new);
	/**
	 * Input -> Output map.
	 */
	private static final Map<Item, Result> RESULTMAP = Map.ofEntries(
		// Gold generating
		new AbstractMap.SimpleImmutableEntry<>(Items.RAW_COPPER_BLOCK, new Result(Items.GOLD_BLOCK, 2)),
		new AbstractMap.SimpleImmutableEntry<>(Items.COPPER_BLOCK, new Result(Items.GOLD_BLOCK, 2)),
		new AbstractMap.SimpleImmutableEntry<>(Items.WAXED_COPPER_BLOCK, new Result(Items.GOLD_BLOCK, 2)),
		new AbstractMap.SimpleImmutableEntry<>(Items.COPPER_INGOT, new Result(Items.GOLD_INGOT, 2)),
		new AbstractMap.SimpleImmutableEntry<>(Items.RAW_IRON_BLOCK, new Result(Items.GOLD_BLOCK, 4)),
		new AbstractMap.SimpleImmutableEntry<>(Items.IRON_BLOCK, new Result(Items.GOLD_BLOCK, 4)),
		new AbstractMap.SimpleImmutableEntry<>(Items.IRON_INGOT, new Result(Items.GOLD_INGOT, 4)),
		// Diamond generating
		new AbstractMap.SimpleImmutableEntry<>(Items.COAL_BLOCK, new Result(Items.DIAMOND_BLOCK, 1)),
		new AbstractMap.SimpleImmutableEntry<>(Items.COAL, new Result(Items.DIAMOND, 1)),
		new AbstractMap.SimpleImmutableEntry<>(Items.CHARCOAL, new Result(Items.DIAMOND, 1)),
		// Misc
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

	public UsingRecipe(CraftingRecipeCategory category) {
		super(category);
	}

	/**
	 * Check if there is a {@link ModItems#PHILOSPHER_STONE_ITEM} at the center.
	 *
	 * @return the {@link ItemStack} of {@link ModItems#PHILOSPHER_STONE_ITEM} on success.
	 */
	private @Nullable ItemStack testCenter(CraftingRecipeInput input) {
		final ItemStack center = input.getStackInSlot(4);
		return center.isOf(ModItems.PHILOSPHER_STONE_ITEM) ? center : null;
	}

	/**
	 * Check if there are 8 identical items around the center and try to craft the result.
	 *
	 * @return the crafted result.
	 */
	private @Nullable Result tryCraft(CraftingRecipeInput input) {
		final Item key = input.getStackInSlot(0).getItem();
		for (int i = 1; i < 9; i++) {
			if (i != 4) {
				ItemStack itemStack = input.getStackInSlot(i);
				if (!itemStack.isOf(key)) {
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
	public boolean matches(CraftingRecipeInput input, World world) {
		final int w = input.getWidth();
		final int h = input.getHeight();
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
	public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
		return result;
	}

	/**
	 * Leave the damaged {@link ModItems#PHILOSPHER_STONE_ITEM} as remainder.
	 */
	@Override
	public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
		DefaultedList<ItemStack> remainder = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);
		final int w = input.getWidth();
		final int h = input.getHeight();
		if (w == 3 && h == 3) {
			ItemStack center = testCenter(input);
			if (center != null) {
				final int damage = center.getDamage();
				if (damage < center.getMaxDamage()) {
					// Have to create a new one
					center = new ItemStack(ModItems.PHILOSPHER_STONE_ITEM);
					center.setDamage(damage + 1);
					remainder.set(4, center);
				}
			}
		}
		return remainder;
	}

	@Override
	public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
		return SERIALIZER;
	}
}
