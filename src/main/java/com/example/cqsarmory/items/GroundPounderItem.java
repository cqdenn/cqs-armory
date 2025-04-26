package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;


public class GroundPounderItem extends SwordItem {
    public GroundPounderItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        var level = attacker.level();
        if (attacker instanceof ServerPlayer serverplayer) {
            ServerLevel serverlevel = (ServerLevel) attacker.level();
            serverlevel.playSound(
                    null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.ANVIL_LAND, serverplayer.getSoundSource(), 0.1F, 0.5F
            );
            MagicManager.spawnParticles(serverlevel, ParticleTypes.EXPLOSION, target.getX(), target.getY(), target.getZ(), 1, 0, 0, 0, 0, false);
        }
        if (target.onGround() || target.isInPowderSnow || target.isInLiquid()) {
            target.push(0, 1, 0);
        }
        else{
            target.setDeltaMovement(attacker.getForward().scale(2));
            target.hasImpulse = true;
        }
        return super.hurtEnemy(stack, target, attacker);
    }

}
