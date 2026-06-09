package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import io.redspace.ironsspellbooks.entity.spells.snowball.FrostField;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

public class FireArrow extends AbilityArrow{

    public FireArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public FireArrow(Level level) {
        this(EntityRegistry.FIRE_ARROW.get(), level);
    }

    @Override
    public void customCritParticles() {
        Vec3 vec3 = getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        var count = 4;
        for (int i = 0; i < count; i++) {
            Vec3 random = Utils.getRandomVec3(1).add(vec3.normalize()).scale(0.125);
            var f = i / ((float) count);
            var x = Mth.lerp(f, d0, this.getX());
            var y = Mth.lerp(f, d1, this.getY()) - .4;
            var z = Mth.lerp(f, d2, this.getZ());
            this.level().addParticle(ParticleHelper.FIRE, true, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        if (target instanceof PartEntity<?> part) target = part.getParent();
        level().getEntities(this, target.getBoundingBox().inflate(0.1f), (entity) ->
                entity instanceof FireField fire
                        && DamageSources.isFriendlyFireBetween(fire.getOwner(), getOwner()))
                .forEach(entity -> {
                    if (entity instanceof FireField fire) {
                        float radius = 2.5f;
                        if (fire.getRadius() <= radius) {
                            Entity owner = getOwner();
                            Vec3 targetArea = fire.position();
                            MagicManager.spawnParticles(level(), ParticleTypes.LAVA, targetArea.x, targetArea.y, targetArea.z, 25, 1, 1, 1, 1, true);
                            MagicManager.spawnParticles(level(), ParticleTypes.LAVA, targetArea.x, targetArea.y + 1, targetArea.z, 25, .25, 1.5, .25, 1, false);
                            level().playSound(null, targetArea.x, targetArea.y, targetArea.z, SoundRegistry.FIERY_EXPLOSION.get(), SoundSource.PLAYERS, 2, Utils.random.nextIntBetweenInclusive(8, 12) * .1f);
                            var radiusSqr = radius * radius;
                            float damage = (float) this.getBaseDamage();
                            var source = SpellDamageSource.source(owner, owner, SpellRegistry.SCORCH_SPELL.get());
                            level().getEntitiesOfClass(LivingEntity.class, new AABB(targetArea.subtract(radius, radius, radius), targetArea.add(radius, radius, radius)),
                                            livingEntity -> livingEntity != owner &&
                                                    horizontalDistanceSqr(livingEntity, targetArea) < radiusSqr &&
                                                    livingEntity.isPickable() &&
                                                    !DamageSources.isFriendlyFireBetween(livingEntity, owner) &&
                                                    Utils.hasLineOfSight(level(), targetArea.add(0, 1.5, 0), livingEntity.getBoundingBox().getCenter(), true))
                                    .forEach(livingEntity -> {
                                        DamageSources.applyDamage(livingEntity, damage, source);
                                        DamageSources.ignoreNextKnockback(livingEntity);
                                    });
                            fire.discard();

                        } else {
                            fire.setRadius(fire.getRadius() - 0.5f);
                            fire.setDamage(fire.getDamage() * 1.25f);
                        }
                    }
                });
    }

    private float horizontalDistanceSqr(LivingEntity livingEntity, Vec3 vec3) {
        var dx = livingEntity.getX() - vec3.x;
        var dz = livingEntity.getZ() - vec3.z;
        return (float) (dx * dx + dz * dz);
    }
}
