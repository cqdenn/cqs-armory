package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.HellfireAOEEntity;
import com.example.cqsarmory.registry.DamageTypes;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlizzardMageAOEEffect extends NonCurableEffect {
    public BlizzardMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        AbilityData.get(livingEntity).firstBlizzardTick = true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.level().isClientSide) return true;
        float radius = 5;
        Level level = livingEntity.level();
        float damage = (float) (2 * livingEntity.getAttributeValue(AttributeRegistry.SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.ICE_SPELL_POWER));
        Vec3 from = livingEntity.position().add(0, 1, 0);
        var entities = level.getEntities(livingEntity, new AABB(from, from).inflate(radius, 1, radius), (targeted) -> !DamageSources.isFriendlyFireBetween(livingEntity, targeted));

        if (livingEntity.level().getGameTime() % 10 == 0) {
            level.playSound(null, livingEntity.blockPosition(), SoundEvents.ELYTRA_FLYING, livingEntity.getSoundSource(), 0.2f, 2f);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity target) {
                    target.hurt(new DamageSource(livingEntity.damageSources().damageTypes.getHolder(DamageTypes.BLIZZARD_MAGE_AOE).get(), null, livingEntity), damage);
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 11, 1, false, false, true)); //40% slow
                    target.setTicksFrozen(target.getTicksFrozen() + 30);
                }
            }
        }

        if (AbilityData.get(livingEntity).firstBlizzardTick) {
            AbilityData.get(livingEntity).firstBlizzardTick = false;
            for (int s = 0; s <= 100; s++) {
                float dx = Utils.random.nextFloat() * (Utils.random.nextBoolean() ? 1 : -1) * 2;
                float dy = Utils.random.nextFloat() * (Utils.random.nextBoolean() ? 1 : -1) * 2;
                float dz = Utils.random.nextFloat() * (Utils.random.nextBoolean() ? 1 : -1) * 2;
                MagicManager.spawnParticles(level, ParticleRegistry.SNOWFLAKE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 10, dx, dy, dz, 1, false);
            }
        }

        int particles = 100;
        Vec3 center = livingEntity.position().add(0, 1, 0);

        for (int i = 0; i < particles; i++) {

            double angle = i * Math.PI * 2 / particles;

            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            double y = center.y;

            MagicManager.spawnParticles(
                    level,
                    ParticleRegistry.SNOWFLAKE_PARTICLE.get(),
                    x, y, z,
                    1,
                    0, -0.02, 0,   // fall slowly
                    1,
                    false
            );
        }


        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
