package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class GravityMageAOEEffect extends NonCurableEffect {
    public GravityMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        Level level = livingEntity.level();
        int radius = 5;
        Vector3f center = new Vector3f(.72f, .1f, .8f);
        Vec3 from = livingEntity.position().add(0, 1, 0);
        var x = from.x;
        var y = from.y;
        var z = from.z;
        var entities = level.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(radius), target -> !DamageSources.isFriendlyFireBetween(livingEntity, target) && Utils.hasLineOfSight(level, livingEntity.position(), target.position(), true));
        entities.forEach(target -> {
            target.addEffect(new MobEffectInstance(MobEffectRegistry.GRAVITY_SNARE, CQtils.getAOETimeSeconds(livingEntity) * 20, 0, false, false, true));
        });
        level.addParticle(new BlastwaveParticleOptions(center, radius), x, y + .165f, z, 0, 0, 0);
        level.addParticle(new BlastwaveParticleOptions(center, radius), x, y + .135f, z, 0, 0, 0);
        level.addParticle(new BlastwaveParticleOptions(center, radius * 1.02f), x, y + .135f, z, 0, 0, 0);
        level.addParticle(new BlastwaveParticleOptions(center, radius * 0.98f), x, y + .135f, z, 0, 0, 0);
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration == 1;
    }
}