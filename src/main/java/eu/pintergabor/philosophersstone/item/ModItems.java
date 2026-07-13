package eu.pintergabor.philosophersstone.item;

import static eu.pintergabor.philosophersstone.Global.modId;

import java.util.function.Function;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;


public final class ModItems {

	// The philosophers stone.
	public static PhilosopherStoneItem PHILOSPHER_STONE_ITEM;

	/**
	 * Create and register an item similarly as it is in {@link Items}.
	 *
	 * @param name        Name of the item, without modId.
	 * @param itemFactory Function to create the item.
	 * @param props       Initial properties.
	 * @param <T>         Subclass of {@link Item}.
	 * @return The created and registered item.
	 */
	public static @NonNull <T extends Item> T registerItem(
		final @NonNull String name,
		final @NonNull Function<Item.Properties, T> itemFactory,
		final Item.@NonNull Properties props
	) {
		// Create the item key.
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, modId(name));
		// Create the item instance.
		T item = itemFactory.apply(props.setId(key));
		// Register the item and return the registered item.
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	/**
	 * Create and register mod items.
	 */
	public static void register() {
		// Create and register philosophers stone.
		PHILOSPHER_STONE_ITEM = registerItem(
			"philosophers_stone",
			PhilosopherStoneItem::new,
			new Item.Properties().durability(20));
		// Item groups.
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(
			entries -> entries.accept(PHILOSPHER_STONE_ITEM));
	}
}
