package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class MjolnirItem extends SwordItem {
    public MjolnirItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        var level = attacker.level();
        var entities = level.getEntities(attacker, attacker.getBoundingBox().inflate(4));
        if (!attacker.hasEffect(MobEffectRegistry.THUNDERSTORM)) {
            attacker.addEffect(new MobEffectInstance(MobEffectRegistry.THUNDERSTORM, 40, 20, false, false, false));
        }

    }
}
