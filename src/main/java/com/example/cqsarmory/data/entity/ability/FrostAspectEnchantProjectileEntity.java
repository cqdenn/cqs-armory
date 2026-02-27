package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.Tags;
import io.redspace.ironsspellbooks.entity.spells.icicle.IcicleProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FrostAspectEnchantProjectileEntity extends IcicleProjectile {
    ItemStack weapon;

    public FrostAspectEnchantProjectileEntity(EntityType<? extends IcicleProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public FrostAspectEnchantProjectileEntity(Level levelIn, LivingEntity shooter, ItemStack weapon) {
        super(levelIn, shooter);
        setWeapon(weapon);
    }

    public void setWeapon (ItemStack weapon) {
        this.weapon = weapon;
    }

    public ItemStack getWeapon() {
        return this.weapon;
    }
}
