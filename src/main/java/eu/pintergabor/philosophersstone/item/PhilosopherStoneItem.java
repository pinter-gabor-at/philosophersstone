package eu.pintergabor.philosophersstone.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * The philosophers stone has healing power and rejuvenating power
 */
public final class PhilosopherStoneItem extends Item {

	public PhilosopherStoneItem(Item.Settings settings) {
		super(settings);
	}

	/**
	 * The philosophers stone has healing power
	 * @param stack is always {@link ModItems#PHILOSPHER_STONE_ITEM} and
	 * <p>the same as {@code player.getInventory().getStack(slot)}
	 */
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isClient() && entity instanceof ServerPlayerEntity player) {
			StatusEffectInstance statusEffect = new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0);
			// Apply statuseffect repeatedly, and increase the damage to the PHILOSPHER_STONE_ITEM
			if (!player.hasStatusEffect(statusEffect.getEffectType()) && player.getHealth() < 16f) {
				player.addStatusEffect(statusEffect);
				stack = damageItem(stack, player, slot);
			}
		}
		super.inventoryTick(stack, world, entity, slot, selected);
	}

	/**
	 * The philosophers stone has rejuvenating power
	 */
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (user instanceof ServerPlayerEntity player &&
			entity instanceof PassiveEntity e) {
			if (!e.isBaby()) {
				e.setBaby(true);
				damageItem(stack, player, player.getInventory().selectedSlot);
				return ActionResult.SUCCESS;
			}
		}
		return super.useOnEntity(stack, user, entity, hand);
	}

	/**
	 * Damage the {@link ModItems#PHILOSPHER_STONE_ITEM}
	 * @param stack the {@link ModItems#PHILOSPHER_STONE_ITEM},
	 * <p>same as {@code player.getInventory().getStack(slot)}
	 * @return the damaged item, or {@link ItemStack#EMPTY}, if fully consumed
	 */
	private ItemStack damageItem(ItemStack stack, ServerPlayerEntity player, int slot) {
		final int damage = stack.getDamage();
		if (damage < stack.getMaxDamage()) {
			// Increase damage
			stack.setDamage(damage + 1);
		} else {
			// Find stack in player's inventory and delete it
			stack = ItemStack.EMPTY;
			player.getInventory().setStack(slot, stack);
		}
		return stack;
	}
}
