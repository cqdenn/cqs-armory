package com.example.cqsarmory.utils;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.ExplosiveMomentumOrb;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import com.example.cqsarmory.data.entity.ability.OrbExplosion;
import com.example.cqsarmory.data.entity.ability.SpeedMomentumOrb;
import com.example.cqsarmory.registry.AttributeRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CQtils {

    public static void disableShield(Player player, int ticks) {
        player.getCooldowns().addCooldown(player.getUseItem().getItem(), ticks);
        player.stopUsingItem();
        player.level().broadcastEntityEvent(player, (byte)30);
    }

    public static void momentumOrbEffects (MomentumOrb momentumOrb) {
        Level level = momentumOrb.level();
        Player player = momentumOrb.getCreator();

        if (momentumOrb instanceof SpeedMomentumOrb speedMomentumOrb) {
            AbilityData.get(player).momentumOrbEffects.speedStacks += 1;
            AbilityData.get(player).momentumOrbEffects.speedEnd = player.tickCount + (20 * 10);
            speedMomentumOrb.discard();
        }
        else if (momentumOrb instanceof ExplosiveMomentumOrb explosiveMomentumOrb) {
            float radius = 2 + (float) (player.getAttribute(AttributeRegistry.MAX_MOMENTUM).getValue() / 10);

            OrbExplosion orbExplosion = new OrbExplosion(level, explosiveMomentumOrb.getCreator(), 20, radius);
            orbExplosion.moveTo(explosiveMomentumOrb.position());
            level.addFreshEntity(orbExplosion);

            explosiveMomentumOrb.discard();
        }
    }
}
