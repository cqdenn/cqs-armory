package com.example.cqsarmory.events;


import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.VolcanoExplosion;
import com.example.cqsarmory.items.MjolnirItem;
import com.example.cqsarmory.network.SyncMomentumPacket;
import com.example.cqsarmory.network.SyncRagePacket;
import com.example.cqsarmory.registry.*;
import com.example.cqsarmory.utils.CQtils;
import com.google.gson.internal.NonNullElementWrapperList;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
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
    public static void stunned(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity && livingEntity.hasEffect(MobEffectRegistry.STUNNED)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void stunnedParticles(MobEffectEvent.Added event) {
        Holder<MobEffect> effect = event.getEffectInstance().getEffect();
        Level level = event.getEntity().level();
        LivingEntity entity = event.getEntity();
        if (effect == MobEffectRegistry.STUNNED && level.getServer() != null) {
            MagicManager.spawnParticles(level, ParticleTypes.CRIT, entity.getEyePosition().x, entity.getEyePosition().y + 1, entity.getEyePosition().z, 5, 1 * Utils.random.nextDouble(), Utils.random.nextDouble(), 1 * Utils.random.nextDouble(), 0.2, false);
            MagicManager.spawnParticles(level, ParticleTypes.CRIT, entity.getEyePosition().x, entity.getEyePosition().y + 1, entity.getEyePosition().z, 5, -1 * Utils.random.nextDouble(), Utils.random.nextDouble(), -1 * Utils.random.nextDouble(), 0.2, false);
            MagicManager.spawnParticles(level, ParticleTypes.CRIT, entity.getEyePosition().x, entity.getEyePosition().y + 1, entity.getEyePosition().z, 5, -1 * Utils.random.nextDouble(), Utils.random.nextDouble(), 1 * Utils.random.nextDouble(), 0.2, false);
            MagicManager.spawnParticles(level, ParticleTypes.CRIT, entity.getEyePosition().x, entity.getEyePosition().y + 1, entity.getEyePosition().z, 5, 1 * Utils.random.nextDouble(), Utils.random.nextDouble(), -1 * Utils.random.nextDouble(), 0.2, false);
            level.playSound(null, entity.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1, 1);
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
    public static void fireAspectBuff(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getDirectEntity();

        if (source == null) {
            return;
        }

        Level level = target.level();
        Holder.Reference<Enchantment> fireAspectHolder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT);
        if (source instanceof LivingEntity attacker) {
            int fireAspectLevel = attacker.getMainHandItem().getEnchantmentLevel(fireAspectHolder);

            if (fireAspectLevel > 0 && !level.isClientSide) {
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
    public static void riposte(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damage = event.getSource();
        MagicData magicData = MagicData.getPlayerMagicData(entity);
        int spellLevel = magicData.getCastingSpellLevel();
        var damageSource = entity.level().damageSources().mobAttack(entity);

        if (magicData.isCasting() && Objects.equals(magicData.getCastingSpellId(), "cqs_armory:riposte_spell")) {
            if (damage.getDirectEntity() instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.STUNNED, 40 * spellLevel, 100, false, false, true));
                livingEntity.hurt(damageSource, (float) entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 4);
            } else if (damage.getEntity() instanceof Projectile projectile) {
                projectile.setDeltaMovement(entity.getForward().scale(10));
            }
            if (entity instanceof ServerPlayer serverPlayer) {
                Utils.serverSideCancelCast(serverPlayer, true);
                MagicData.getPlayerMagicData(serverPlayer).getPlayerRecasts().removeAll(RecastResult.USER_CANCEL);
            }
            entity.level().playSound(null, entity.blockPosition(), com.example.cqsarmory.registry.SoundRegistry.RIPOSTE_HIT_SOUND.get(), SoundSource.PLAYERS, 1, 1);
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void checkDodge(LivingIncomingDamageEvent event) {
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

    @SubscribeEvent
    public static void stopUsingShield (LivingEntityUseItemEvent.Stop event) {
        LivingEntity livingEntity = event.getEntity();
        Item item = livingEntity.getUseItem().getItem();

        if (item instanceof ShieldItem && livingEntity instanceof Player player) {
            CQtils.disableShield(player, 20);
            AbilityData.get(player).currentShieldDamage = 0;
        }

    }

    @SubscribeEvent
    public static void shieldBlock (LivingShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        float damage = event.getBlockedDamage();
        if (event.getOriginalBlock()) {
            Item item = livingEntity.getUseItem().getItem();
            if (item instanceof ShieldItem shield && livingEntity instanceof Player player) {
                AbilityData.get(livingEntity).currentShieldDamage += damage;

                if (AbilityData.get(livingEntity).currentShieldDamage >= livingEntity.getAttribute(AttributeRegistry.BLOCK_STRENGTH).getValue()) {
                    CQtils.disableShield(player, 100);
                    AbilityData.get(livingEntity).currentShieldDamage = 0;
                }
            }
        }

    }

    @SubscribeEvent
    public static void shieldEffects (LivingShieldBlockEvent event) {
        boolean blocked = event.getOriginalBlock();
        LivingEntity defender = event.getEntity();
        Entity attacker = event.getDamageSource().getEntity();
        ItemStack item = defender.getUseItem();
        DamageSource damageSource = defender.level().damageSources().mobAttack(defender);


        //thornbark
        if (item.is(ItemRegistry.THORNBARK) && Utils.random.nextFloat() <= 0.35 && blocked) {
            float damage = Utils.random.nextIntBetweenInclusive(4, 8);
            attacker.hurt(damageSource, damage);
        }

    }

    @SubscribeEvent
    public static void rageOnHit (LivingDamageEvent.Pre event) {
        Entity directEntity = event.getSource().getDirectEntity();
        Entity sourceEntity = event.getSource().getEntity();

        if (sourceEntity == directEntity && directEntity instanceof Player player) {
            if (AbilityData.get(player).getRage() > 0) {
                event.setNewDamage(event.getOriginalDamage() + (event.getOriginalDamage() * (float) player.getAttribute(AttributeRegistry.RAGE_DAMAGE).getValue() * AbilityData.get(player).getRage()));
            }

            float newRageTest = (AbilityData.get(player).getRage() + (float) player.getAttribute(AttributeRegistry.RAGE_ON_HIT).getValue());
            float newRage = newRageTest < player.getAttribute(AttributeRegistry.MAX_RAGE).getValue() ? newRageTest : (float) player.getAttribute(AttributeRegistry.MAX_RAGE).getValue();
            AbilityData.get(player).setRage(newRage);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncRagePacket((int) newRage));

            //remove momentum on rage gain
            /*AbilityData.get(player).setMomentum(0);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumPacket((int) 0));*/

            AbilityData.get(player).combatEndRage = player.tickCount + (20 * 5);

        }

    }

    @SubscribeEvent
    public static void outOfCombatRageLoss (PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        if (!AbilityData.inCombatRage(player) && player.level().getGameTime() % 20 == 0) {
            float newRageTest = ((float) (AbilityData.get(player).getRage() - 5));
            float newRage = newRageTest > player.getAttribute(AttributeRegistry.MIN_RAGE) .getValue() ? newRageTest : (float) player.getAttribute(AttributeRegistry.MIN_RAGE) .getValue();
            AbilityData.get(player).setRage(newRage);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncRagePacket((int) newRage));
        }
    }

    @SubscribeEvent
    public static void speedPerRage(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        var rage = AbilityData.get(player).getRage();
        float speed = (rage * (float) player.getAttribute(AttributeRegistry.RAGE_SPEED).getValue());

        ResourceLocation rageSpeedModifier = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "rage_speed_modifier");
        AttributeModifier speedModifierRage = new AttributeModifier(rageSpeedModifier, speed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
        attributeinstance.removeModifier(speedModifierRage.id());

        if (rage > 0) {
            attributeinstance.addTransientModifier(speedModifierRage);
        }
    }

    @SubscribeEvent
    public static void momentumOnHit (LivingDamageEvent.Pre event) {
        Entity directEntity = event.getSource().getDirectEntity();
        Entity sourceEntity = event.getSource().getEntity();

        if (directEntity instanceof Arrow && sourceEntity instanceof Player player) {

            float newMomentumTest = (AbilityData.get(player).getMomentum() + (float) player.getAttribute(AttributeRegistry.MOMENTUM_ON_HIT).getValue());
            float newMomentum = newMomentumTest < player.getAttribute(AttributeRegistry.MAX_MOMENTUM).getValue() ? newMomentumTest : (float) player.getAttribute(AttributeRegistry.MAX_MOMENTUM).getValue();
            AbilityData.get(player).setMomentum(newMomentum);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumPacket((int) newMomentum));

            //remove rage on momentum gain
            /*AbilityData.get(player).setRage(0);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncRagePacket((int) 0));*/


            AbilityData.get(player).combatEndMomentum = player.tickCount + (20 * 5);

            if (AbilityData.get(player).getMomentum() == player.getAttribute(AttributeRegistry.MAX_MOMENTUM).getValue()) {
                //add logic for momentum orbs
            }

        }

    }

    @SubscribeEvent
    public static void outOfCombatMomentumLoss (PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        if (!AbilityData.inCombatMomentum(player) && player.level().getGameTime() % 20 == 0) {
            float newMomentumTest = ((float) (AbilityData.get(player).getMomentum() - 5));
            float newMomentum = newMomentumTest > player.getAttribute(AttributeRegistry.MIN_MOMENTUM) .getValue() ? newMomentumTest : (float) player.getAttribute(AttributeRegistry.MIN_MOMENTUM) .getValue();
            AbilityData.get(player).setMomentum(newMomentum);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumPacket((int) newMomentum));

        }
    }

}
