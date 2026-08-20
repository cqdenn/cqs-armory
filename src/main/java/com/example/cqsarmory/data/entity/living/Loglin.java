package com.example.cqsarmory.data.entity.living;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.entity.spells.poison_cloud.PoisonCloud;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class Loglin extends Hoglin {

    private static final EntityDataAccessor<Boolean> CHARGING_JUMP = SynchedEntityData.defineId(Loglin.class, EntityDataSerializers.BOOLEAN);
    public int chargeTime = 60;
    public boolean poisonOnLand = false;
    public boolean triggerPoison = false;

    public Loglin(EntityType<? extends Hoglin> entityType, Level level) {
        super(EntityRegistry.LOGLIN.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8F)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.GRAVITY, 0.08)
                .add(Attributes.SCALE, 1.25)
                .add(Attributes.FOLLOW_RANGE, 4);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.getTarget();
        if (target != null) {
            if (target.distanceToSqr(this) > 6 * 6) {
                this.stopInPlace();
                this.lookAt(target, 1, 1);
                this.setChargingJump(true);
            }
        }
        if (isChargingJump()) {
            this.chargeTime--;
        }
        if (this.chargeTime <= 0) {
            this.chargeTime = 60;
            this.setChargingJump(false);
            this.push(calculateJumpVector(this, target != null ? target.position() : this.position()));
            this.triggerPoison = true;
        }
        if (!this.onGround() && this.triggerPoison) {
            this.poisonOnLand = true;
            this.triggerPoison = false;
        }
        if (this.verticalCollisionBelow && this.poisonOnLand) {
            if (!level().isClientSide) {
                PoisonCloud cloud = new PoisonCloud(level());
                cloud.setOwner(this);
                cloud.setDuration(100);
                cloud.setDamage(1);
                cloud.moveTo(this.position());
                level().addFreshEntity(cloud);
            }
            this.poisonOnLand = false;
        }
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    public boolean isChargingJump() {
        return this.getEntityData().get(CHARGING_JUMP);
    }

    public void setChargingJump(boolean jump) {
        this.getEntityData().set(CHARGING_JUMP, jump);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHARGING_JUMP, false);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        return spawnGroupData;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0;
    }

    @Override
    public boolean isConverting() {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.FLOWERS);
    }

    @Override
    public boolean canBeHunted() {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        Loglin loglin = new Loglin(EntityRegistry.LOGLIN.get(), level);
        if (loglin != null) {
            loglin.setPersistenceRequired();
        }

        return loglin;
    }

    /**
     * Calculates the velocity vector needed to jump from an entity's current position
     * to land exactly at a target position, accounting for Minecraft's per-tick
     * horizontal drag (air resistance), which a naive ballistic formula ignores.
     *
     * @param entity  the jumping entity (used to read its gravity attribute)
     * @param target  the position to land at
     * @return the velocity vector to apply to the entity
     */
    public static Vec3 calculateJumpVector(LivingEntity entity, Vec3 target) {
        Vec3 start = entity.position();
        double gravity = entity.getAttributeValue(Attributes.GRAVITY);

        // Horizontal drag factor: living entities lose ~9% horizontal speed per tick in air.
        // (Movement is multiplied by this each tick before/after gravity is applied.)
        double drag = 0.83;

        double dx = target.x - start.x;
        double dy = target.y - start.y;
        double dz = target.z - start.z;
        double horizontalDist = Math.sqrt(dx * dx + dz * dz);

        double jumpHeight = Math.max(dy, 0) + Math.max(0.5, horizontalDist * 0.5);

        double vy = Math.sqrt(2 * gravity * jumpHeight);
        double fallTime = Math.sqrt(2 * (jumpHeight - dy) / gravity);
        double riseTime = vy / gravity;
        double totalTime = riseTime + fallTime;

        int ticks = Math.max(1, (int) Math.round(totalTime));

        // Compensate horizontal velocity for drag using the geometric series sum
        double dragSum = (1 - Math.pow(drag, ticks)) / (1 - drag);
        double speedScale = horizontalDist / dragSum; // total horizontal speed needed per tick, undecayed

        // Split into x/z proportionally to direction
        double vx = (dx / horizontalDist) * speedScale;
        double vz = (dz / horizontalDist) * speedScale;

        // Guard against zero horizontal distance (straight-up jump)
        if (horizontalDist < 1e-6) {
            vx = 0;
            vz = 0;
        }

        return new Vec3(vx, vy, vz);
    }
}
