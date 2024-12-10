package eu.pintergabor.philosophersstone;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;

public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(pluginContext ->
                new ModelIdentifier(Global.ModIdentifier("philosophers_stone_3d"), "inventory"));
    }
}
