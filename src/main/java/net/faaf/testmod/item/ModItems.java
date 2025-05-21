package net.faaf.testmod.item;

import net.faaf.testmod.TestMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item IRON_CARD = registerItem("iron_card", new Item(new Item.Settings()));
    public static final Item GOLD_CARD = registerItem("gold_card", new Item(new Item.Settings()));
    public static final Item DIAMOND_CARD = registerItem("diamond_card", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TestMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        net.faaf.testmod.TestMod.LOGGER.info("Registering Mod Items for " + net.faaf.testmod.TestMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(IRON_CARD);
            entries.add(GOLD_CARD);
            entries.add(DIAMOND_CARD);
        });
    }
}
