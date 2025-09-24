package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PoisonCoating extends OnHitCoating {
    public PoisonCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        target.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
    }
}
