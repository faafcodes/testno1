package net.faaf.testmod.item;

import net.faaf.testmod.TestMod;
import net.faaf.testmod.ui.CustomScreen;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ModItems {

    public static final Item IRON_CARD = registerItem("iron_card", new CardItem(new Item.Settings()));
    public static final Item GOLD_CARD = registerItem("gold_card", new CardItem(new Item.Settings()));
    public static final Item DIAMOND_CARD = registerItem("diamond_card", new CardItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TestMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TestMod.LOGGER.info("Registering Mod Items for " + TestMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(IRON_CARD);
            entries.add(GOLD_CARD);
            entries.add(DIAMOND_CARD);
        });
    }

    // Classe personalizada para abrir a tela
    public static class CardItem extends Item {
        public CardItem(Settings settings) {
            super(settings);
        }

        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
            if (world.isClient) {
                String augmentType;

                if (this == IRON_CARD) {
                    augmentType = "iron";
                } else if (this == GOLD_CARD) {
                    augmentType = "gold";
                } else if (this == DIAMOND_CARD) {
                    augmentType = "diamond";
                } else {
                    augmentType = "unknown"; // Caso inesperado (você pode lançar uma exceção aqui, se preferir)
                }

                MinecraftClient.getInstance().setScreen(new CustomScreen(augmentType));
            }
            return TypedActionResult.success(player.getStackInHand(hand));
        }


        @Override
        public boolean hasGlint(ItemStack stack) {
            return true; // Faz o item brilhar
        }
    }
}
