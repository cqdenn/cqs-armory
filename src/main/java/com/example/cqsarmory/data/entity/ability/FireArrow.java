package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import io.redspace.ironsspellbooks.entity.spells.snowball.FrostField;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

public class FireArrow extends AbilityArrow{

    public FireArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public FireArrow(Level level) {
        this(EntityRegistry.FIRE_ARROW.get(), level);
    }

    @Override
    public boolean isOnFire() {
        return true;
    }
/*
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        createAoe(result.getLocation());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        if (target instanceof PartEntity<?> part) target = part.getParent();
        createAoe(result.getLocation());
    }

    public void createAoe(Vec3 location) {
        if (!level().isClientSide) {
            FireField fire = new FireField(level());
            fire.setDamage(4);
            fire.setOwner(getOwner());
            fire.setDuration(100);
            fire.setRadius(3);
            fire.setCircular();
            fire.moveTo(location);
            level().addFreshEntity(fire);
        }
    }*/
}
