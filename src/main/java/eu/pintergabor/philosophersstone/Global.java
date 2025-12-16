package eu.pintergabor.philosophersstone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.Identifier;


public final class Global {

	private Global() {
		// Static class.
	}

	/**
	 * Used for logging and registration.
	 */
	public static final String MODID = "philosophersstone";

	/**
	 * This logger is used to write text to the console and the log file.
	 */
	@SuppressWarnings("unused")
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	/**
	 * Create a mod specific name.
	 *
	 * @param path Name without {@link #MODID}.
	 */
	@SuppressWarnings("unused")
	public static String modName(String path) {
		return MODID + ":" + path;
	}

	/**
	 * Create a mod specific identifier.
	 *
	 * @param path Name without {@link #MODID}.
	 */
	@SuppressWarnings("unused")
	public static Identifier modId(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}
}
