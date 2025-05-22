package net.faaf.testmod.item;

import net.faaf.testmod.TestMod;
import net.faaf.testmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup LUCKY_CARDS_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TestMod.MOD_ID, "lucky_cards_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.DIAMOND_CARD))
                    .displayName(Text.translatable("itemgroup.testmod.lucky_cards_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.IRON_CARD);
                        entries.add(ModItems.GOLD_CARD);
                        entries.add(ModItems.DIAMOND_CARD);
                    }).build());

    public static final ItemGroup LUCKY_CARDS_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TestMod.MOD_ID, "lucky_cards_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.PRISMATIC_BLOCK))
                    .displayName(Text.translatable("itemgroup.testmod.lucky_cards_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.PRISMATIC_BLOCK);
                    }).build());

    public static void registerItemGroups() {
        TestMod.LOGGER.info("Registering Item Groups for " + TestMod.MOD_ID);
    }
}
