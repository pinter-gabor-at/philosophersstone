package eu.pintergabor.philosophersstone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Global {
	private Global() {
	}

	// Used for logging and registration
	public static final String MODID = "philosophersstone";

	// This logger is used to write text to the console and the log file.
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
}
