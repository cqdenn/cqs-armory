package com.example.cqsarmory.data.entity.living;

import com.example.cqsarmory.network.SmashParticlePacket;
import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.entity.mobs.wizards.fire_boss.NotIdioticNavigation;
import io.redspace.ironsspellbooks.entity.spells.poison_cloud.PoisonCloud;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class Loglin extends Hoglin {
    public final int JUMP_TIME_MAX = 60;
    public final int CHARGE_TIME_MAX = 20;
    public final int CHARGE_CD_MAX = 100;

    private static final EntityDataAccessor<Boolean> CHARGING_JUMP = SynchedEntityData.defineId(Loglin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHARGING_CHARGE = SynchedEntityData.defineId(Loglin.class, EntityDataSerializers.BOOLEAN);
    public int chargeCD = CHARGE_CD_MAX;
    public boolean canSlow = false;
    public int jumpTime = JUMP_TIME_MAX;
    public int chargeTime = CHARGE_TIME_MAX;
    public boolean poisonOnLand = false;
    public boolean triggerPoison = false;

    public Loglin(EntityType<? extends Hoglin> entityType, Level level) {
        super(EntityRegistry.LOGLIN.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8F)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.GRAVITY, 0.08)
                .add(Attributes.SCALE, 1.25)
                .add(Attributes.STEP_HEIGHT, 1.5)
                .add(Attributes.FOLLOW_RANGE, 4);
    }

    public boolean hasRoomToJump() {
        Vec3 raw = this.getTarget() != null
                ? this.getTarget().position().subtract(this.position())
                : this.getLookAngle();
        Vec3 dir = new Vec3(raw.x, 0, raw.z).normalize();
        AABB forward = this.getBoundingBox().inflate(-0.01).move(dir.scale(1.5));
        AABB above   = forward.move(0, 2.5, 0); // headroom check
        return !level().getBlockCollisions(null, forward).iterator().hasNext() && !level().getBlockCollisions(null, above).iterator().hasNext();
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.getTarget();
        this.chargeCD--;
        if (target != null) {
            if (target.distanceToSqr(this) > 8 * 8 && hasRoomToJump() && !isChargingCharge()) {
                this.stopInPlace();
                this.lookAt(target, 30, 30);
                this.setChargingJump(true);
            } else if (this.chargeCD <= 0 && target.distanceToSqr(this) > 3 * 3 && Math.abs(this.getY() - this.getTarget().getY()) < 1.5 && !isChargingJump()) {
                this.stopInPlace();
                this.lookAt(target, 30, 30);
                this.setChargingCharge(true);
            }
        }
        if (isChargingCharge()) {
            this.chargeTime--;
        }
        if (this.chargeTime <= 0) {
            this.chargeTime = CHARGE_TIME_MAX;
            this.setChargingCharge(false);
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 4, false, false, false));
            this.push(calculateJumpVector(this, target != null ? target.position() : this.position()).multiply(2, 0.1, 2));
            this.canSlow = true;
            this.chargeCD = CHARGE_CD_MAX;
            this.playSound(SoundEvents.HOGLIN_ANGRY, 2, 2);
        }
        if (!this.hasEffect(MobEffects.MOVEMENT_SPEED)) {
            this.canSlow = false;
        }
        if (isChargingJump()) {
            this.jumpTime--;
        }
        if (this.jumpTime <= 0) {
            this.jumpTime = JUMP_TIME_MAX;
            this.setChargingJump(false);
            this.push(calculateJumpVector(this, target != null ? target.position() : this.position()));
            this.triggerPoison = true;
            this.playSound(SoundEvents.HOGLIN_ATTACK, 2, 2);
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
            PacketDistributor.sendToPlayersTrackingEntity(this, new SmashParticlePacket(this.getBlockPosBelowThatAffectsMyMovement()));
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (canSlow && entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false, false, true));
        }
        return super.doHurtTarget(entity);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new NotIdioticNavigation(this, level);
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

    public boolean isChargingCharge() {
        return this.getEntityData().get(CHARGING_CHARGE);
    }

    public void setChargingCharge(boolean charge) {
        this.getEntityData().set(CHARGING_CHARGE, charge);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHARGING_JUMP, false);
        builder.define(CHARGING_CHARGE, false);
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
