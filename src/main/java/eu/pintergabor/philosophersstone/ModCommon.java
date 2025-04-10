package eu.pintergabor.philosophersstone;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.recipe.ModRecipes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;


/**
 * Common startup.
 */
@Mod(Global.MODID)
public final class ModCommon {

	@SuppressWarnings("unused")
	public ModCommon(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
		// Register items.
		ModItems.init(modEventBus);
		// Register custom recipes.
		ModRecipes.init(modEventBus);
	}
}
