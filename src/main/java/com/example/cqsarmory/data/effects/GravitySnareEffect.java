package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GravitySnareEffect extends MobEffect {
    public GravitySnareEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        int duration = livingEntity.getEffect(MobEffectRegistry.GRAVITY_SNARE).getDuration();
        if (duration > 97) {
            livingEntity.setDeltaMovement(0, 1, 0);
            livingEntity.hurtMarked = true;
        } else if (duration == 97) {
            livingEntity.setDeltaMovement(0, 0, 0);
            livingEntity.hurtMarked = true;
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
       return true;
    }
}
