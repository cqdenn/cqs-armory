package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

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

    @Override
    public double getBaseDamage() {
        if (this.getOwner() instanceof LivingEntity owner) {
            return super.getBaseDamage() * owner.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER);
        }
        return super.getBaseDamage();
    }

}
