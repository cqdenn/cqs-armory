package com.example.cqsarmory.events;


import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.enchants.PoisonAspect;
import com.example.cqsarmory.data.entity.ability.VolcanoExplosion;
import com.example.cqsarmory.items.CosmicArkItem;
import com.example.cqsarmory.items.MjolnirItem;
import com.example.cqsarmory.items.VolcanoSwordItem;
import com.example.cqsarmory.registry.*;
import com.sun.jna.platform.win32.Winevt;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.Objects;

@EventBusSubscriber
public class ServerEvents {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
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
    public static void onFall(LivingFallEvent event) {
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
            } else {
                AbilityData.get(entity).mjolnirData.speed = 0;
            }
        } else {
            AbilityData.get(event.getEntity()).mjolnirData.speed = 0;
        }
    }

    @SubscribeEvent
    public static void onHit(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        LivingEntity attacker = target.getLastAttacker();

        if (attacker == null) {
            return;
        }

        Level level = attacker.level();
        Holder.Reference<Enchantment> fireAspectHolder = attacker.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT);
        int fireAspectLevel = attacker.getMainHandItem().getEnchantmentLevel(fireAspectHolder);

        if (fireAspectLevel > 0 && event.getSource().getDirectEntity() instanceof LivingEntity) {
            if (!level.isClientSide) {
                FireField fire = new FireField(level);
                fire.setOwner(attacker);
                fire.setDuration(100);
                fire.setDamage(2 * fireAspectLevel);
                fire.setRadius(2 * fireAspectLevel);
                fire.setCircular();
                fire.moveTo(target.position());
                level.addFreshEntity(fire);
            }
        }

    }

    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        LivingEntity target = event.getEntity();
        LivingEntity attacker = target.getLastAttacker();

        if (attacker == null) {
            return;
        }

        Holder.Reference<Enchantment> knockbackCurseHolder = attacker.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "knockback_curse")));
        int knockbackCurseLevel = attacker.getMainHandItem().getEnchantmentLevel(knockbackCurseHolder);

        if (knockbackCurseLevel > 0) {
            attacker.knockback(event.getStrength(), -1 * event.getRatioX(), -1 * event.getRatioZ());
            attacker.hurtMarked = true;
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void reallyHeavinessCurse(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        Holder.Reference<Enchantment> reallyHeavinessCurseHolder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "really_heaviness_curse")));
        int reallyHeavinessCurseLevel = player.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(reallyHeavinessCurseHolder);

        if (reallyHeavinessCurseLevel > 0) {
            player.setForcedPose(Pose.SWIMMING);
        } else if (player.getForcedPose() == Pose.SWIMMING) {
            player.setForcedPose(null);
        } //FIXME might mess w other setforcedpose implementations


    }

    @SubscribeEvent
    public static void leakyXP(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        Holder.Reference<Enchantment> xpCurseHolder = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "leaky_xp_curse")));
        int headCurseLevel = entity.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(xpCurseHolder);
        int chestCurseLevel = entity.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(xpCurseHolder);
        int legCurseLevel = entity.getItemBySlot(EquipmentSlot.LEGS).getEnchantmentLevel(xpCurseHolder);
        int feetCurseLevel = entity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(xpCurseHolder);
        int totalCurseLevel = headCurseLevel + chestCurseLevel + legCurseLevel + feetCurseLevel;

        if (totalCurseLevel > 0 && entity instanceof Player player) {
            Vec3 offset = entity.getForward().multiply(3, 0, 3).scale(player.getScale()).yRot(180 * Mth.DEG_TO_RAD);
            Vec3 spawn = Utils.moveToRelativeGroundLevel(player.level(), Utils.raycastForBlock(player.level(), player.getEyePosition(), player.position().add(offset), ClipContext.Fluid.NONE).getLocation(), 4);


            ExperienceOrb experienceOrb = new ExperienceOrb(player.level(), spawn.x, spawn.add(0, 0.1, 0).y, spawn.z, 10 * totalCurseLevel);
            player.giveExperiencePoints(-10 * totalCurseLevel);
            player.level().addFreshEntity(experienceOrb);
        }

    }

    @SubscribeEvent
    public static void sneezingCurse(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        Holder.Reference<Enchantment> sneezingCurseHolder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "sneezing_curse")));
        int curseLevel = player.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(sneezingCurseHolder);

        if (curseLevel > 0 && Utils.random.nextIntBetweenInclusive(1, 6000) == 999) {
            int multX = Utils.random.nextBoolean() ? 1 : -1;
            int multZ = Utils.random.nextBoolean() ? 1 : -1;
            if (!player.level().isClientSide) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), com.example.cqsarmory.registry.SoundRegistry.SNEEZE_SOUND, SoundSource.PLAYERS, 2f, 1f);
                player.push(multX * Utils.random.nextFloat(), 1, multZ * Utils.random.nextFloat());
                player.hurtMarked = true;
            }
        }

    }
    @SubscribeEvent
    public static void checkDodge (LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (Utils.random.nextDouble() <= entity.getAttributeValue(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(AttributeRegistry.DODGE_CHANCE.get()))) {
            MagicManager.spawnParticles(entity.level(), ParticleTypes.POOF, entity.getX(), entity.getY() + 1, entity.getZ(), 2, 1 * Utils.random.nextDouble(), 1, 1 * Utils.random.nextDouble(), Utils.random.nextDouble(), false);
            MagicManager.spawnParticles(entity.level(), ParticleTypes.POOF, entity.getX(), entity.getY() + 1, entity.getZ(), 2, -1 * Utils.random.nextDouble(), 1, -1 * Utils.random.nextDouble(), Utils.random.nextDouble(), false);
            MagicManager.spawnParticles(entity.level(), ParticleTypes.POOF, entity.getX(), entity.getY() + 1, entity.getZ(), 2, -1 * Utils.random.nextDouble(), 1, 1 * Utils.random.nextDouble(), Utils.random.nextDouble(), false);
            MagicManager.spawnParticles(entity.level(), ParticleTypes.POOF, entity.getX(), entity.getY() + 1, entity.getZ(), 2, 1 * Utils.random.nextDouble(), 1, -1 * Utils.random.nextDouble(), Utils.random.nextDouble(), false);

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), com.example.cqsarmory.registry.SoundRegistry.DODGE_SOUND, SoundSource.PLAYERS, 1, 1.5f);

            event.setCanceled(true);
        }

    }

}
