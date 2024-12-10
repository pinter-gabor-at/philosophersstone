package eu.pintergabor.philosophersstone.item;

import eu.pintergabor.philosophersstone.Global;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static eu.pintergabor.philosophersstone.util.Register.registerItem;

public final class ModItems {

    // The philosophers stone
    public static PhilosopherStoneItem PHILOSPHER_STONE_ITEM;

    public static void register() {
        // Create and register philosophers stone
        PHILOSPHER_STONE_ITEM = new PhilosopherStoneItem(
                new Item.Settings().maxDamage(20));
        registerItem(Global.ModIdentifier("philosophers_stone"), PHILOSPHER_STONE_ITEM);
        // Item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(
                entries -> entries.add(PHILOSPHER_STONE_ITEM));
    }
}
