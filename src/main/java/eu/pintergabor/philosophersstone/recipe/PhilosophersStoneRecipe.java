package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.item.ModItems;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class PhilosophersStoneRecipe extends SpecialCraftingRecipe {
	public static String ID = "crafting_special_philosophers_stone";
	public static final RecipeSerializer<PhilosophersStoneRecipe> SERIALIZER =
		new SpecialRecipeSerializer<>(PhilosophersStoneRecipe::new);

	public PhilosophersStoneRecipe(CraftingRecipeCategory category) {
		super(category);
	}

	@Override
	public boolean matches(RecipeInputInventory inventory, World world) {
		final int w = inventory.getWidth();
		final int h = inventory.getHeight();
		final ItemStack center = inventory.getStack(w + 1);
		return fits(w, h) &&
			inventory.getStack(1).isOf(Items.DIAMOND_BLOCK) &&
			inventory.getStack(2 * w + 1).isOf(Items.DIAMOND_BLOCK) &&
			inventory.getStack(w).isOf(Items.GOLD_BLOCK) &&
			inventory.getStack(w + 2).isOf(Items.GOLD_BLOCK) &&
			center.isOf(Items.POTION) && PotionUtil.getPotion(center).equals(Potions.HEALING);
	}

	@Override
	public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
		return new ItemStack(ModItems.PHILOSPHER_STONE_ITEM);
	}

	@Override
	public boolean fits(int width, int height) {
		return width >= 3 && height >= 3;
	}

//	@Override
//	public boolean isIgnoredInRecipeBook() {
//		return false;
//	}

	@Override
	public DefaultedList<ItemStack> getRemainder(RecipeInputInventory inventory) {
		DefaultedList<ItemStack> remainder = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
		remainder.set(0, new ItemStack(Items.STICK));
		return remainder;
	}

	@Override
	public ItemStack getResult(DynamicRegistryManager registryManager) {
		return new ItemStack(ModItems.PHILOSPHER_STONE_ITEM);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
}
