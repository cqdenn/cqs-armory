package com.example.cqsarmory.utils;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.*;
import com.example.cqsarmory.network.SyncMomentumDamageEndPacket;
import com.example.cqsarmory.network.SyncMomentumDamagePacket;
import com.example.cqsarmory.network.SyncMomentumSpeedEndPacket;
import com.example.cqsarmory.network.SyncMomentumSpeedPacket;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class CQtils {

    public static void disableShield(Player player, int ticks) {
        player.getCooldowns().addCooldown(player.getUseItem().getItem(), ticks);
        player.stopUsingItem();
        player.level().broadcastEntityEvent(player, (byte) 30);
    }

    public static void momentumOrbEffects(MomentumOrb momentumOrb) {
        Level level = momentumOrb.level();
        Player player = momentumOrb.getCreator();

        if (momentumOrb instanceof SpeedMomentumOrb speedMomentumOrb) {
            int newMomentumSpeedTest = (AbilityData.get(player).momentumOrbEffects.speedStacks + 1);
            int newMomentumSpeed = Math.min(newMomentumSpeedTest, 10); // capped at 10 for some reason idk its hardcoded everywhere at this point FIXME
            AbilityData.get(player).momentumOrbEffects.speedEnd = player.tickCount + (20 * 10);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumSpeedEndPacket(player.tickCount + (20 * 10)));
            AbilityData.get(player).momentumOrbEffects.speedStacks = newMomentumSpeed;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumSpeedPacket(newMomentumSpeed));
            speedMomentumOrb.discard();
        } else if (momentumOrb instanceof ExplosiveMomentumOrb explosiveMomentumOrb) {
            float radius = 2 + (float) (player.getAttribute(AttributeRegistry.MAX_MOMENTUM).getValue() / 10);

            OrbExplosion orbExplosion = new OrbExplosion(level, explosiveMomentumOrb.getCreator(), 20, radius); // 20 dmg tbd FIXME
            orbExplosion.moveTo(explosiveMomentumOrb.position());
            level.addFreshEntity(orbExplosion);

            explosiveMomentumOrb.discard();
        } else if (momentumOrb instanceof DodgeMomentumOrb dodgeOrb) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.DODGE, 20 * 5, 0, false, false, true));
            dodgeOrb.discard();
        } else if (momentumOrb instanceof InstaDrawMomentumOrb instaDrawMomentumOrb) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.INSTA_DRAW, 20 * 5, 0, false, false, true));
            instaDrawMomentumOrb.discard();
        } else if (momentumOrb instanceof ArrowDamageMomentumOrb arrowDamageMomentumOrb) {
            int newMomentumDamageTest = (AbilityData.get(player).momentumOrbEffects.arrowDamageStacks + 1);
            int newMomentumDamage = Math.min(newMomentumDamageTest, 10); // capped at 10 for some reason idk its hardcoded everywhere at this point FIXME
            AbilityData.get(player).momentumOrbEffects.arrowDamageEnd = player.tickCount + (20 * 10);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumDamageEndPacket(player.tickCount + (20 * 10)));
            AbilityData.get(player).momentumOrbEffects.arrowDamageStacks = newMomentumDamage;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumDamagePacket(newMomentumDamage));
            arrowDamageMomentumOrb.discard();
        }
    }

    public static void findOrbLoc(Vec3 startLoc, MomentumOrb orb, Level level) {
        List<MomentumOrb> orbs = new ArrayList<>();
        var entities = level.getEntities(orb, new AABB(startLoc, startLoc).inflate(0.25));
        for (Entity entity : entities) {
            if (entity instanceof MomentumOrb momentumOrb) {
                orbs.add(momentumOrb);
            }
        }
        if (!orbs.isEmpty()) {
            Vec3 add = Utils.getRandomVec3(1).multiply(1, 0, 1).add(0, Utils.random.nextFloat() / 2, 0);
            findOrbLoc(startLoc.add(add),orb, level);
        } else {
            orb.moveTo(startLoc);
        }
    }
}
