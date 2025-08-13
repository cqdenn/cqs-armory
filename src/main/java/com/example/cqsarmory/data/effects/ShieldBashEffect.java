package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ShieldBashEffect extends MobEffect {
    public ShieldBashEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public static final float SHIELD_BASH_ATTACK_DAMAGE_MULTIPLIER = 0.5f;

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        List<Entity> list = livingEntity.level().getEntities(livingEntity, livingEntity.getBoundingBox().inflate(1.2));
        var damageSource = CQSpellRegistry.SHIELD_BASH_SPELL.get().getDamageSource(livingEntity);
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity instanceof LivingEntity target && !DamageSources.isFriendlyFireBetween(livingEntity, target)) {
                    target.hurt(damageSource, (float) livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * SHIELD_BASH_ATTACK_DAMAGE_MULTIPLIER);
                    target.addEffect(new MobEffectInstance(MobEffectRegistry.STUNNED, 20 * (amplifier + 1), 100, false, false, true));
                    livingEntity.setDeltaMovement(0, 0, 0);
                    livingEntity.hurtMarked = true;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
