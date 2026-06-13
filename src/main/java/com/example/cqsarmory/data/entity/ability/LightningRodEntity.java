package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import io.redspace.ironsspellbooks.particle.SparkParticleOptions;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LightningRodEntity extends Entity implements GeoEntity {

    private LivingEntity owner;
    int lifetime;
    int radius;

    public LightningRodEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public LightningRodEntity (Level level, LivingEntity owner, int lifetime, int radius) {
        this(EntityRegistry.LIGHTNING_ROD.get(), level);
        this.owner = owner;
        this.lifetime = lifetime;
        this.radius = radius;
    }

    public LivingEntity getOwner() {
        return owner;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.10;
    }

    @Override
    public void tick() {
        super.tick();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.applyGravity();
        if (this.onGround()) this.setDeltaMovement(Vec3.ZERO);
        if (tickCount == lifetime) {
            if (level().isClientSide) {
                level().playSound(null, this.blockPosition(), SoundRegistry.LIGHTNING_WOOSH_01.get(), SoundSource.PLAYERS, 1, 0.7f);
            }
            discard();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!DamageSources.isFriendlyFireBetween(source.getEntity(), getOwner()) || !(source.is(ISSDamageTypes.LIGHTNING_MAGIC) || (source.getDirectEntity() instanceof LightningArrow))) return false;
        float radius = this.radius;
        level().getEntities(this, this.getBoundingBox().inflate(radius), entity -> entity != getOwner()
                && (entity instanceof LivingEntity)
                && entity.isAlive()
                && Utils.hasLineOfSight(level(), this, entity, true))
                .forEach(target -> {
                    CQChainLightning lightning = new CQChainLightning(level(), getOwner(), target, this.getPosition(0).add(0, 2, 0));
                    lightning.range = radius + 2;
                    lightning.setDamage(amount);
                    lightning.maxConnections = 1;
                    level().addFreshEntity(lightning);
                });
        if (!level().isClientSide) MagicManager.spawnParticles(level(), ParticleHelper.ELECTRIC_SPARKS, this.getX(), this.getY() + 2, this.getZ(), 2 + Utils.random.nextIntBetweenInclusive(0, 2), 0, 0, 0, 0.3, false);
        if (source.getDirectEntity() instanceof LightningArrow arrow && arrow.getPierceLevel() == (byte) 0) arrow.discard();
        return true;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
