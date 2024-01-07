package eu.pintergabor.philosophersstone;

import eu.pintergabor.philosophersstone.util.ModIdentifier;

import net.minecraft.client.util.ModelIdentifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class ModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModelLoadingPlugin.register(pluginContext ->
			new ModelIdentifier(new ModIdentifier("philosophers_stone_3d"), "inventory"));
	}
}
