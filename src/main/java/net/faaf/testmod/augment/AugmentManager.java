package net.faaf.testmod.augment;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AugmentManager {

    public static final Identifier MELEE_DAMAGE_BOOST_ID = Identifier.of("testmod", "melee_damage_boost");
    public static final Identifier RESISTANCE_ID = Identifier.of("testmod", "resistance_boost");
    public static final Identifier SPEED_ID = Identifier.of("testmod", "speed_boost");

    public static void applyMeleeDamageBoost(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addPersistentModifier(
                new EntityAttributeModifier(MELEE_DAMAGE_BOOST_ID, 2.0, EntityAttributeModifier.Operation.ADD_VALUE));
        player.sendMessage(Text.of("Melee damage boost applied!"), true);
    }

    public static void applyResistance(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addPersistentModifier(
                new EntityAttributeModifier(RESISTANCE_ID, 2.0, EntityAttributeModifier.Operation.ADD_VALUE));
        player.sendMessage(Text.of("Resistance boost applied!"), true);
    }

    public static void applySpeed(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(
                new EntityAttributeModifier(SPEED_ID, 0.1, EntityAttributeModifier.Operation.ADD_VALUE));
        player.sendMessage(Text.of("Speed boost applied!"), true);
    }

    /**
     * Returns a brief description of the given augment.
     *
     * @param augmentId The ID of the augment.
     * @return A brief description of the augment.
     */
    public static String getAugmentDescription(Identifier augmentId) {
        if (augmentId.equals(MELEE_DAMAGE_BOOST_ID)) {
            return "Increases melee damage by 2 points.";
        } else if (augmentId.equals(RESISTANCE_ID)) {
            return "Increases armor by 2 points.";
        } else if (augmentId.equals(SPEED_ID)) {
            return "Increases movement speed by 10%.";
        } else {
            return "Unknown augment.";
        }
    }
}
