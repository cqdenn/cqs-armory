package com.example.cqsarmory.items.curios;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class OnHitBooster extends SimpleDescriptiveBooster{
    public OnHitBooster(Properties properties) {
        super(properties, "booster");
    }

    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage, DamageSource source) {
    }
}
