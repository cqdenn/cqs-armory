package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.entity.PartEntity;

public class ChainArrow extends AbilityArrow{

    public ChainArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ChainArrow(Level level, int duration) {
        this(EntityRegistry.CHAIN_ARROW.get(), level);
        this.duration = duration;
    }

    int duration;

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (target instanceof PartEntity<?> part) target = part.getParent();

        if (this.canHitEntity(result.getEntity())) {
            if (!this.level().isClientSide && target instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffectRegistry.CHAINED, duration, 0, false, false, true));
            }
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    public boolean isInGround () {
        return this.inGround;
    }

    @Override
    public void customCritParticles() {
    }
}
