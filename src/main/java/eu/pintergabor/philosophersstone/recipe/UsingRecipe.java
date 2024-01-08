package eu.pintergabor.philosophersstone.recipe;

import java.util.AbstractMap;
import java.util.Map;

import eu.pintergabor.philosophersstone.item.ModItems;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class UsingRecipe extends SpecialCraftingRecipe {
	public static String ID = "using";
	public static final RecipeSerializer<UsingRecipe> SERIALIZER =
		new SpecialRecipeSerializer<>(UsingRecipe::new);
	/**
	 * Input -> Output map
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
	ItemStack result;

	private record Result(Item item, int count) {
	}

	public UsingRecipe(CraftingRecipeCategory category) {
		super(category);
	}

	/**
	 * Match recipe and craft {@link #result}
	 * <p>
	 * All recipes consist of 8 identical input items, and the {@link ModItems#PHILOSPHER_STONE_ITEM} at the center
	 * @return true on match
	 */
	@Override
	public boolean matches(RecipeInputInventory inventory, World world) {
		final int w = inventory.getWidth();
		final int h = inventory.getHeight();
		if (w == 3 && h == 3) {
			final ItemStack center = inventory.getStack(4);
			final Item key = inventory.getStack(0).getItem();
			if (center.isOf(ModItems.PHILOSPHER_STONE_ITEM)) {
				for (int i = 1; i < 9; i++) {
					if (i != 4) {
						ItemStack itemStack = inventory.getStack(i);
						if (!itemStack.isOf(key)) {
							return false;
						}
					}
				}
			}
			Result r = RESULTMAP.get(key);
			if (r != null) {
				result = new ItemStack(r.item, r.count);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the already crafted result
	 */
	@Override
	public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
		return result;
	}

	@Override
	public boolean fits(int width, int height) {
		return width >= 3 && height >= 3;
	}

	/**
	 * Leave the damaged {@link ModItems#PHILOSPHER_STONE_ITEM} as remainder
	 */
	@Override
	public DefaultedList<ItemStack> getRemainder(RecipeInputInventory inventory) {
		DefaultedList<ItemStack> remainder = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
		final int w = inventory.getWidth();
		final int h = inventory.getHeight();
		if (w == 3 && h == 3) {
			ItemStack center = inventory.getStack(4);
			if (center.isOf(ModItems.PHILOSPHER_STONE_ITEM)) {
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

	/**
	 * @return the already crafted result
	 */
	@Override
	public ItemStack getResult(DynamicRegistryManager registryManager) {
		return new ItemStack(ModItems.PHILOSPHER_STONE_ITEM);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
}
