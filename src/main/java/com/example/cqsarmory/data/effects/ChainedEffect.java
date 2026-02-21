package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.netty.handler.ssl.util.TrustManagerFactoryWrapper;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.effect.ISyncedMobEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ChainedEffect extends MobEffect implements ISyncedMobEffect {
    public static final int CHAINED_EFFECT_MAX_CHAIN_LENGTH = 6;

    public ChainedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        super.onEffectAdded(livingEntity, amplifier);
        if (!livingEntity.level().isClientSide && livingEntity.isAddedToLevel() && livingEntity.level().isLoaded(livingEntity.blockPosition())) {
            DamageData.get(livingEntity).chainWhipLocation = Utils.moveToRelativeGroundLevel(livingEntity.level(), livingEntity.position(), CHAINED_EFFECT_MAX_CHAIN_LENGTH);
            livingEntity.syncData(EntityDataAttachmentRegistry.DAMAGE_DATA);
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity living, int amplifier) {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
