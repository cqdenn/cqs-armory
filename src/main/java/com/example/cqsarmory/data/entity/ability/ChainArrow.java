package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.entity.PartEntity;

public class ChainArrow extends AbilityArrow{

    public ChainArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ChainArrow(Level level) {
        this(EntityRegistry.CHAIN_ARROW.get(), level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (target instanceof PartEntity<?> part) target = part.getParent();
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        discard();
    }
}
