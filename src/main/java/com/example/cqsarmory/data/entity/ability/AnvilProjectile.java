package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class AnvilProjectile extends AbilityArrow{
    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(AnvilProjectile.class, EntityDataSerializers.ITEM_STACK);
    private IntOpenHashSet piercingIgnoreEntityIds;
    public double blocksFallen;
    public double oldPosY;

    public AnvilProjectile(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public AnvilProjectile(Level level) {
        this(EntityRegistry.ANVIL_PROJECTILE.get(), level);
        setThrownItem(new ItemStack(Items.ANVIL));
        blocksFallen = 0;
    }

    public double getDamage() {
        return this.getBaseDamage() + (2 * this.blocksFallen);
    }

    public Vec3 deltaMovementOld = Vec3.ZERO;

    @Override
    public void tick() {
        super.tick();
        // prevent first-tick flicker due to deltaMoveOld being "uninitialized" on our first tick
        if (tickCount == 1) {
            deltaMovementOld = getDeltaMovement();
            oldPosY = position().y;
        }
        deltaMovementOld = getDeltaMovement();

        if (position().y < oldPosY) {
            if (blocksFallen == 0) {
                blocksFallen = 1;
            }
            blocksFallen += oldPosY - position().y;
            oldPosY = position().y;
        }
    }

    @Override
    protected double getDefaultGravity() {
        return 0.2;
    }

    @Override
    public boolean isCritArrow() {
        return false;
    }

    @Override
    public Vec3 getMovementToShoot(double x, double y, double z, float velocity, float inaccuracy) {
        return super.getMovementToShoot(x, y, z, velocity, inaccuracy).scale(0.5);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target) && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains(target.getId()));
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        double damage = getDamage();
        var target = result.getEntity();
        var damageType = damageSources().damageTypes.getHolder(DamageTypes.FALLING_ANVIL).get();
        level().playSound(null, this.blockPosition(), SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS);

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
        if (level().isClientSide) {
            return;
        }

        Level level = this.level();
        Vec3 hitPos = result.getLocation();
        float damage = (float) this.getDamage() * 0.25f;
        float radius = (float) Math.min(1 + 0.5 * this.blocksFallen, 4);
        for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, new AABB(hitPos.subtract(radius, radius, radius), hitPos.add(radius, radius, radius)))) {
            if (livingentity.isAlive() && livingentity.isPickable() && Utils.hasLineOfSight(level, hitPos, livingentity.getBoundingBox().getCenter(), true)) {
                DamageSources.applyDamage(livingentity, damage, new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.FALLING_ANVIL).get(), this, this.getOwner()));
            }
        }

        for (MomentumOrb orb : level.getEntitiesOfClass(MomentumOrb.class, new AABB(hitPos.subtract(radius, radius, radius), hitPos.add(radius, radius, radius)))) {
            if (Utils.hasLineOfSight(level, hitPos, orb.getBoundingBox().getCenter(), true)) {
                DamageSources.applyDamage(orb, damage, new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.FALLING_ANVIL).get(), this, this.getOwner()));
            }
        }

        Vector3f center = new Vector3f(1, 1f, 1f);
        var x = result.getLocation().x;
        var y = result.getLocation().y;
        var z = result.getLocation().z;

        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(center, radius), x, y + .165f, z, 1, 0, 0, 0, 0, false);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(center, radius), x, y + .135f, z, 1, 0, 0, 0, 0, false);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(center, radius * 1.02f), x, y + .135f, z, 0, 1, 0, 0, 0, false);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(center, radius * 0.98f), x, y + .135f, z, 0, 1, 0, 0, 0, false);

        level.playSound(null, result.getBlockPos(), SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS);
        discard();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_ITEM, ItemStack.EMPTY);
    }

    public ItemStack getItem() {
        return entityData.get(DATA_ITEM);
    }

    public void setThrownItem(ItemStack stack) {
        this.entityData.set(DATA_ITEM, stack);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        var item = getItem();
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
}
