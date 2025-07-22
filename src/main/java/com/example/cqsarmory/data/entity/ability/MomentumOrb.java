package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.SoundRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MomentumOrb extends Entity implements GeoEntity {

    private Player creator = null;

    public MomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level);
        this.creator = creator;
    }

    public MomentumOrb(EntityType<?> momentumOrbEntityType, Level level) {
        super(momentumOrbEntityType, level);
    }

    public Player getCreator() { return this.creator; }

    public ResourceLocation getTexture() {return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/momentum_orb.png");}

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
    public boolean hurt(DamageSource source, float amount) {
        if (DamageSources.isFriendlyFireBetween(this.getCreator(), source.getEntity()) || source.getEntity() instanceof OrbExplosion) {
            if (!level().isClientSide) {
                CQtils.momentumOrbEffects(this, amount);
                this.level().playSound(null, this.blockPosition(), SoundRegistry.ORB_SHOT_SOUND.get(), SoundSource.MASTER, 0.2f, 2f);
                return true;
            } else {
                return false;
            }
        }
        else {return false;}
    }

    public final int waitTime = 200;

    @Override
    public void tick() {
        super.tick();

        if (tickCount == waitTime) {
            discard();
        }

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
