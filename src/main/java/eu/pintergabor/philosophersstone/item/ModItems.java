package eu.pintergabor.philosophersstone.item;

import eu.pintergabor.philosophersstone.Global;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;


public final class ModItems {
	public static final DeferredRegister.Items ITEMS =
		DeferredRegister.createItems(Global.MODID);

	// The philosophers stone.
	public static DeferredItem<Item> PHILOSPHER_STONE_ITEM;

	public static void init(IEventBus modEventBus) {
		// Create and register philosophers stone.
		PHILOSPHER_STONE_ITEM = ITEMS.registerItem(
			"philosophers_stone",
			PhilosopherStoneItem::new,
			new Item.Properties().durability(20));
		// Register them on the mod event bus.
		ITEMS.register(modEventBus);
//		// Item groups.
//		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(
//			entries -> entries.accept(PHILOSPHER_STONE_ITEM));
	}
}
