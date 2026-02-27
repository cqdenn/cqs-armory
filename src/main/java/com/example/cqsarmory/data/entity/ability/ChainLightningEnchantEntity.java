package com.example.cqsarmory.data.entity.ability;

import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChainLightningEnchantEntity extends ChainLightning {
    public ChainLightningEnchantEntity(Level level, Entity owner, Entity initialVictim, ItemStack weapon) {
        super(level, owner, initialVictim);
        setWeapon(weapon);
    }
    ItemStack weapon;

    public ChainLightningEnchantEntity(EntityType<ChainLightningEnchantEntity> chainLightningEnchantEntityEntityType, Level level) {
        super(chainLightningEnchantEntityEntityType, level);
    }

    public void setWeapon (ItemStack weapon) {
        this.weapon = weapon;
    }

    public ItemStack getWeapon() {
        return this.weapon;
    }
}
