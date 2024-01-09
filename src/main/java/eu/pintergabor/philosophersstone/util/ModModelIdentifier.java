package eu.pintergabor.philosophersstone.util;

import eu.pintergabor.philosophersstone.Global;

import net.minecraft.client.util.ModelIdentifier;

public class ModModelIdentifier extends ModelIdentifier {

	/**
	 * Create a mod specific model identifier
	 * @param path to the *.json file
	 */
	public ModModelIdentifier(String path, String variant) {
		super(Global.MODID, path, variant);
	}
}
