package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.ThrownItemProjectile;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfileKeyPair;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwordQuiver extends SimpleDescriptiveQuiver {
    public SwordQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        double newDamage = (arrowDmg * 0.5) + ((shooter.getAttributeValue(Attributes.ATTACK_DAMAGE) - 1) * 0.75);
        ThrownItemProjectile projectile = new ThrownItemProjectile(shooter.level(), new ItemStack(Items.IRON_SWORD), 0.1, 0.75);
        projectile.copyStats(arrow, shooter, (float) newDamage);
        return projectile;
    }
}
