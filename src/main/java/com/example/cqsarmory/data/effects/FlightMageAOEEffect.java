package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class FlightMageAOEEffect extends NonCurableEffect {
    public FlightMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);
        livingEntity.level().playSound(null, livingEntity.blockPosition(), SoundRegistry.HOLY_CAST.get(), SoundSource.PLAYERS, 0.7f, 0.7f);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Vec3 from = livingEntity.position().add(0, 1, 0);
        var x = from.x;
        var y = from.y;
        var z = from.z;
        Level level = livingEntity.level();
        int radius = 5;
        Vector3f center = new Vector3f(0, 0, 1);

        var entities = level.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(radius), target -> DamageSources.isFriendlyFireBetween(livingEntity, target) && Utils.hasLineOfSight(level, livingEntity.position(), target.position(), false));
        entities.forEach(target -> {
            target.addEffect(new MobEffectInstance(MobEffectRegistry.FLIGHT, CQtils.getAOETimeSeconds(livingEntity) * 20, 0, false, false, true));
        });
        if (!livingEntity.level().isClientSide) {
            CQtils.genericMageAOEParticlesServer(livingEntity.level(), radius, center, from);
        }
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration == 1;
    }
}