package com.example.cqsarmory.data.entity.ability;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import io.redspace.ironsspellbooks.entity.spells.icicle.IcicleProjectile;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FrostAspectEnchantEntity extends FrozenHumanoid {

    ItemStack weapon;
    private float shatterProjectileDamage;

    public FrostAspectEnchantEntity(Level level, LivingEntity entityToCopy, ItemStack weapon) {
        super(level, entityToCopy);
        setWeapon(weapon);
    }

    public FrostAspectEnchantEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public void setWeapon (ItemStack weapon) {
        this.weapon = weapon;
    }

    public ItemStack getWeapon() {
        return this.weapon;
    }

    @Override
    public void setShatterDamage(float damage) {
        this.shatterProjectileDamage = damage;
    }



    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (level().isClientSide || this.isInvulnerableTo(pSource) || invulnerableTime > 0)
            return false;
        invulnerableTime = 10;
        doPuffDamage();
        spawnIcicleShards(this.getEyePosition(), this.shatterProjectileDamage);
        this.playHurtSound(pSource);
        this.discard();
        return true;
    }

    private void doPuffDamage() {
        Level level = this.level();
        var damage = this.shatterProjectileDamage * .5f;
        var collider = this.getBoundingBox().inflate(2);
        var radius = collider.getXsize();
        Vec3 center = collider.getCenter();
        var entities = level.getEntities(this, collider);
        for (Entity entity : entities) {
            double distanceSqr = entity.distanceToSqr(center);
            if (distanceSqr < radius * radius && entity.canBeHitByProjectile() && !DamageSources.isFriendlyFireBetween(entity, getSummoner()) && Utils.hasLineOfSight(level, center, entity.getBoundingBox().getCenter(), true)) {
                entity.hurt(new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.EXPLOSION).get(), this, getSummoner()), damage);
            }
        }
        MagicManager.spawnParticles(level, ParticleHelper.SNOW_DUST, getX(), getY() + 1, getZ(), 50, 0.2, 0.2, 0.2, 0.2, false);
        MagicManager.spawnParticles(level, ParticleHelper.SNOWFLAKE, getX(), getY() + 1, getZ(), 50, 0.2, 0.2, 0.2, 0.2, false);
    }

    private void spawnIcicleShards(Vec3 origin, float damage) {
        int count = 8;
        int offset = 360 / count;
        for (int i = 0; i < count; i++) {

            Vec3 motion = new Vec3(0, 0, 1.0);
            motion = motion.xRot(12 * Mth.DEG_TO_RAD);
            motion = motion.yRot(offset * i * Mth.DEG_TO_RAD);


            FrostAspectEnchantProjectileEntity shard = new FrostAspectEnchantProjectileEntity(level(), getSummoner(), getWeapon());
            shard.setDamage(damage);
            shard.setDeltaMovement(motion);
            shard.setNoGravity(false);

            Vec3 spawn = origin.add(motion.multiply(1, 0, 1).normalize().scale(.5f));
            var angle = Utils.rotationFromDirection(motion);

            shard.moveTo(spawn.x, spawn.y - shard.getBoundingBox().getYsize() / 2, spawn.z, angle.y, angle.x);
            level().addFreshEntity(shard);
        }
    }
}
