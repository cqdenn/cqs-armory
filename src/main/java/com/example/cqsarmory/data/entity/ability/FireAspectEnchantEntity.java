package com.example.cqsarmory.data.entity.ability;

import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireAspectEnchantEntity extends FireField {

    ItemStack weapon;

    public FireAspectEnchantEntity(Level level, ItemStack weapon) {
        super(level);
        setWeapon(weapon);
    }

    public FireAspectEnchantEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public void setWeapon (ItemStack weapon) {
        this.weapon = weapon;
    }

    public ItemStack getWeapon() {
        return this.weapon;
    }
}
