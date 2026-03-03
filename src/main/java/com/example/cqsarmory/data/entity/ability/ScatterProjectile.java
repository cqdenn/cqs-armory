package com.example.cqsarmory.data.entity.ability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.UUID;

public class ScatterProjectile extends AbilityArrow{
    public ScatterProjectile(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ScatterProjectile(Level level, int scattersLeft) {
        super(level);
        this.scattersLeft = scattersLeft;
        this.victims = new HashMap<>();
        this.hasBounced = false;
    }

    public final int lifetime = 100;
    boolean hasBounced;
    HashMap<UUID, Integer> victims;
    int scattersLeft;
    int bounces;

    public Vec3 getAfterBounceSpeed () {
        return new Vec3(0.5, 0.5, 0.5);
    }

    /*public boolean canHitVictim(Entity entity) {
        var timestamp = victims.get(entity.getUUID());
        return timestamp == null || entity.tickCount - timestamp >= 10;
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target) && canHitVictim(target);
    }*/

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.hasBounced) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(this.getAfterBounceSpeed()));
            this.hasBounced = true;
        }
        switch (pResult.getDirection()) {
            case UP, DOWN ->
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1, this.isNoGravity() ? -1 : -.8f, 1));
            case EAST, WEST -> this.setDeltaMovement(this.getDeltaMovement().multiply(-1, 1, 1));
            case NORTH, SOUTH -> this.setDeltaMovement(this.getDeltaMovement().multiply(1, 1, -1));
        }
        if (this.scattersLeft > 0) {
            if (getOwner() instanceof LivingEntity owner) {
                int deg = 30;
                for (int i = - deg; i <= deg; i += deg * 2) {
                    ScatterProjectile scatterArrow = new ScatterProjectile(level(), this.scattersLeft - 1);
                    scatterArrow.copyStats(this, owner, (float) getBaseDamage());

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
            this.scattersLeft -= 1;
        }
        if (++this.bounces >= 4) {
            discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > this.lifetime) {
            discard();
        }
    }
}
