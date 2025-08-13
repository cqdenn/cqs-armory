package com.example.cqsarmory.utils;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.data.entity.ability.*;
import com.example.cqsarmory.network.SyncMomentumDamageEndPacket;
import com.example.cqsarmory.network.SyncMomentumDamagePacket;
import com.example.cqsarmory.network.SyncMomentumSpeedEndPacket;
import com.example.cqsarmory.network.SyncMomentumSpeedPacket;
import com.example.cqsarmory.registry.*;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import io.redspace.ironsspellbooks.entity.spells.black_hole.BlackHole;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CQtils {

    public static final Map<AbstractSpell, Supplier<SchoolType>> schoolMap = buildSchoolMap();

    public static void disableShield(Player player, int ticks) {
        player.getCooldowns().addCooldown(player.getUseItem().getItem(), ticks);
        player.stopUsingItem();
        player.level().broadcastEntityEvent(player, (byte) 30);
    }

    private static Map<AbstractSpell, Supplier<SchoolType>> buildSchoolMap () {
        Map<AbstractSpell, Supplier<SchoolType>> schoolMap = new HashMap<>();

        schoolMap.put(SpellRegistry.FANG_STRIKE_SPELL.get(), CQSchoolRegistry.NECROMANCY);
        schoolMap.put(SpellRegistry.RAISE_DEAD_SPELL.get(), CQSchoolRegistry.NECROMANCY);
        schoolMap.put(SpellRegistry.DEVOUR_SPELL.get(), CQSchoolRegistry.NECROMANCY);
        schoolMap.put(SpellRegistry.BLIGHT_SPELL.get(), CQSchoolRegistry.NECROMANCY);
        schoolMap.put(SpellRegistry.SUMMON_VEX_SPELL.get(), CQSchoolRegistry.NECROMANCY);
        schoolMap.put(SpellRegistry.SACRIFICE_SPELL.get(), CQSchoolRegistry.NECROMANCY);
        schoolMap.put(SpellRegistry.BLOOD_SLASH_SPELL.get(), CQSchoolRegistry.NECROMANCY);
        schoolMap.put(SpellRegistry.HEARTSTOP_SPELL.get(), CQSchoolRegistry.NECROMANCY);

        schoolMap.put(SpellRegistry.MAGIC_MISSILE_SPELL.get(), CQSchoolRegistry.ARCANE);
        schoolMap.put(SpellRegistry.GUST_SPELL.get(), CQSchoolRegistry.ARCANE);
        schoolMap.put(SpellRegistry.STARFALL_SPELL.get(), CQSchoolRegistry.ARCANE);
        schoolMap.put(SpellRegistry.INVISIBILITY_SPELL.get(), CQSchoolRegistry.ARCANE);
        schoolMap.put(SpellRegistry.ELDRITCH_BLAST_SPELL.get(), CQSchoolRegistry.ARCANE);
        schoolMap.put(SpellRegistry.SLOW_SPELL.get(), CQSchoolRegistry.ARCANE);
        schoolMap.put(SpellRegistry.TELEKINESIS_SPELL.get(), CQSchoolRegistry.ARCANE);
        schoolMap.put(SpellRegistry.TELEPORT_SPELL.get(), CQSchoolRegistry.ARCANE);

        return schoolMap;
    }

    public static MomentumOrb getRandomOrbType(Level level, Player player) {
        ExplosiveMomentumOrb explosiveMomentumOrb = new ExplosiveMomentumOrb(EntityRegistry.EXPLOSIVE_MOMENTUM_ORB.get(), level, player);
        BlackHoleMomentumOrb blackHoleMomentumOrb = new BlackHoleMomentumOrb(EntityRegistry.BLACK_HOLE_MOMENTUM_ORB.get(), level, player);
        DodgeMomentumOrb dodgeMomentumOrb = new DodgeMomentumOrb(EntityRegistry.DODGE_MOMENTUM_ORB.get(), level, player);
        //InstaDrawMomentumOrb instaDrawMomentumOrb = new InstaDrawMomentumOrb(EntityRegistry.INSTA_DRAW_MOMENTUM_ORB.get(), level, player);
        IceExplosionMomentumOrb iceExplosionMomentumOrb = new IceExplosionMomentumOrb(EntityRegistry.ICE_EXPLOSIVE_MOMENTUM_ORB.get(), level, player);
        RootMomentumOrb rootMomentumOrb = new RootMomentumOrb(EntityRegistry.ROOT_MOMENTUM_ORB.get(), level, player);
        ChainLightningMomentumOrb chainLightningMomentumOrb = new ChainLightningMomentumOrb(EntityRegistry.CHAIN_LIGHTNING_MOMENTUM_ORB.get(), level, player);

        List<MomentumOrb> orbs = List.of(
                explosiveMomentumOrb, blackHoleMomentumOrb, dodgeMomentumOrb, iceExplosionMomentumOrb, rootMomentumOrb, chainLightningMomentumOrb
        );
        return orbs.get(Utils.random.nextIntBetweenInclusive(0, orbs.size() - 1));
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
            float radius = 10;
            float dmg = DamageData.get(explosiveMomentumOrb).lastDamage;

            OrbExplosion orbExplosion = new OrbExplosion(level, explosiveMomentumOrb.getCreator(), dmg * 4, radius); // dmg tbd FIXME
            orbExplosion.moveTo(explosiveMomentumOrb.position());
            level.addFreshEntity(orbExplosion);

            explosiveMomentumOrb.discard();
        } else if (momentumOrb instanceof DodgeMomentumOrb dodgeOrb) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.DODGE, 20 * 10, 0, false, false, true));
            dodgeOrb.discard();
        } else if (momentumOrb instanceof InstaDrawMomentumOrb instaDrawMomentumOrb) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.INSTA_DRAW, 20 * 10, 0, false, false, true));
            instaDrawMomentumOrb.discard();
        } else if (momentumOrb instanceof ArrowDamageMomentumOrb arrowDamageMomentumOrb) {
            int newMomentumDamageTest = (AbilityData.get(player).momentumOrbEffects.arrowDamageStacks + 1);
            int newMomentumDamage = Math.min(newMomentumDamageTest, 10); // capped at 10 for some reason idk its hardcoded everywhere at this point FIXME
            AbilityData.get(player).momentumOrbEffects.arrowDamageEnd = player.tickCount + (20 * 10);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumDamageEndPacket(player.tickCount + (20 * 10)));
            AbilityData.get(player).momentumOrbEffects.arrowDamageStacks = newMomentumDamage;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumDamagePacket(newMomentumDamage));
            arrowDamageMomentumOrb.discard();
        } else if (momentumOrb instanceof ChainLightningMomentumOrb chainLightningMomentumOrb) {
            ChainLightning chainLightning = new ChainLightning(level, player, chainLightningMomentumOrb);
            chainLightning.range = 30;
            chainLightning.setDamage(DamageData.get(chainLightningMomentumOrb).lastDamage);
            chainLightning.maxConnections = 20;
            chainLightningMomentumOrb.discard();
            level.addFreshEntity(chainLightning);
            chainLightningMomentumOrb.discard();
        } else if (momentumOrb instanceof IceExplosionMomentumOrb iceExplosionMomentumOrb) {
            float radius = 10;
            float dmg = DamageData.get(iceExplosionMomentumOrb).lastDamage;

            IceOrbExplosion iceOrbExplosion = new IceOrbExplosion(level, iceExplosionMomentumOrb.getCreator(), dmg * 4, radius); // dmg tbd FIXME
            iceOrbExplosion.moveTo(iceExplosionMomentumOrb.position());
            level.addFreshEntity(iceOrbExplosion);

            iceExplosionMomentumOrb.discard();
        } else if (momentumOrb instanceof BlackHoleMomentumOrb blackHoleMomentumOrb) {
            float radius = Math.min(DamageData.get(blackHoleMomentumOrb).lastDamage / 2, 15);
            float dmg = DamageData.get(blackHoleMomentumOrb).lastDamage * 0.5f;

            BlackHole blackHole = new BlackHole(level, player);
            blackHole.setDamage(dmg * 0.5f);
            blackHole.setRadius(radius);
            blackHole.setDuration(120);
            blackHole.moveTo(Utils.moveToRelativeGroundLevel(level, blackHoleMomentumOrb.position(), 6));
            level.addFreshEntity(blackHole);

            blackHoleMomentumOrb.discard();
        } else if (momentumOrb instanceof RootMomentumOrb rootMomentumOrb) {
            float radius = 5;
            var entities = level.getEntities(rootMomentumOrb, new AABB(rootMomentumOrb.position(), rootMomentumOrb.position()).inflate(radius, radius, radius), (targeted) -> !DamageSources.isFriendlyFireBetween(player, targeted));

            for (Entity entity : entities) {
                if (entity instanceof LivingEntity target) {
                    Vec3 spawn = target.position();
                    RootEntity rootEntity = new RootEntity(level, player);
                    rootEntity.setDuration(100);
                    rootEntity.setTarget(target);
                    rootEntity.moveTo(spawn);
                    level.addFreshEntity(rootEntity);
                    target.stopRiding();
                    target.startRiding(rootEntity, true);
                }
            }

            rootMomentumOrb.discard();
        }
    }

    public static void findOrbLoc(Vec3 startLoc, MomentumOrb orb, Level level) {
        List<MomentumOrb> orbs = new ArrayList<>();
        var entities = level.getEntities(orb, new AABB(startLoc, startLoc).inflate(0.3));
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
