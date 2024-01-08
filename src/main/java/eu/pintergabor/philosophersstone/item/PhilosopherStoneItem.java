package eu.pintergabor.philosophersstone.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

/**
 * The philosopher's stone has healing power
 */
public final class PhilosopherStoneItem extends Item {

	public PhilosopherStoneItem(Item.Settings settings) {
		super(settings);
	}

	/**
	 * @param stack is always {@link ModItems#PHILOSPHER_STONE_ITEM} and the same as
	 * <p>
	 * {@code player.getInventory().getStack(slot)}
	 */
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isClient() && entity instanceof ServerPlayerEntity player) {
			StatusEffectInstance statusEffect = new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0);
			// Apply statuseffect repeatedly, and increase the damage to the PHILOSPHER_STONE_ITEM
			if (!player.hasStatusEffect(statusEffect.getEffectType())) {
				player.addStatusEffect(statusEffect);
				int damage = stack.getDamage();
				if (damage < stack.getMaxDamage()) {
					// Increase damage
					stack.setDamage(damage + 1);
				} else {
					// Find stack in player's inventory and delete it
					player.getInventory().setStack(slot, ItemStack.EMPTY);
					stack = ItemStack.EMPTY;
				}
			}
		}
		super.inventoryTick(stack, world, entity, slot, selected);
	}
}

