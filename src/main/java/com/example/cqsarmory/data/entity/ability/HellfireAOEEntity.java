package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.DamageTypes;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.fireball.SmallMagicFireball;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class HellfireAOEEntity extends SmallMagicFireball {
    public HellfireAOEEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public HellfireAOEEntity(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!this.level().isClientSide) {
            var target = pResult.getEntity();
            var owner = getOwner();
            target.hurt(new DamageSource(damageSources().damageTypes.getHolder(DamageTypes.HELLFIRE_MAGE_AOE).get(), this, owner), damage);
        }
    }
}
