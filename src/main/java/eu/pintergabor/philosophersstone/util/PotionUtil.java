package eu.pintergabor.philosophersstone.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;


public class PotionUtil {

	private PotionUtil() {
		// Static class.
	}

	/**
	 * Check {@code stack} is of {@code potion}.
	 * <p>
	 * Similar to {@link ItemStack#is(Item)}, but for potions.
	 *
	 * @param stack  Item stack to check.
	 * @param potion A potion from {@link Potions}.
	 * @return whether the {@code stack} is of {@code potion}.
	 */
	public static boolean matchPotion(ItemStack stack, Holder<Potion> potion) {
		return stack.is(Items.POTION) &&
			stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
				.is(potion);
	}
}
