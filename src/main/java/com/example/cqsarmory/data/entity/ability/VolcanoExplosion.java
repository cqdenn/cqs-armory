package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

import java.util.Optional;

public class VolcanoExplosion extends AoeEntity {
    public VolcanoExplosion(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCircular();
    }

    public VolcanoExplosion(Level level, LivingEntity owner, float damage, float radius) {
        this(EntityRegistry.VOLCANO_EXPLOSION.get(), level);
        setOwner(owner);
        this.setRadius(radius);
        this.setDamage(damage);
        this.setLevel(level);
    }

    public final int waitTime = 20;

    @Override
    public void tick() {
        var level = this.level();
        float radius = getRadius();
        if (tickCount == 1) {this.playSound(SoundEvents.CREEPER_PRIMED, 1, 0.1f);}
        if (tickCount == waitTime) {
            this.playSound(SoundEvents.GENERIC_EXPLODE.value(), 1, Utils.random.nextIntBetweenInclusive(8, 12) * .1f);
            if (level.isClientSide) {
                var x = this.position().x;
                var y = this.position().y;
                var z = this.position().z;
                //Blastwave
                level.addParticle(new BlastwaveParticleOptions(new Vector3f(1, .6f, 0.3f), radius + 1), x, y, z, 0, 0, 0);
                //Billowing wave
                int c = (int) (6.28 * radius) * 3;
                float step = 360f / c * Mth.DEG_TO_RAD;
                float speed = 0.06f + 0.01f * radius;
                for (int i = 0; i < c; i++) {
                    Vec3 vec3 = new Vec3(Mth.cos(step * i), 0, Mth.sin(step * i)).scale(speed);
                    Vec3 posOffset = Utils.getRandomVec3(.5f).add(vec3.scale(10));
                    vec3 = vec3.add(Utils.getRandomVec3(0.01));
                    level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x + posOffset.x, y + posOffset.y, z + posOffset.z, vec3.x, vec3.y, vec3.z);
                }
                //Smoke Cloud
                int cloudDensity = 50 + (int) (25 * radius);
                for (int i = 0; i < cloudDensity; i++) {
                    Vec3 posOffset = Utils.getRandomVec3(1).scale(radius * .4f);
                    Vec3 motion = posOffset.normalize().scale(speed * .5f);
                    posOffset = posOffset.add(motion.scale(Utils.getRandomScaled(1)));
                    motion = motion.add(Utils.getRandomVec3(0.02));
                    level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x + posOffset.x, y + posOffset.y, z + posOffset.z, motion.x, motion.y, motion.z);
                }
                //Fire Cloud
                for (int i = 0; i < cloudDensity; i += 2) {
                    Vec3 posOffset = Utils.getRandomVec3(1).scale(radius * .4f);
                    Vec3 motion = posOffset.normalize().scale(speed * .5f);
                    motion = motion.add(Utils.getRandomVec3(0.25));
                    level.addParticle(ParticleHelper.EMBERS, true, x + posOffset.x, y + posOffset.y, z + posOffset.z, motion.x, motion.y, motion.z);
                    level.addParticle(ParticleHelper.FIRE, x + posOffset.x * .5f, y + posOffset.y * .5f, z + posOffset.z * .5f, motion.x, motion.y, motion.z);
                }
                //Sparks
                for (int i = 0; i < cloudDensity; i += 2) {
                    Vec3 posOffset = Utils.getRandomVec3(radius).scale(.2f);
                    Vec3 motion = posOffset.normalize().scale(0.6);
                    motion = motion.add(Utils.getRandomVec3(0.18));
                    level.addParticle(ParticleHelper.FIERY_SPARKS, x + posOffset.x * .5f, y + posOffset.y * .5f, z + posOffset.z * .5f, motion.x, motion.y, motion.z);
                }

            }
            DamageSource volcano = new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.VOLCANO).get(), getOwner(), getOwner());
            var entities = level.getEntities(this, new AABB(this.position(), this.position()).inflate(radius, radius, radius), (targeted) -> !DamageSources.isFriendlyFireBetween(getOwner(), targeted));
            for (Entity target : entities) {
                if (target instanceof LivingEntity && Utils.hasLineOfSight(target.level(), this.position(), target.getBoundingBox().getCenter(), true)) {
                    target.hurt(volcano, damage);
                }
            }
            entities.clear();
        }else if (tickCount > waitTime) {
            discard();
        }
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
