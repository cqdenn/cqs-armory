package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class LightningArrow extends AbilityArrow{
    public LightningArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public LightningArrow(Level level) {
        this(EntityRegistry.LIGHTNING_ARROW.get(), level);
    }

    int zapTimer = 0;

    @Override
    public void tick() {
        super.tick();
        if (this.inGround) {
            zapTimer++;
            if (this.zapTimer % 20 == 0) {
                Vec3 pos = this.getPosition(0);
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level());
                lightningBolt.setVisualOnly(true);
                lightningBolt.setDamage(0);
                lightningBolt.setPos(pos);
                level().addFreshEntity(lightningBolt);

                float radius = 4;
                float damage = (float) this.getBaseDamage();
                if (getOwner() instanceof LivingEntity living) damage *= (float) living.getAttributeValue(AttributeRegistry.LIGHTNING_SPELL_POWER);
                float finDmg = damage;
                var source = SpellDamageSource.source(lightningBolt, getOwner(), SpellRegistry.LIGHTNING_BOLT_SPELL.get());
                var finalpos = pos;
                level().getEntities(getOwner(), AABB.ofSize(finalpos, radius * 2, radius * 2, radius * 2), (target) -> this.canHit(getOwner(), target)).forEach(target -> {
                    double distance = target.distanceToSqr(finalpos);
                    if (distance < radius * radius && Utils.hasLineOfSight(level(), finalpos.add(0, 2, 0), target.getBoundingBox().getCenter(), true)) {
                        float finalDamage = (float) (finDmg * (1 - distance / (radius * radius)));
                        DamageSources.applyDamage(target, finalDamage, source);
                        if (target instanceof Creeper creeper) {
                            creeper.thunderHit((ServerLevel) level(), lightningBolt);
                        }
                    }
                });
            }
            if (zapTimer >= 21) {
                discard();
            }
        }
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
            this.level().addParticle(ParticleHelper.ELECTRICITY, true, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    private boolean canHit(Entity owner, Entity target) {
        return target != owner && target.isAlive() && target.isPickable() && !target.isSpectator();
    }
}
