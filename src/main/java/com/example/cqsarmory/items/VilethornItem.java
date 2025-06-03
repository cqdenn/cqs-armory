package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class VilethornItem extends SwordItem {
    public VilethornItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);


        Random random = new Random();
        if (random.nextFloat() <= 0.35) {
            Vec3 spawn = target.position();
            Level level = attacker.level();
            RootEntity rootEntity = new RootEntity(level, attacker);
            rootEntity.setDuration(40);
            rootEntity.setTarget(target);
            rootEntity.moveTo(spawn);
            level.addFreshEntity(rootEntity);
            target.stopRiding();
            target.startRiding(rootEntity, true);
        }
    }
}
