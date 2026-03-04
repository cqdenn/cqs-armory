package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.UUID;

public class ScatterProjectile extends AbilityArrow{

    private static final EntityDataAccessor<Boolean> HAS_BOUNCED = SynchedEntityData.defineId(ScatterProjectile.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> INITIAL = SynchedEntityData.defineId(ScatterProjectile.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SCATTERS_LEFT = SynchedEntityData.defineId(ScatterProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOUNCES_LEFT = SynchedEntityData.defineId(ScatterProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(ScatterProjectile.class, EntityDataSerializers.INT);

    public ScatterProjectile(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ScatterProjectile(Level level, int scattersLeft, int bouncesLeft, boolean initial) {
        super(EntityRegistry.SCATTER_ARROW.get(), level);
        setScattersLeft(scattersLeft);
        setBouncesLeft(bouncesLeft);
        setHasBounced(false);
        setInitial(initial);
        setBounces(0);
    }

    public void setHasBounced (boolean hasBounced) {
        if (level().isClientSide) return;
        entityData.set(HAS_BOUNCED, hasBounced);
    }

    public boolean getHasBounced () {
        return entityData.get(HAS_BOUNCED);
    }

    public void setInitial (boolean initial) {
        if (level().isClientSide) return;
        entityData.set(INITIAL, initial);
    }

    public boolean getInitial () {
        return entityData.get(INITIAL);
    }

    public void setScattersLeft (int scattersLeft) {
        if (level().isClientSide) return;
        entityData.set(SCATTERS_LEFT, scattersLeft);
    }

    public int getScattersLeft () {
        return entityData.get(SCATTERS_LEFT);
    }

    public void setBouncesLeft (int bouncesLeft) {
        if (level().isClientSide) return;
        entityData.set(BOUNCES_LEFT, bouncesLeft);
    }

    public int getBouncesLeft () {
        return entityData.get(BOUNCES_LEFT);
    }

    public void setBounces (int bounces) {
        if (level().isClientSide) return;
        entityData.set(BOUNCES, bounces);
    }

    public int getBounces () {
        return entityData.get(BOUNCES);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_BOUNCED, false);
        builder.define(INITIAL, false);
        builder.define(SCATTERS_LEFT, 0);
        builder.define(BOUNCES_LEFT, 0);
        builder.define(BOUNCES, 0);
    }

    public final int lifetime = 400;
    boolean hasBounced;
    boolean initial;
    HashMap<UUID, Integer> victims;
    int scattersLeft;
    int bouncesLeft;
    int bounces;

    public Vec3 getAfterBounceSpeed () {
        return new Vec3(0.5, 0.5, 0.5);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target) && target != getOwner();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        Vec3 normal = Vec3.atLowerCornerOf(pResult.getDirection().getNormal());
        double push = this.getBbWidth() / 2.0 + 0.001;

        this.setPos(
                pResult.getLocation().x + normal.x * push,
                pResult.getLocation().y + normal.y * push,
                pResult.getLocation().z + normal.z * push
        );
        if (!getHasBounced() && getInitial()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(this.getAfterBounceSpeed()));
            setHasBounced(true);
        }
        switch (pResult.getDirection()) {
            case UP, DOWN ->
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1, this.isNoGravity() ? -1 : -.8f, 1));
            case EAST, WEST -> this.setDeltaMovement(this.getDeltaMovement().multiply(-1, 1, 1));
            case NORTH, SOUTH -> this.setDeltaMovement(this.getDeltaMovement().multiply(1, 1, -1));
        }
        if (getScattersLeft() > 0) {
            if (getOwner() instanceof LivingEntity owner) {
                int deg = 30;
                for (int i = - deg; i <= deg; i += deg * 2) {
                    ScatterProjectile scatterArrow = new ScatterProjectile(level(), getScattersLeft() - 1, getBouncesLeft() - 1, false);
                    scatterArrow.copyStats(this, owner, (float) getBaseDamage());
                    scatterArrow.setCritArrow(false);

                    Vec3 original = this.getDeltaMovement().normalize();
                    float speed = (float)this.getDeltaMovement().length();

                    float radians = (float)Math.toRadians(i);
                    double cos = Math.cos(radians);
                    double sin = Math.sin(radians);

                    Vec3 rotated = new Vec3(
                            original.x * cos - original.z * sin,
                            original.y,
                            original.x * sin + original.z * cos
                    );

                    scatterArrow.shoot(rotated.x, rotated.y, rotated.z, speed, 0);

                    level().addFreshEntity(scatterArrow);
                }
            }
            setScattersLeft(getScattersLeft() - 1);
        }
        if (getBounces() >= getBouncesLeft()) {
            super.onHitBlock(pResult);
        }
        setBounces(getBounces() + 1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > this.lifetime) {
            discard();
        }
    }
}
