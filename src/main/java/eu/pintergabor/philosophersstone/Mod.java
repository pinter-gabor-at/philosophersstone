package eu.pintergabor.philosophersstone;

import eu.pintergabor.philosophersstone.item.ModItems;

import net.fabricmc.api.ModInitializer;

public final class Mod implements ModInitializer {

	@Override
	public void onInitialize() {
		ModItems.register();
	}
}
