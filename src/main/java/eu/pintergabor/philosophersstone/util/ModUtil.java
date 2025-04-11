package eu.pintergabor.philosophersstone.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;


/**
 * Common, unclassified, utilities for the mod.
 */
public final class ModUtil {

	private ModUtil() {
		// Static class.
	}

	/**
	 * Check if {@code stack} is of {@code potion}.
	 * <p>
	 * Similar to {@link ItemStack#is(Item)}, but more generic.
	 *
	 * @param stack  Item stack to check.
	 * @param item Usually an item from {@link Items}.
	 * @return whether the {@code stack} is of {@code item}.
	 */
	@SuppressWarnings("unused")
	public static boolean isItem(ItemStack stack, ItemLike item) {
		return stack.is(item.asItem());
	}

	/**
	 * Check if {@code stack1} contains the same item as {@code stack2}.
	 * <p>
	 * Similar to {@link ItemStack#is(Item)}.
	 */
	@SuppressWarnings("unused")
	public static boolean sameItem(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() == stack2.getItem();
	}

	/**
	 * Check if {@code stack} is of {@code potion}.
	 * <p>
	 * Similar to {@link ItemStack#is(Item)}, but for potions.
	 *
	 * @param stack  Item stack to check.
	 * @param potion A potion from {@link Potions}.
	 * @return whether the {@code stack} is of {@code potion}.
	 */
	@SuppressWarnings("unused")
	public static boolean isPotion(ItemStack stack, Holder<Potion> potion) {
		return stack.is(Items.POTION) &&
			stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
				.is(potion);
	}
}
