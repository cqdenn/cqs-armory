package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;

import java.util.Optional;

public class GenericMageAOE extends AoeEntity {
    public GenericMageAOE(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GenericMageAOE(Level level, LivingEntity owner, float damage, float radius) {
        this(EntityRegistry.GENERIC_MAGE_AOE.get(), level);
        setOwner(owner);
        this.setRadius(radius);
        this.setDamage(damage);
        this.setLevel(level);
    }

    public final int waitTime = 20;

    @Override
    public void tick() {
        if (tickCount < waitTime) {
            this.moveTo(getOwner().position());

            float radius = getRadius();
            Vector3f center = new Vector3f(1, 1f, 1f);
            var x = this.position().x;
            var y = this.position().y;
            var z = this.position().z;
            Level level = this.level();
            DamageSource damageSource = new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.MAGIC).get(), this, getOwner());
            var entities = level.getEntities(this, new AABB(this.position(), this.position()).inflate(radius, radius, radius), (targeted) -> !DamageSources.isFriendlyFireBetween(getOwner(), targeted));
            if (level.getGameTime() % 20 == 0) {

                level.addParticle(new BlastwaveParticleOptions(center, radius), x, y + .165f, z, 0, 0, 0);
                level.addParticle(new BlastwaveParticleOptions(center, radius), x, y + .135f, z, 0, 0, 0);
                level.addParticle( new BlastwaveParticleOptions(center, radius * 1.02f), x, y + .135f, z, 0, 0, 0);
                level.addParticle( new BlastwaveParticleOptions(center, radius * 0.98f), x, y + .135f, z, 0, 0, 0);

                for (Entity target : entities) {
                    if (target instanceof LivingEntity) {
                        target.hurt(damageSource, damage);
                    }
                }
                entities.clear();
            }
        }else if (tickCount > waitTime) {
            discard();
            AbilityData.get(getOwner()).hasMageAOE = false;
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
