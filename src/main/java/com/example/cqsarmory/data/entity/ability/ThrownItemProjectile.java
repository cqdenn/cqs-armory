package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class ThrownItemProjectile extends AbilityArrow {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(ThrownItemProjectile.class, EntityDataSerializers.ITEM_STACK);
    private double gravity;
    private double speed;
    private IntOpenHashSet piercingIgnoreEntityIds;

    public ThrownItemProjectile(EntityType<? extends AbilityArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownItemProjectile(Level level, ItemStack itemStack, double gravity, double speed) {
        this(EntityRegistry.THROWN_ITEM.get(), level);
        setThrownItem(itemStack);
        setGravity(gravity);
        setSpeed(speed);
    }

    public boolean isInGround() {
        return this.inGround;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    @Override
    protected double getDefaultGravity() {
        return gravity;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public Vec3 getMovementToShoot(double x, double y, double z, float velocity, float inaccuracy) {
        return super.getMovementToShoot(x, y, z, velocity, inaccuracy).scale(this.speed);
    }

    @Override
    public boolean isCritArrow() {
        return false;
    }

    public Vec3 deltaMovementOld = Vec3.ZERO;

    @Override
    public void tick() {
        super.tick();
        // prevent first-tick flicker due to deltaMoveOld being "uninitialized" on our first tick
        if (tickCount == 1) {
            deltaMovementOld = getDeltaMovement();
        }

        deltaMovementOld = getDeltaMovement();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_ITEM, ItemStack.EMPTY);
    }

    public ItemStack getThrownItem() {
        return entityData.get(DATA_ITEM);
    }

    public void setThrownItem(ItemStack stack) {
        this.entityData.set(DATA_ITEM, stack);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        var item = getThrownItem();
        if (!item.isEmpty()) {
            tag.put("item", item.save(this.level().registryAccess()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("item")) {
            this.setThrownItem(ItemStack.parseOptional(level().registryAccess(), tag.getCompound("item")));
        }
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target) && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains(target.getId()));
    }

    public double getDamage(Entity target) {
        double damage = this.getBaseDamage();
        if (this.level() instanceof ServerLevel serverLevel && target != null) {
            DamageSource source = new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.THROWN_ITEM).get(), this, this.getOwner());
            return EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), target, source, (float) damage) ;
        }
        return damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        var target = result.getEntity();
        if (target instanceof PartEntity<?> part) target = part.getParent();
        double damage = getDamage(target);
        var damageType = damageSources().damageTypes.getHolder(DamageTypes.THROWN_ITEM).get();

        if (this.canHitEntity(result.getEntity())) {
            if (!this.level().isClientSide) {
                target.hurt(new DamageSource(damageType, this, getOwner()), (float) damage);
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
        super.onHitBlock(result);
    }
}
