package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.EntityRegistry;
import com.google.common.collect.Lists;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class FireworkProjectile extends AbilityArrow {
    private static final EntityDataAccessor<ItemStack> DATA_ID_FIREWORKS_ITEM = SynchedEntityData.defineId(
            FireworkProjectile.class, EntityDataSerializers.ITEM_STACK
    );
    private IntOpenHashSet piercingIgnoreEntityIds;

    public FireworkProjectile(Level level, ItemStack stack, Entity shooter, double x, double y, double z, boolean shotAtAngle, float damage) {
        super(EntityRegistry.FIREWORK_PROJECTILE.get(), level);
        this.setOwner(shooter);
        this.entityData.set(DATA_ID_FIREWORKS_ITEM, stack.copy());
        this.setPos(x, y, z);
        this.damage = damage;
        this.life = 0;
        this.lifetime = 10 + (Utils.random.nextInt(10));
        this.shotAtAngle = shotAtAngle;
    }

    public FireworkProjectile(EntityType<FireworkProjectile> fireworkProjectileEntityType, Level level) {
        super(fireworkProjectileEntityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_FIREWORKS_ITEM, new ItemStack(Items.FIREWORK_ROCKET));
    }

    private float damage;
    private int life;
    private int lifetime;
    private boolean shotAtAngle;

    public boolean isShotAtAngle() {return shotAtAngle;}

    @Override
    @NotNull
    public Vec3 getMovementToShoot(double x, double y, double z, float velocity, float inaccuracy) {
        return super.getMovementToShoot(x, y, z, velocity, inaccuracy).scale(0.5f);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isCritArrow() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        /*if (!this.isShotAtAngle()) {
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

        this.updateRotation();*/
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
            this.explode(this.position());
            this.discard();
        }
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target) && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains(target.getId()));
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        //super.onHitEntity(result);
        if (this.canHitEntity(result.getEntity())) {
            if (!this.level().isClientSide) {
                this.explode(result.getLocation());
            }
            if (this.getPierceLevel() > 0) {
                if (this.piercingIgnoreEntityIds == null) {
                    this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
                }

                if (this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1) {
                    this.discard();
                    return;
                }

                this.piercingIgnoreEntityIds.add(result.getEntity().getId());
            } else {
                discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        BlockPos blockpos = new BlockPos(result.getBlockPos());
        this.level().getBlockState(blockpos).entityInside(this.level(), blockpos, this);
        if (!this.level().isClientSide) {
            this.explode(result.getLocation());
            this.discard();
        }
        //super.onHitBlock(result);
    }

    private void explode(Vec3 pos) {
        this.level().broadcastEntityEvent(this, (byte) 17);
        this.gameEvent(GameEvent.EXPLODE, this.getOwner());
        this.dealExplosionDamage(pos);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 17 && this.level().isClientSide) {
            Vec3 vec3 = this.getDeltaMovement();
            this.level().createFireworks(this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z, vec3.x, vec3.y, vec3.z, this.getExplosions());
        }

        super.handleEntityEvent(id);
    }

    private List<FireworkExplosion> getExplosions() {
        ItemStack itemstack = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
        Fireworks fireworks = itemstack.get(DataComponents.FIREWORKS);
        return fireworks != null ? fireworks.explosions() : List.of();
    }

    public double getDamage(Entity target) {
        double damage = this.damage;
        if (this.level() instanceof ServerLevel serverLevel && target != null) {
            DamageSource source = new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.FIREWORK_PROJECTILE).get(), this, this.getOwner());
            return EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), target, source, (float) damage) ;
        }
        return damage;
    }

    private void dealExplosionDamage(Vec3 hitPos) {
        Level level = this.level();
        double explosionRadius = 2;
        for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, new AABB(hitPos.subtract(explosionRadius, explosionRadius, explosionRadius), hitPos.add(explosionRadius, explosionRadius, explosionRadius)))) {
            float damage = (float) getDamage(livingentity);
            if (livingentity.isAlive() && Utils.hasLineOfSight(level, hitPos, livingentity.getBoundingBox().getCenter(), true)) {
                DamageSources.applyDamage(livingentity, damage, new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.FIREWORK_PROJECTILE).get(), this, this.getOwner()));
            }
        }

        for (MomentumOrb orb : level.getEntitiesOfClass(MomentumOrb.class, new AABB(hitPos.subtract(explosionRadius, explosionRadius, explosionRadius), hitPos.add(explosionRadius, explosionRadius, explosionRadius)))) {
            float damage = (float) getDamage(orb);
            if (Utils.hasLineOfSight(level, hitPos, orb.getBoundingBox().getCenter(), true)) {
                DamageSources.applyDamage(orb, damage, new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.FIREWORK_PROJECTILE).get(), this, this.getOwner()));
            }
        }
    }
}
