package eu.pintergabor.philosophersstone.item;

import static eu.pintergabor.philosophersstone.util.Register.registerItem;

import eu.pintergabor.philosophersstone.util.ModIdentifier;

import net.minecraft.item.ItemGroups;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.util.Identifier;

public final class ModItems {
	private ModItems() {
	}

	// The philosophers stone
	public static Identifier PHILOSPHER_STONE_ITEM_ID;
	public static PhilosopherStoneItem PHILOSPHER_STONE_ITEM;

	public static void register() {
		// Create and register philosophers stone
		PHILOSPHER_STONE_ITEM = new PhilosopherStoneItem(
			new FabricItemSettings().maxDamage(100));
		PHILOSPHER_STONE_ITEM_ID = new ModIdentifier("philosophers_stone");
		registerItem(PHILOSPHER_STONE_ITEM_ID, PHILOSPHER_STONE_ITEM);
		// Item groups
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(
			entries -> {
				entries.add(PHILOSPHER_STONE_ITEM);
			});
	}
}
