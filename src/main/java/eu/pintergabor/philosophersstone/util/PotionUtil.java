package eu.pintergabor.philosophersstone.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;


public class PotionUtil {

	private PotionUtil() {
		// Static class.
	}

	/**
	 * Check {@code stack} is of {@code potion}
	 * <p>
	 * Similar to {@link ItemStack#isOf(Item)}, but for potions
	 *
	 * @param stack  Item stack to check
	 * @param potion A potion from {@link Potions}
	 * @return whether the {@code stack} is of {@code potion}
	 */
	public static boolean matchPotion(ItemStack stack, RegistryEntry<Potion> potion) {
		return stack.isOf(Items.POTION) &&
			stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)
				.matches(potion);
	}
}
