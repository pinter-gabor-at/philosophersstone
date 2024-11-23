package eu.pintergabor.philosophersstone.util;

import eu.pintergabor.philosophersstone.Global;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class Register {

    /**
     * Register mod item
     *
     * @param name Name, as in lang/*.json files without the "item.modid." prefix
     * @param item Item
     * @return The same item
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Item registerItem(String name, Item item) {
        return Registry.register(
                Registries.ITEM,
                Global.ModIdentifier(name),
                item);
    }

    /**
     * Register mod item
     *
     * @param id   Identifier
     * @param item Item
     * @return The same identifier
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Identifier registerItem(Identifier id, Item item) {
        Registry.register(
                Registries.ITEM,
                id,
                item);
        return id;
    }
}
