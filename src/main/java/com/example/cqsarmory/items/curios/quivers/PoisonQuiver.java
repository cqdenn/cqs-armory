package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

public class PoisonQuiver extends QuiverItem {
    public PoisonQuiver(Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
    }

    @Override
    public Projectile getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        if (arrow instanceof Arrow newArrow) {
            newArrow.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
            return newArrow;
        }
        if (arrow instanceof AbilityArrow newArrow) {
            newArrow.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
            return newArrow;
        }
        return arrow;
    }
}
