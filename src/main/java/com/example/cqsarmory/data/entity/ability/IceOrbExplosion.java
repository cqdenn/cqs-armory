package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.entity.spells.ice_tomb.IceTombEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Optional;

public class IceOrbExplosion extends AoeEntity {

    public IceOrbExplosion(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCircular();
    }

    public IceOrbExplosion(Level level, LivingEntity owner, float damage, float radius) {
        this(EntityRegistry.ICE_ORB_EXPLOSION.get(), level);
        setOwner(owner);
        this.setRadius(radius);
        this.setDamage(damage);
        this.setLevel(level);
    }

    public int waitTime = 20;

    @Override
    public void tick() {
        var level = this.level();
        float radius = getRadius();
        float speed = 0.06f + 0.01f * radius;
        var x = this.position().x;
        var y = this.position().y;
        var z = this.position().z;

        if (tickCount == 1) {this.playSound(SoundEvents.CREEPER_PRIMED, 1, 0.1f);}
        if (tickCount < waitTime) {level.addParticle(ParticleTypes.FLAME, x, y, z, 0, 0, 0);}
        if (tickCount == waitTime) {
            this.playSound(SoundEvents.GENERIC_EXPLODE.value(), 1, Utils.random.nextIntBetweenInclusive(8, 12) * .1f);
            if (level.isClientSide) {
                int cloudDensity = 25 + (int) (25 * radius);
                for (int i = 0; i < cloudDensity; i++) {
                    Vec3 posOffset = Utils.getRandomVec3(1).scale(radius * .4f);
                    Vec3 motion = posOffset.normalize().scale(speed * .5f);
                    posOffset = posOffset.add(motion.scale(Utils.getRandomScaled(1)));
                    motion = motion.add(Utils.getRandomVec3(0.02));
                    level.addParticle(ParticleRegistry.SNOW_DUST.get(), x + posOffset.x, y + posOffset.y, z + posOffset.z, motion.x, motion.y, motion.z);
                }

                level.addParticle(new BlastwaveParticleOptions(SchoolRegistry.ICE.get().getTargetingColor(), radius + 1), x, y, z, 0, 0, 0);

            } else {
                DamageSource damageSource = level.damageSources().explosion(this.getOwner(), this);
                var entities = level.getEntities(this, new AABB(this.position(), this.position()).inflate(radius, radius, radius), (targeted) -> !DamageSources.isFriendlyFireBetween(getOwner(), targeted) || targeted instanceof MomentumOrb);
                for (Entity target : entities) {
                    if (target instanceof LivingEntity && Utils.hasLineOfSight(target.level(), this.position(), target.getBoundingBox().getCenter(), true)) {
                        Vec3 spawn = target.position();
                        IceTombEntity iceTombEntity = new IceTombEntity(level, getOwner());
                        iceTombEntity.setEvil();
                        iceTombEntity.moveTo(spawn);
                        iceTombEntity.setLifetime(100);
                        level.addFreshEntity(iceTombEntity);
                        target.startRiding(iceTombEntity, true);
                    } else if (target instanceof MomentumOrb) {
                        target.hurt(damageSource, damage);
                    }
                }
                entities.clear();
            }
        }
        else if (tickCount > waitTime) {discard();}
    }

    @Override
    public void applyEffect(LivingEntity target) {

    }

    @Override
    public float getParticleCount() {
        return 0;
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }


}

