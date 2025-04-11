package eu.pintergabor.philosophersstone.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * The philosophers stone has healing power and rejuvenating power.
 */
public final class PhilosopherStoneItem extends Item {

	public PhilosopherStoneItem(Item.Properties settings) {
		super(settings);
	}

	/**
	 * The philosophers stone has healing power.
	 *
	 * @param stack is always {@link ModItems#PHILOSPHER_STONE_ITEM} and
	 *              <p>the same as {@code player.getInventory().getStack(slot)}.
	 */
	@Override
	public void inventoryTick(
		ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
		if (!level.isClientSide && entity instanceof ServerPlayer player) {
			MobEffectInstance statusEffect = new MobEffectInstance(MobEffects.REGENERATION, 600, 0);
			// Apply statuseffect repeatedly, and increase the damage to the PHILOSPHER_STONE_ITEM
			if (!player.hasEffect(statusEffect.getEffect()) && player.getHealth() < 16F) {
				player.addEffect(statusEffect);
				if (slot != null) {
					stack = damageItem(stack, player, slot.getIndex());
				}
			}
		}
		super.inventoryTick(stack, level, entity, slot);
	}

	/**
	 * The philosophers stone has rejuvenating power.
	 */
	@Override
	@NotNull
	public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
		if (user instanceof ServerPlayer player &&
			entity instanceof AgeableMob e) {
			if (!e.isBaby()) {
				e.setBaby(true);
				damageItem(stack, player, player.getInventory().getSelectedSlot());
				return InteractionResult.SUCCESS;
			}
		}
		return super.interactLivingEntity(stack, user, entity, hand);
	}

	/**
	 * Damage the {@link ModItems#PHILOSPHER_STONE_ITEM}.
	 *
	 * @param stack the {@link ModItems#PHILOSPHER_STONE_ITEM},
	 *              <p>same as {@code player.getInventory().getStack(slot)}.
	 * @return the damaged item, or {@link ItemStack#EMPTY}, if fully consumed.
	 */
	private ItemStack damageItem(ItemStack stack, ServerPlayer player, int slot) {
		final int damage = stack.getDamageValue();
		if (damage < stack.getMaxDamage()) {
			// Increase damage
			stack.setDamageValue(damage + 1);
		} else {
			// Find stack in player's inventory and delete it
			stack = ItemStack.EMPTY;
			player.getInventory().add(slot, stack);
		}
		return stack;
	}
}
