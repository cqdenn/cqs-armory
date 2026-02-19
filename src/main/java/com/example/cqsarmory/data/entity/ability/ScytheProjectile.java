package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.EntityRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import static com.example.cqsarmory.spells.ChainWhipSpell.getDuration;

public class ScytheProjectile extends AbilityArrow {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(ScytheProjectile.class, EntityDataSerializers.ITEM_STACK);
    private double gravity;
    private double speed;
    public int spellLevel;
    public int lifetime;

    public ScytheProjectile(EntityType<? extends AbilityArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ScytheProjectile(Level level, ItemStack itemStack, double gravity, double speed, int spellLevel) {
        this(EntityRegistry.SCYTHE_PROJECTILE.get(), level);
        setThrownItem(itemStack);
        setGravity(gravity);
        setSpeed(speed);
        this.spellLevel = spellLevel;
        this.lifetime = 0;
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
        lifetime++;
        if (lifetime >= 15) {
            discard();
        }
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
        return super.canHitEntity(target);
    }

    public double getDamage(Entity target) {
        double damage = this.getBaseDamage();
        if (this.level() instanceof ServerLevel serverLevel && target != null) {
            DamageSource source = new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.MELEE_SKILL).get(), this, this.getOwner());
            return EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), target, source, (float) damage) ;
        }
        return damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        var target = result.getEntity();
        double damage = getDamage(target);
        var damageType = damageSources().damageTypes.getHolder(DamageTypes.MELEE_SKILL).get();

        if (this.canHitEntity(result.getEntity())) {
            if (!this.level().isClientSide) {
                target.hurt(new DamageSource(damageType, this, getOwner()), (float) damage);
                if (target instanceof LivingEntity living && getOwner() instanceof LivingEntity owner) {
                    living.addEffect(new MobEffectInstance(MobEffectRegistry.CHAINED, getDuration(this.spellLevel, owner), 0, false, false, true));
                }
            }
            discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        discard();
    }
}
