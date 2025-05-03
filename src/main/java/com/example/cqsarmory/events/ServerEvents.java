package com.example.cqsarmory.events;


import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.VolcanoExplosion;
import com.example.cqsarmory.items.CosmicArkItem;
import com.example.cqsarmory.items.MjolnirItem;
import com.example.cqsarmory.items.VolcanoSwordItem;
import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.EntityRegistry;
import com.example.cqsarmory.registry.ItemRegistry;
import com.sun.jna.platform.win32.Winevt;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.Objects;

@EventBusSubscriber
public class ServerEvents {

    @SubscribeEvent
    public static void onDeath (LivingDeathEvent event) {
        Entity entity = event.getEntity().getKillCredit();
        if (event.getSource().typeHolder().is(DamageTypes.VOLCANO) || (entity instanceof LivingEntity livingEntity && livingEntity.getMainHandItem().is(ItemRegistry.VOLCANO))) {
            VolcanoExplosion volcanoExplosion = new VolcanoExplosion(event.getEntity().level(), (LivingEntity) entity, 20, 4);
            volcanoExplosion.moveTo(event.getEntity().position().add(0, 1, 0));
            event.getEntity().level().addFreshEntity(volcanoExplosion);
            if (event.getSource().getDirectEntity() instanceof VolcanoExplosion volcanoExplosion1) {
                volcanoExplosion.setOwner(volcanoExplosion1.getOwner());
            }
        }
    }

    @SubscribeEvent
    public static void onFall (LivingFallEvent event) {
        if (event.getEntity() instanceof LivingEntity entity && entity.getMainHandItem().is(ItemRegistry.MJOLNIR) && AbilityData.get(entity).mjolnirData.speed < 0) {
            if (event.getDistance() > 1.5) {
                if (entity.level().isClientSide) {
                    return;
                }
                Vector3f center = new Vector3f(1, 1f, 1f);
                float radius = 8;
                Vector3f edge = new Vector3f(.7f, 1f, 1f);
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                var entities = entity.level().getEntities(entity, entity.getBoundingBox().inflate(radius, radius, radius), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(event.getEntity().level(), entity, target, true));
                MjolnirItem.slamDamage(entities, event.getEntity().level(), entity);
                MagicManager.spawnParticles(entity.level(), new BlastwaveParticleOptions(center, radius), x, y + .165f, z, 1, 0, 0, 0, 0, false);
                MagicManager.spawnParticles(entity.level(), new BlastwaveParticleOptions(center, radius), x, y + .135f, z, 1, 0, 0, 0, 0, false);
                MagicManager.spawnParticles(entity.level(), new BlastwaveParticleOptions(center, radius * 1.02f), x, y + .135f, z, 1, 0, 0, 0, 0, false);
                MagicManager.spawnParticles(entity.level(), new BlastwaveParticleOptions(center, radius * 0.98f), x, y + .135f, z, 1, 0, 0, 0, 0, false);

                MagicManager.spawnParticles(entity.level(), ParticleHelper.ELECTRICITY, x, y + 1f, z, 80, entity.level().random.nextDouble(), entity.level().random.nextDouble(), entity.level().random.nextDouble(), entity.level().random.nextDouble(), false);
                MagicManager.spawnParticles(entity.level(), ParticleHelper.ELECTRICITY, x, y + 1f, z, 80, entity.level().random.nextDouble() * -1, entity.level().random.nextDouble(), entity.level().random.nextDouble(), entity.level().random.nextDouble(), false);
                MagicManager.spawnParticles(entity.level(), ParticleHelper.ELECTRICITY, x, y + 1f, z, 80, entity.level().random.nextDouble(), entity.level().random.nextDouble(), entity.level().random.nextDouble() * -1, entity.level().random.nextDouble(), false);
                entity.level().playSound(entity, entity.blockPosition(), SoundRegistry.SHOCKWAVE_CAST.get(), SoundSource.MASTER, 0.5f, 1f);
                AbilityData.get(entity).mjolnirData.speed = 0;
                event.setCanceled(true);
            }
            else {
                AbilityData.get(entity).mjolnirData.speed = 0;
            }
        }
        else {
            AbilityData.get(event.getEntity()).mjolnirData.speed = 0;
        }
    }

}
