package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import io.redspace.ironsspellbooks.effect.ISyncedMobEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ChainedEffect extends MobEffect implements ISyncedMobEffect {
    public ChainedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        super.onEffectAdded(livingEntity, amplifier);
        if (!livingEntity.level().isClientSide && livingEntity.isAddedToLevel() && livingEntity.level().isLoaded(livingEntity.blockPosition())) {
            DamageData.get(livingEntity).chainWhipLocation = livingEntity.position();
            livingEntity.syncData(EntityDataAttachmentRegistry.DAMAGE_DATA);
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
