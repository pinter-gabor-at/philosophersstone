package eu.pintergabor.philosophersstone.item;

import eu.pintergabor.philosophersstone.Global;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;


public final class ModItems {

	// The philosophers stone.
	public static PhilosopherStoneItem PHILOSPHER_STONE_ITEM;

	public static void register() {
		// Create and register philosophers stone.
		PHILOSPHER_STONE_ITEM = (PhilosopherStoneItem) Items.registerItem(
			ResourceKey.create(Registries.ITEM, Global.modId("philosophers_stone")),
			PhilosopherStoneItem::new,
			new Item.Properties().durability(20));
		// Item groups.
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(
			entries -> entries.accept(PHILOSPHER_STONE_ITEM));
	}
}
