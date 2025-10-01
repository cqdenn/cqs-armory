package com.example.cqsarmory.data.entity.ability;


import com.example.cqsarmory.data.effects.CQMobEffectInstance;
import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.EntityRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BatProjectile extends AbilityArrow implements IMagicSummon {

    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState restAnimationState = new AnimationState();
    private IntOpenHashSet piercingIgnoreEntityIds;
    private int numPierced;
    private float damage;
    private int life;
    private int lifetime;

    public BatProjectile(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BatProjectile(Level level, LivingEntity shooter, float damage) {
        this(EntityRegistry.BAT_PROJECTILE.get(), level);
        this.setOwner(shooter);
        this.flyAnimationState.stop();
        this.setBaseDamage(damage);
        this.life = 0;
        this.lifetime = 160 + Utils.random.nextInt(40);
        SummonManager.initSummon(shooter, this, this.lifetime, new SummonedEntitiesCastData());
    }

    @Override
    public boolean isCritArrow() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    @NotNull
    public Vec3 getMovementToShoot(double x, double y, double z, float velocity, float inaccuracy) {
        return super.getMovementToShoot(x, y, z, velocity, inaccuracy).scale(0.3f);
    }

    public void bite(Entity entity) {
        if (entity instanceof LivingEntity target) {
            target.hurt(new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.BAT_PROJECTILE).get(), this, getOwner()), (float) this.getBaseDamage());
            target.addEffect(new CQMobEffectInstance(MobEffectRegistry.BLEED, 100, (int) Math.floor(this.getBaseDamage()) / 10, false, false, true, getOwner(), true));
        } else if (entity instanceof MomentumOrb orb) {
            orb.hurt(new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.BAT_PROJECTILE).get(), this, getOwner()), (float) this.getBaseDamage());
        }
        level().playSound(null, this.blockPosition(), SoundEvents.BAT_AMBIENT, SoundSource.PLAYERS, 1, 1);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target) && target != this.getOwner() && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains(target.getId()));
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.setDeltaMovement(this.getDeltaMovement().scale(-2));
        level().playSound(null, this.blockPosition(), SoundEvents.ANVIL_FALL, SoundSource.PLAYERS, 1f, 2f);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (this.canHitEntity(entity)) {
            if (!this.level().isClientSide) {
                this.bite(entity);
                this.setBaseDamage(this.getBaseDamage() * 0.9);
            }
            if (this.getPierceLevel() > 0) {
                if (this.piercingIgnoreEntityIds == null) {
                    this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
                }

                if (this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1 || this.numPierced >= this.getPierceLevel()) {
                    removeBat();
                    MagicManager.spawnParticles(level(), ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0, false);
                    return;
                }

                this.piercingIgnoreEntityIds.add(entity.getId());
                this.numPierced++;
            } else {
                removeBat();
            }
        }
    }

    public void removeBat() {
        SummonManager.removeSummon(this);
        discard();
    }

    private void setupAnimationStates() {
        this.restAnimationState.stop();
        this.flyAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public boolean isFlapping() {
        return (float) this.tickCount % 10.0F == 0.0F;
    }

    @Override
    public void tick() {
        //super.tick();
        if (this.life == 0 && getOwner() != null && this.getYRot() != getOwner().getYRot()) {
            this.setYRot(getOwner().getYRot());
        }

        abstractArrowTick();

        if (getOwner() != null && getOwner() instanceof LivingEntity owner) {


            double reach = 64.0D; // Max distance for raycast
            Vec3 start = owner.getEyePosition(1.0F);
            Vec3 look = owner.getLookAngle();
            Vec3 end = start.add(look.scale(reach));

            Vec3 target = Utils.raycastForEntity(level(), owner, owner.getEyePosition(), end, true, 0.1f, entity -> !(entity instanceof BatProjectile) && Utils.canHitWithRaycast(entity)).getLocation();
            //Utils.particleTrail(level(), target, target.add(0, 2, 0), ParticleTypes.HAPPY_VILLAGER);

            Vec3 toTarget = target.subtract(this.position());
            //Utils.particleTrail(level(), this.position(), this.position().add(toTarget), ParticleHelper.UNSTABLE_ENDER);

            double dx = toTarget.x;
            double dy = toTarget.y;
            double dz = toTarget.z;

            float targetYaw = (float) (Mth.atan2(-dx, dz) * (180F / Math.PI));
            float targetPitch = -(float) (Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180F / Math.PI));

            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();

            float yaw = Mth.rotLerp(0.2F, this.getYRot(), targetYaw);
            float pitch = Mth.rotLerp(0.2F, this.getXRot(), targetPitch);

            this.setYRot(yaw);
            this.setXRot(pitch);

            Vec3 wantedMotion = this.getLookAngle().scale(0.3);
            /*if (!level().isClientSide) {
                Utils.particleTrail(level(), this.position(), this.position().add(wantedMotion), ParticleTypes.HAPPY_VILLAGER);
            }*/
            Vec3 dMovement = this.getDeltaMovement();
            float speed = (this.getId() % 10 + 35) * .01f;
            this.setDeltaMovement(Utils.lerp(speed, dMovement, wantedMotion));
            double dX = dMovement.x;
            double dY = dMovement.y;
            double dZ = dMovement.z;
            double newX = this.getX() + dX;
            double newY = this.getY() + dY;
            double newZ = this.getZ() + dZ;
            this.setPos(newX, newY, newZ);
        }


        this.setupAnimationStates();

        if (this.life % 20 == 0 && this.piercingIgnoreEntityIds != null) {
            this.piercingIgnoreEntityIds.clear();
        }

        this.life++;
        if (!level().isClientSide && this.life > this.lifetime) {
            MagicManager.spawnParticles(level(), ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0, false);
            removeBat();

        }
    }

    public void abstractArrowTick() {
        projectileTick();

        boolean flag = this.isNoPhysics();
        Vec3 vec3 = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * 180.0F / (float) Math.PI));
            this.setXRot((float) (Mth.atan2(vec3.y, d0) * 180.0F / (float) Math.PI));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level().getBlockState(blockpos);
        if (!blockstate.isAir() && !flag) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level(), blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vec31 = this.position();

                for (AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(vec31)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shakeTime > 0) {
            this.shakeTime--;
        }

        if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW) || this.isInFluidType((fluidType, height) -> this.canFluidExtinguish(fluidType))) {
            this.clearFire();
        }


        this.inGroundTime = 0;
        Vec3 vec32 = this.position();
        Vec3 vec33 = vec32.add(vec3);
        HitResult hitresult = this.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec33 = hitresult.getLocation();
        }

        while (!this.isRemoved()) {
            EntityHitResult entityhitresult = this.findHitEntity(vec32, vec33);
            if (entityhitresult != null) {
                hitresult = entityhitresult;
            }

            if (hitresult != null && hitresult.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult) hitresult).getEntity();
                Entity entity1 = this.getOwner();
                if (entity instanceof Player && entity1 instanceof Player && !((Player) entity1).canHarmPlayer((Player) entity)) {
                    hitresult = null;
                    entityhitresult = null;
                }
            }

            if (hitresult != null && hitresult.getType() != HitResult.Type.MISS && !flag) {
                if (net.neoforged.neoforge.event.EventHooks.onProjectileImpact(this, hitresult))
                    break;
                ProjectileDeflection projectiledeflection = this.hitTargetOrDeflectSelf(hitresult);
                this.hasImpulse = true;
                if (projectiledeflection != ProjectileDeflection.NONE) {
                    break;
                }
            }

            if (entityhitresult == null || this.getPierceLevel() <= 0) {
                break;
            }

            hitresult = null;
        }

        vec3 = this.getDeltaMovement();
        double d5 = vec3.x;
        double d6 = vec3.y;
        double d1 = vec3.z;
        if (this.isCritArrow()) {
            for (int i = 0; i < 4; i++) {
                this.level()
                        .addParticle(
                                ParticleTypes.CRIT,
                                this.getX() + d5 * (double) i / 4.0,
                                this.getY() + d6 * (double) i / 4.0,
                                this.getZ() + d1 * (double) i / 4.0,
                                -d5,
                                -d6 + 0.2,
                                -d1
                        );
            }
        }

        double d7 = this.getX() + d5;
        double d2 = this.getY() + d6;
        double d3 = this.getZ() + d1;
        double d4 = vec3.horizontalDistance();
        /*if (flag) {
            this.setYRot((float) (Mth.atan2(-d5, -d1) * 180.0F / (float) Math.PI));
        } else {
            this.setYRot((float) (Mth.atan2(d5, d1) * 180.0F / (float) Math.PI));
        }

        this.setXRot((float) (Mth.atan2(d6, d4) * 180.0F / (float) Math.PI));
        this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(lerpRotation(this.yRotO, this.getYRot()));*/
        float f = 0.99F;
        if (this.isInWater()) {
            for (int j = 0; j < 4; j++) {
                float f1 = 0.25F;
                this.level().addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25, d2 - d6 * 0.25, d3 - d1 * 0.25, d5, d6, d1);
            }

            f = this.getWaterInertia();
        }

        //this.setDeltaMovement(vec3.scale((double) f));
        if (!flag) {
            this.applyGravity();
        }

        //this.setPos(d7, d2, d3);
        this.checkInsideBlocks();

    }

    public void projectileTick() {
        if (!this.hasBeenShot) {
            this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
            this.hasBeenShot = true;
        }

        if (!this.leftOwner) {
            this.leftOwner = this.checkLeftOwner();
        }
        this.baseTick();
    }

    @Override
    public Entity getSummoner() {
        return getOwner();
    }

    @Override
    public void onUnSummon() {
    }
}
