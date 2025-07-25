package com.example.cqsarmory.items.weapons;

import com.example.cqsarmory.data.AbilityData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class MjolnirItem extends TridentItem {


    public MjolnirItem(Tier tier, Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        if (!attacker.hasEffect(MobEffectRegistry.THUNDERSTORM)) {
            attacker.addEffect(new MobEffectInstance(MobEffectRegistry.THUNDERSTORM, 40, 20, false, false, false));
        }

    }

    public static void slamDamage (List<Entity> entities, Level level, Entity entity) {
        DamageSource genericDamage = level.damageSources().indirectMagic(entity, entity);
        float radius = 8;
        for (Entity target : entities) {
            if (target instanceof LivingEntity livingEntity && canHit(entity, target) && livingEntity.distanceToSqr(entity) < radius * radius) {
                target.hurt(genericDamage, 20);
            }
        }
        AbilityData.get(entity).mjolnirData.doDamage = false;
    }


    private static boolean canHit(Entity owner, Entity target) {
        return target.isAlive() && target.isPickable() && !target.isSpectator();
    }

    public final SpellDamageSource getDamageSource(Entity attacker) {
        return getDamageSource(attacker, attacker);
    }

    public SpellDamageSource getDamageSource(Entity projectile, Entity attacker) {
        return SpellDamageSource.source(projectile, attacker, SpellRegistry.SHOCKWAVE_SPELL.get());
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return super.use(level, player, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, level, entityLiving, timeLeft);
        if (this.getUseDuration(stack, entityLiving) - timeLeft >= 10) {
            entityLiving.push(entityLiving.getForward().scale(2));
            level.playSound(entityLiving, entityLiving.blockPosition(), SoundRegistry.LIGHTNING_CAST.get(), SoundSource.MASTER, 0.5f, 1.0f);
            if (!entityLiving.onGround()) {
                AbilityData.get(entityLiving).mjolnirData.speed = entityLiving.getDeltaMovement().y;
            }
        }
    }
}
