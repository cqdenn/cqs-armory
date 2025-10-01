package com.example.cqsarmory.items.curios;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class OnHitBrand extends SimpleDescriptiveBrand{
    public OnHitBrand(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage, DamageSource source) {
    }
}
