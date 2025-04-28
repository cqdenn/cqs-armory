package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class MjolnirItem extends TridentItem {
    public MjolnirItem(Tier tier, Properties properties) {super(properties);}

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        var level = attacker.level();
        var entities = level.getEntities(attacker, attacker.getBoundingBox().inflate(4));
        if (!attacker.hasEffect(MobEffectRegistry.THUNDERSTORM)) {
            attacker.addEffect(new MobEffectInstance(MobEffectRegistry.THUNDERSTORM, 40, 20, false, false, false));
        }

    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {return UseAnim.SPEAR;}

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {return 72000;}

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return super.use(level, player, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, level, entityLiving, timeLeft);
        if (this.getUseDuration(stack, entityLiving) - timeLeft >= 10) {
            entityLiving.push(entityLiving.getForward().scale(3));
        }
    }

    private static boolean isTooDamagedToUse(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }

}
