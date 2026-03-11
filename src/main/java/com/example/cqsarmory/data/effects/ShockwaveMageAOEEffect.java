package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.data.entity.ability.HellfireAOEEntity;
import com.example.cqsarmory.registry.DamageTypes;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.particle.ZapParticleOption;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ShockwaveMageAOEEffect extends NonCurableEffect {
    public ShockwaveMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private boolean canHit(Entity owner, Entity target) {
        return target != owner && target.isAlive() && target.isPickable() && !target.isSpectator();
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.level().isClientSide) return true;
        float radius = 5;
        Level level = livingEntity.level();
        float damage = (float) (60 * livingEntity.getAttributeValue(AttributeRegistry.SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.LIGHTNING_SPELL_POWER));

        level.playSound(null, livingEntity.blockPosition(), SoundRegistry.SHOCKWAVE_CAST.get(), livingEntity.getSoundSource());
        Vector3f edge = new Vector3f(.7f, 1f, 1f);
        Vector3f center = new Vector3f(1, 1f, 1f);
        //this is immaculately stupid
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(edge, radius * 1.02f), livingEntity.getX(), livingEntity.getY() + .15f, livingEntity.getZ(), 1, 0, 0, 0, 0, true);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(edge, radius * 0.98f), livingEntity.getX(), livingEntity.getY() + .15f, livingEntity.getZ(), 1, 0, 0, 0, 0, true);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(center, radius), livingEntity.getX(), livingEntity.getY() + .165f, livingEntity.getZ(), 1, 0, 0, 0, 0, true);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(center, radius), livingEntity.getX(), livingEntity.getY() + .135f, livingEntity.getZ(), 1, 0, 0, 0, 0, true);
        MagicManager.spawnParticles(level, ParticleHelper.ELECTRICITY, livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 80, .25, .25, .25, 0.7f + radius * .1f, false);
        CameraShakeManager.addCameraShake(new CameraShakeData(level, 30, livingEntity.position(), radius * 2));

        Vec3 start = livingEntity.getBoundingBox().getCenter();
        var dummyLightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        dummyLightningBolt.setDamage(0);
        dummyLightningBolt.setVisualOnly(true);
        level.getEntities(livingEntity, livingEntity.getBoundingBox().inflate(radius, radius, radius), (target) -> !DamageSources.isFriendlyFireBetween(target, livingEntity) && Utils.hasLineOfSight(level, livingEntity, target, true)).forEach(target -> {
            if (target instanceof LivingEntity living && canHit(livingEntity, target) && living.distanceToSqr(livingEntity) < radius * radius) {
                Vec3 dest = living.getBoundingBox().getCenter();
                ((ServerLevel) level).sendParticles(new ZapParticleOption(dest), start.x, start.y, start.z, 1, 0, 0, 0, 0);
                MagicManager.spawnParticles(level, ParticleHelper.ELECTRICITY, living.getX(), living.getY() + living.getBbHeight() / 2, living.getZ(), 10, living.getBbWidth() / 3, living.getBbHeight() / 3, living.getBbWidth() / 3, 0.1, false);
                target.hurt(new DamageSource(livingEntity.damageSources().damageTypes.getHolder(DamageTypes.SHOCKWAVE_MAGE_AOE).get(), null, livingEntity), damage);
                if (target instanceof Creeper creeper) {
                    creeper.thunderHit((ServerLevel) level, dummyLightningBolt);
                }
            }
        });
        for (int i = 0; i < 7; i++) {
            Vec3 dest = start.add(Utils.getRandomVec3(1).multiply(4, 2.5, 4).add(0, 4, 0));
            ((ServerLevel) level).sendParticles(new ZapParticleOption(dest), start.x, start.y, start.z, 1, 0, 0, 0, 0);
        }


        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration == 1;
    }
}
