package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.utils.CQtils;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GenericMageAOEEffect extends NonCurableEffect {
    public GenericMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity.level().getGameTime() % 20 == 0) || livingEntity.level().isClientSide) return true;
        CQtils.doGenericMageAOE(livingEntity, null, livingEntity.position(), 5, 10);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
