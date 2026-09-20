package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.utils.CQtils;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GravitySnareEffect extends NonCurableEffect {
    public GravitySnareEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        int left = livingEntity.getEffect(MobEffectRegistry.GRAVITY_SNARE).getDuration();
        int duration = CQtils.getAOETimeSeconds(livingEntity);
        if (left > duration - 3) {
            livingEntity.setDeltaMovement(0, 1, 0);
            livingEntity.hurtMarked = true;
        } else if (left == duration - 3) {
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
