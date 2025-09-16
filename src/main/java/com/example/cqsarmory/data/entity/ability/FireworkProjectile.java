package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.DamageTypes;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;

import java.util.List;
import java.util.Random;

public class FireworkProjectile extends FireworkRocketEntity {
    /*private static final EntityDataAccessor<Float> ID_SCALE = SynchedEntityData.defineId(FireworkProjectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.defineId(FireworkProjectile.class, EntityDataSerializers.BYTE);*/

    public FireworkProjectile(Level level, ItemStack stack, Entity shooter, double x, double y, double z, boolean shotAtAngle, float damage) {
        super(level, stack, shooter, x, y, z, shotAtAngle);
        this.damage = damage;
        this.life = 0;
        this.lifetime = 60;
    }

    /*public FireworkProjectile(Level level, ItemStack stack, Entity shooter, double x, double y, double z, boolean shotAtAngle, float damage, float scale) {
        super(level, stack, shooter, x, y, z, shotAtAngle);
        this.damage = damage;
        this.life = 0;
        this.lifetime = 60;
        this.setScale(scale);
    }*/

    private final float damage;
    private int life;
    private final int lifetime;

    /*@Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_SCALE, 2f);
        builder.define(ID_FLAGS, (byte)0);
    }*/

    public float getDamage() {
        return damage;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.isShotAtAngle()) {
            double d2 = this.horizontalCollision ? 1.0 : 1.15;
            this.setDeltaMovement(this.getDeltaMovement().multiply(d2, 1.0, d2).add(0.0, 0.04, 0.0));
        }

        Vec3 vec33 = this.getDeltaMovement();
        this.move(MoverType.SELF, vec33);
        this.setDeltaMovement(vec33);


        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (!this.noPhysics) {
            this.hitTargetOrDeflectSelf(hitresult);
            this.hasImpulse = true;
        }

        this.updateRotation();
        if (this.life == 0 && !this.isSilent()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.AMBIENT, 3.0F, 1.0F);
        }

        this.life++;
        if (this.level().isClientSide && this.life % 2 < 2) {
            this.level()
                    .addParticle(
                            ParticleTypes.FIREWORK,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            this.random.nextGaussian() * 0.05,
                            -this.getDeltaMovement().y * 0.5,
                            this.random.nextGaussian() * 0.05
                    );
        }

        if (!this.level().isClientSide && this.life > this.lifetime) {
            this.explode();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            this.explode();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        BlockPos blockpos = new BlockPos(result.getBlockPos());
        this.level().getBlockState(blockpos).entityInside(this.level(), blockpos, this);
        if (!this.level().isClientSide()) {
            this.explode();
        }

        super.onHitBlock(result);
    }

    private void explode() {
        this.level().broadcastEntityEvent(this, (byte) 17);
        this.gameEvent(GameEvent.EXPLODE, this.getOwner());
        this.dealExplosionDamage();
        this.discard();
    }

    private void dealExplosionDamage() {
        Vec3 hitPos = this.position();
        Level level = this.level();
        double explosionRadius = 2;
        for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, new AABB(hitPos.subtract(explosionRadius, explosionRadius, explosionRadius), hitPos.add(explosionRadius, explosionRadius, explosionRadius)))) {
            if (livingentity.isAlive() && livingentity.isPickable() && Utils.hasLineOfSight(level, hitPos, livingentity.getBoundingBox().getCenter(), true)) {
                DamageSources.applyDamage(livingentity, this.getDamage(), new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.VOLCANO).get(), this, this.getOwner()));
            }
        }
    }

    /*public float getScale() {
        return this.entityData.get(ID_SCALE);
    }

    public void setScale(float scale) {
        this.entityData.set(ID_SCALE, scale);
    }*/
}
