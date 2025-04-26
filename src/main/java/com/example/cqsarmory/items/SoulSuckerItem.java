package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class SoulSuckerItem extends Item {



    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        var proj = livingEntity.getForward().scale(6);
        DamageSource genericDamage = level.damageSources().indirectMagic(livingEntity, livingEntity);
        var entities = level.getEntities(livingEntity, livingEntity.getBoundingBox().expandTowards(proj), entity -> {
            return Utils.checkEntityIntersecting(entity, livingEntity.getEyePosition(), (livingEntity.getEyePosition().add(proj)), 0.15f).getType() == HitResult.Type.ENTITY;
        });
//        if (remainingUseDuration % 20 == 0) {
//            MagicManager.spawnParticles(livingEntity.level(), ParticleTypes.EXPLOSION, ((livingEntity.getForward().x()) * 2) + livingEntity.getEyePosition().x(), ((livingEntity.getForward().y()) * 2) + livingEntity.getEyePosition().y(), ((livingEntity.getForward().z()) * 2) + livingEntity.getEyePosition().z(), 1, 0, 0, 0, 0, false);
//        }
        if (entities.size() > 0) {
            for (Entity Bob : entities) {
                if (remainingUseDuration % 5 == 0) {
                    Bob.push(livingEntity.getLookAngle().scale(-0.25).subtract(0, 0, 0));
                    Bob.hurt(genericDamage, 10.0f);
                }
            }
        }
    }

    public SoulSuckerItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        player.startUsingItem(usedHand);
        return super.
                use(level, player, usedHand);
    }
}
