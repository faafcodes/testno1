package net.faaf.testmod.augment;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AugmentManager {

    // Iron Augments
    public static final Identifier MELEE_DAMAGE_BOOST_ID = Identifier.of("testmod", "iron_melee_damage_boost");
    public static final Identifier RESISTANCE_ID = Identifier.of("testmod", "iron_resistance_boost");
    public static final Identifier SPEED_ID = Identifier.of("testmod", "iron_speed_boost");
    public static final Identifier IRON_HEALTH_REGEN_ID = Identifier.of("testmod", "iron_health_regen");
    public static final Identifier IRON_DAMAGE_RESIST_ID = Identifier.of("testmod", "iron_damage_resist");

    // Gold Augments
    public static final Identifier GOLD_STRENGTH_BOOST_ID = Identifier.of("testmod", "gold_strength_boost");
    public static final Identifier GOLD_HEALTH_BOOST_ID = Identifier.of("testmod", "gold_health_boost");
    public static final Identifier GOLD_FIRE_RESISTANCE_ID = Identifier.of("testmod", "gold_fire_resistance");
    public static final Identifier GOLD_ATTACK_SPEED_ID = Identifier.of("testmod", "gold_attack_speed");
    public static final Identifier GOLD_LUCK_ID = Identifier.of("testmod", "gold_luck");

    // Diamond Augments
    public static final Identifier DIAMOND_REGENERATION_ID = Identifier.of("testmod", "diamond_regeneration");
    public static final Identifier DIAMOND_SPEED_BOOST_ID = Identifier.of("testmod", "diamond_speed_boost");
    public static final Identifier DIAMOND_DAMAGE_BOOST_ID = Identifier.of("testmod", "diamond_damage_boost");
    public static final Identifier DIAMOND_DEFENSE_ID = Identifier.of("testmod", "diamond_defense");
    public static final Identifier DIAMOND_WATER_BREATHING_ID = Identifier.of("testmod", "diamond_water_breathing");

    /**
     * Applies the augment effect to the player.
     */
    public static void applyAugment(PlayerEntity player, Identifier augmentId) {
        if (consumeItemInHand(player)) {
            switch (augmentId.toString()) {
                case "testmod:iron_melee_damage_boost" ->
                        player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                                .addPersistentModifier(new EntityAttributeModifier(MELEE_DAMAGE_BOOST_ID, 2.0, EntityAttributeModifier.Operation.ADD_VALUE));
                case "testmod:iron_resistance_boost" -> player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR)
                        .addPersistentModifier(new EntityAttributeModifier(RESISTANCE_ID, 2.0, EntityAttributeModifier.Operation.ADD_VALUE));
                case "testmod:iron_speed_boost" -> player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                        .addPersistentModifier(new EntityAttributeModifier(SPEED_ID, 0.1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                case "testmod:iron_health_regen" ->
                        player.sendMessage(Text.of("Iron health regeneration applied!"), true);
                case "testmod:iron_damage_resist" ->
                        player.sendMessage(Text.of("Iron damage resistance applied!"), true);

                // Gold Augments
                case "testmod:gold_strength_boost" ->
                        player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                                .addPersistentModifier(new EntityAttributeModifier(GOLD_STRENGTH_BOOST_ID, 5.0, EntityAttributeModifier.Operation.ADD_VALUE));
                case "testmod:gold_health_boost" -> player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
                        .addPersistentModifier(new EntityAttributeModifier(GOLD_HEALTH_BOOST_ID, 8.0, EntityAttributeModifier.Operation.ADD_VALUE));
                case "testmod:gold_fire_resistance" ->
                        player.sendMessage(Text.of("Gold fire resistance applied!"), true);
                case "testmod:gold_attack_speed" -> player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED)
                        .addPersistentModifier(new EntityAttributeModifier(GOLD_ATTACK_SPEED_ID, 0.2, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                case "testmod:gold_luck" -> player.sendMessage(Text.of("Gold luck boost applied!"), true);

                // Diamond Augments
                case "testmod:diamond_regeneration" ->
                        player.sendMessage(Text.of("Diamond regeneration applied!"), true);
                case "testmod:diamond_speed_boost" ->
                        player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                                .addPersistentModifier(new EntityAttributeModifier(DIAMOND_SPEED_BOOST_ID, 0.2, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                case "testmod:diamond_damage_boost" ->
                        player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                                .addPersistentModifier(new EntityAttributeModifier(DIAMOND_DAMAGE_BOOST_ID, 10.0, EntityAttributeModifier.Operation.ADD_VALUE));
                case "testmod:diamond_defense" -> player.sendMessage(Text.of("Diamond defense boost applied!"), true);
                case "testmod:diamond_water_breathing" ->
                        player.sendMessage(Text.of("Diamond water breathing applied!"), true);
            }
        }
    }

    /**
     * Returns a list of 3 random augments filtered by type.
     *
     * @param type The type of augments to filter (iron, gold, diamond).
     * @return List of augment Identifiers.
     */
    public static List<Identifier> getRandomAugmentsByType(String type) {
        List<Identifier> filteredAugments = switch (type.toLowerCase()) {
            case "iron" ->
                    List.of(MELEE_DAMAGE_BOOST_ID, RESISTANCE_ID, SPEED_ID, IRON_HEALTH_REGEN_ID, IRON_DAMAGE_RESIST_ID);
            case "gold" ->
                    List.of(GOLD_STRENGTH_BOOST_ID, GOLD_HEALTH_BOOST_ID, GOLD_FIRE_RESISTANCE_ID, GOLD_ATTACK_SPEED_ID, GOLD_LUCK_ID);
            case "diamond" ->
                    List.of(DIAMOND_REGENERATION_ID, DIAMOND_SPEED_BOOST_ID, DIAMOND_DAMAGE_BOOST_ID, DIAMOND_DEFENSE_ID, DIAMOND_WATER_BREATHING_ID);
            default -> throw new IllegalArgumentException("Invalid augment type: " + type);
        };

        List<Identifier> randomAugments = new ArrayList<>(filteredAugments);
        Collections.shuffle(randomAugments);
        return randomAugments.subList(0, Math.min(3, randomAugments.size()));
    }

    /**
     * Returns a brief description of the given augment.
     */
    public static String getAugmentDescription(Identifier augmentId) {
        if (augmentId.equals(MELEE_DAMAGE_BOOST_ID)) {
            return "Increases melee damage by 2 points (Iron Tier).";
        } else if (augmentId.equals(RESISTANCE_ID)) {
            return "Increases resistance by 2 points (Iron Tier).";
        } else if (augmentId.equals(SPEED_ID)) {
            return "Increases movement speed by 10% (Iron Tier).";
        } else if (augmentId.equals(IRON_HEALTH_REGEN_ID)) {
            return "Regenerates health over time (Iron Tier).";
        } else if (augmentId.equals(IRON_DAMAGE_RESIST_ID)) {
            return "Reduces damage taken (Iron Tier).";
        } else if (augmentId.equals(GOLD_STRENGTH_BOOST_ID)) {
            return "Increases strength by 5 points (Gold Tier).";
        } else if (augmentId.equals(GOLD_HEALTH_BOOST_ID)) {
            return "Increases maximum health by 8 points (Gold Tier).";
        } else if (augmentId.equals(GOLD_FIRE_RESISTANCE_ID)) {
            return "Grants fire resistance (Gold Tier).";
        } else if (augmentId.equals(GOLD_ATTACK_SPEED_ID)) {
            return "Increases attack speed by 20% (Gold Tier).";
        } else if (augmentId.equals(GOLD_LUCK_ID)) {
            return "Increases luck (Gold Tier).";
        } else if (augmentId.equals(DIAMOND_REGENERATION_ID)) {
            return "Regenerates health quickly (Diamond Tier).";
        } else if (augmentId.equals(DIAMOND_SPEED_BOOST_ID)) {
            return "Increases movement speed by 20% (Diamond Tier).";
        } else if (augmentId.equals(DIAMOND_DAMAGE_BOOST_ID)) {
            return "Increases melee damage by 10 points (Diamond Tier).";
        } else if (augmentId.equals(DIAMOND_DEFENSE_ID)) {
            return "Greatly increases defense (Diamond Tier).";
        } else if (augmentId.equals(DIAMOND_WATER_BREATHING_ID)) {
            return "Grants the ability to breathe underwater (Diamond Tier).";
        } else {
            return "Unknown augment.";
        }
    }


    /**
     * Consumes the item in the player's hand.
     */
    private static boolean consumeItemInHand(PlayerEntity player) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        if (!stack.isEmpty()) {
            stack.decrement(1);
            if (stack.isEmpty()) {
                player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            }
            return true;
        } else {
            player.sendMessage(Text.of("You must hold an augment item to use this!"), true);
            return false;
        }
    }

    public static void applyAugment(ClientPlayerEntity player, Identifier augmentId) {
        applyAugment((PlayerEntity) player, augmentId);
    }
}
