package eu.pintergabor.philosophersstone.util;

import eu.pintergabor.philosophersstone.Global;
import net.minecraft.util.Identifier;

public class ModIdentifier extends Identifier {
	/**
	 * Create a mod specific identifier
	 * @param path Name, as in lang/*.json files without "*.modid." prefix
	 */
	public ModIdentifier(String path) {
		super(Global.MODID, path);
	}
}
