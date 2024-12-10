package eu.pintergabor.philosophersstone.item;

import eu.pintergabor.philosophersstone.Global;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public final class ModItems {

    // The philosophers stone
    public static PhilosopherStoneItem PHILOSPHER_STONE_ITEM;

    public static void register() {
        // Create and register philosophers stone
        PHILOSPHER_STONE_ITEM = (PhilosopherStoneItem) Items.register(
                RegistryKey.of(RegistryKeys.ITEM, Global.ModIdentifier("philosophers_stone")),
                PhilosopherStoneItem::new,
                new Item.Settings().maxDamage(20));
        // Item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(
                entries -> entries.add(PHILOSPHER_STONE_ITEM));
    }
}
