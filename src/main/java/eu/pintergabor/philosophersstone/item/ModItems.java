package eu.pintergabor.philosophersstone.item;

import static eu.pintergabor.philosophersstone.util.Register.registerItem;

import net.minecraft.item.ItemGroups;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public final class ModItems {
	private ModItems() {
	}

	// The philosophers stone
	public static PhilosopherStoneItem PHILOSPHER_STONE_ITEM;

	public static void register() {
		// Create and register philosophers stone
		PHILOSPHER_STONE_ITEM = new PhilosopherStoneItem(
			new FabricItemSettings().maxDamage(100));
		registerItem(
			"philosophers_stone", PHILOSPHER_STONE_ITEM);
		// Item groups
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(
			entries -> {
				entries.add(PHILOSPHER_STONE_ITEM);
			});
	}
}
