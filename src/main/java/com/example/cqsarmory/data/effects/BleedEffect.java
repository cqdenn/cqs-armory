package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.registry.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedEffect extends MobEffect {
    public BleedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    public static final float DAMAGE_PER_STACK = 5;

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        /*var dTypeReg = livingEntity.damageSources().damageTypes;
        var dType = dTypeReg.getHolder(net.neoforged.neoforge.common.NeoForgeMod.POISON_DAMAGE).orElse(dTypeReg.getHolderOrThrow(net.minecraft.world.damagesource.DamageTypes.MAGIC));*/
        DamageSource bleeding = new DamageSource(livingEntity.damageSources().damageTypes.getHolder(DamageTypes.BLEEDING).get());
        float damage = DAMAGE_PER_STACK * (amplifier + 1);
        livingEntity.hurt(bleeding, damage);

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
