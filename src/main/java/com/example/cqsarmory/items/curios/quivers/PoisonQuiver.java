package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.FireworkProjectile;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PoisonQuiver extends SimpleDescriptiveQuiver {
    public PoisonQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        AbilityArrow abilityArrow = new AbilityArrow(shooter.level());
        abilityArrow.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
        abilityArrow.copyStats(arrow, shooter, arrowDmg);
        return abilityArrow;
    }
}
