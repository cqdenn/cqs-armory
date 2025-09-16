package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AbilityArrow extends AbstractArrow {
    private static final EntityDataAccessor<Float> ID_SCALE = SynchedEntityData.defineId(AbilityArrow.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> ID_IGNORE_BLOCKS = SynchedEntityData.defineId(AbilityArrow.class, EntityDataSerializers.BOOLEAN);

    public AbilityArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public AbilityArrow(Level level) {
        this(EntityRegistry.ABILITY_ARROW.get(), level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_SCALE, 1f);
        builder.define(ID_IGNORE_BLOCKS, false);
    }

    public float getScale() {
        return this.entityData.get(ID_SCALE);
    }

    public void setScale(float scale) {
        this.entityData.set(ID_SCALE, scale);
    }

    public boolean ignoreBlocks() {
        return this.entityData.get(ID_IGNORE_BLOCKS);
    }

    public void setIgnoreBlocks(boolean ignoreBlocks) {
        this.entityData.set(ID_IGNORE_BLOCKS, ignoreBlocks);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return Items.ARROW.getDefaultInstance();
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }

    private PotionContents getPotionContents() {
        return this.getPickupItemStackOrigin().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    private void setPotionContents(PotionContents potionContents) {
        this.getPickupItemStackOrigin().set(DataComponents.POTION_CONTENTS, potionContents);
    }

    public void addEffect(MobEffectInstance effectInstance) {
        this.setPotionContents(this.getPotionContents().withEffectAdded(effectInstance));
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        Entity entity = this.getEffectSource();
        PotionContents potioncontents = this.getPotionContents();
        if (potioncontents.potion().isPresent()) {
            for (MobEffectInstance mobeffectinstance : potioncontents.potion().get().value().getEffects()) {
                living.addEffect(
                        new MobEffectInstance(
                                mobeffectinstance.getEffect(),
                                Math.max(mobeffectinstance.mapDuration(p_268168_ -> p_268168_ / 8), 1),
                                mobeffectinstance.getAmplifier(),
                                mobeffectinstance.isAmbient(),
                                mobeffectinstance.isVisible()
                        ),
                        entity
                );
            }
        }

        for (MobEffectInstance mobeffectinstance1 : potioncontents.customEffects()) {
            living.addEffect(mobeffectinstance1, entity);
        }
    }

    @Override
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return ProjectileUtil.getEntityHitResult(
                this.level(), this, startVec, endVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(getScale()), this::canHitEntity
        );
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        //fixme: this doesn't affect inground calculations, which can still stop an arrow in its tracks (especially slow ones that don't skip through blocks)
        if (ignoreBlocks()) {
            return;
        }
        super.onHitBlock(result);
        this.setNoGravity(false);
    }
}

