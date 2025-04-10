package eu.pintergabor.philosophersstone.item;

import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import net.minecraft.world.item.CreativeModeTabs;


public final class CreativeTabs {

	/**
	 * Add items to creative tabs.
	 */
	public static void init(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(ModItems.PHILOSPHER_STONE_ITEM);
		}
	}
}
