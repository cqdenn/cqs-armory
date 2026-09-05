package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.utils.CQtils;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class HealingMageAOEEffect extends NonCurableEffect {
    public HealingMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity.level().getGameTime() % 20 == 0) || livingEntity.level().isClientSide) return true;
        CQtils.doHealingMageAOE(livingEntity, livingEntity.position().add(0, 1, 0), 8, 2);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
