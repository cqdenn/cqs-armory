package com.example.cqsarmory.events;


import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.config.ServerConfigs;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.data.DodgeData;
import com.example.cqsarmory.data.effects.CQMobEffectInstance;
import com.example.cqsarmory.data.entity.ability.*;
import com.example.cqsarmory.items.curios.OnHitBrand;
import com.example.cqsarmory.items.curios.OnHitCoating;
import com.example.cqsarmory.items.curios.OnSwingCoating;
import com.example.cqsarmory.items.curios.brands.ArcaneBrand;
import com.example.cqsarmory.network.*;
import com.example.cqsarmory.registry.*;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.events.ChangeManaEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import io.redspace.ironsspellbooks.entity.spells.acid_orb.AcidOrb;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import io.redspace.ironsspellbooks.entity.spells.thrown_spear.ThrownSpear;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
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
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Objects;

@EventBusSubscriber
public class ServerEvents {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity().getKillCredit();
        if (event.getSource().typeHolder().is(DamageTypes.VOLCANO) || (entity instanceof LivingEntity livingEntity1 && ItemRegistry.VOLCANO_COATING.get().isEquippedBy(livingEntity1) && event.getSource().getEntity() == event.getSource().getDirectEntity())/* || (entity instanceof LivingEntity livingEntity && livingEntity.getMainHandItem().is(ItemRegistry.VOLCANO))*/) {
            float damage = entity instanceof LivingEntity living ? (float) living.getAttributeValue(Attributes.ATTACK_DAMAGE) : 20f;
            if (entity instanceof VolcanoExplosion volcano) {
                damage = volcano.getDamage();
            }
            VolcanoExplosion volcanoExplosion = new VolcanoExplosion(event.getEntity().level(), (LivingEntity) entity, damage, 4);
            volcanoExplosion.moveTo(event.getEntity().position().add(0, 1, 0));
            event.getEntity().level().addFreshEntity(volcanoExplosion);
            if (event.getSource().getDirectEntity() instanceof VolcanoExplosion volcanoExplosion1) {
                volcanoExplosion.setOwner(volcanoExplosion1.getOwner());
            }
        }
    }

    @SubscribeEvent
    public static void stunned(LivingIncomingDamageEvent event) {
        DamageData.get(event.getEntity()).lastSource = event.getSource();
        DamageData.get(event.getEntity()).lastDamage = event.getAmount();

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

    /*@SubscribeEvent
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
    }*/

    @SubscribeEvent
    public static void fireAspectBuff(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();

        if (source == null) {
            return;
        }

        Level level = target.level();
        Holder.Reference<Enchantment> fireAspectHolder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT);
        if (source instanceof LivingEntity attacker) {
            int fireAspectLevel = attacker.getMainHandItem().getEnchantmentLevel(fireAspectHolder);

            if (fireAspectLevel > 0 && !level.isClientSide && AbilityData.get(attacker).fireAspectCooldownEnd < attacker.tickCount) {
                FireField fire = new FireField(level);
                fire.setOwner(attacker);
                fire.setDuration(100);
                fire.setDamage(2 * fireAspectLevel);
                fire.setRadius(2 * fireAspectLevel);
                fire.setCircular();
                fire.moveTo(target.position());
                level.addFreshEntity(fire);
                AbilityData.get(attacker).fireAspectCooldownEnd = attacker.tickCount + (20 * 5);
            }
        }

    }

    @SubscribeEvent
    public static void frostAspect(LivingDeathEvent event) {
        var damageSource = event.getSource();
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (damageSource != null && damageSource.getEntity() instanceof LivingEntity attacker) {
            Holder.Reference<Enchantment> frostAspectHolder = attacker.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "frost_aspect")));
            int frostAspectMainHand = attacker.getMainHandItem().getEnchantmentLevel(frostAspectHolder);
            int frostAspectOffHand = attacker.getOffhandItem().getEnchantmentLevel(frostAspectHolder);
            int frostAspectLevel = frostAspectMainHand + frostAspectOffHand;
            int damage = event.getSource().getDirectEntity() instanceof Arrow arrow ? (int) arrow.getBaseDamage() : (int) DamageData.get(entity).lastDamage;

            if (frostAspectLevel > 0) {
                FrozenHumanoid iceClone = new FrozenHumanoid(level, entity);
                iceClone.setSummoner(attacker);
                iceClone.setShatterDamage(damage);
                iceClone.setDeathTimer(20 * 5);
                level.addFreshEntity(iceClone);
                entity.deathTime = 1000;
                iceClone.playSound(SoundRegistry.FROSTBITE_FREEZE.get(), 2, Utils.random.nextInt(9, 11) * .1f);
            }

        }
    }

    @SubscribeEvent
    public static void lightningAspect(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Entity entityAttacker = event.getSource().getEntity();
        float rand = Utils.random.nextFloat();
        Level level = entity.level();
        if (entityAttacker instanceof LivingEntity attacker && !(event.getSource().getDirectEntity() instanceof ChainLightning)) {
            Holder.Reference<Enchantment> lightningAspectHolder = attacker.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "lightning_aspect")));
            int lightningAspectMainHand = attacker.getMainHandItem().getEnchantmentLevel(lightningAspectHolder);
            int lightningAspectOffHand = attacker.getOffhandItem().getEnchantmentLevel(lightningAspectHolder);
            int lightningAspectLevel = Math.max(lightningAspectOffHand, lightningAspectMainHand);
            if (rand <= 0.35 && lightningAspectLevel > 0) {
                float dmg = event.getAmount() * 0.5f;
                ChainLightning chainLightning = new ChainLightning(level, attacker, entity);
                chainLightning.setDamage(dmg);
                chainLightning.maxConnections = 3 * lightningAspectLevel;
                chainLightning.range = 8;
                level.addFreshEntity(chainLightning);
            }
        }
    }

    @SubscribeEvent
    public static void poisonAspect(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Entity entityAttacker = event.getSource().getEntity();
        if (entityAttacker instanceof LivingEntity attacker) {
            Holder.Reference<Enchantment> poisonAspectHolder = attacker.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "poison_aspect")));
            int poisonAspectMainHand = attacker.getMainHandItem().getEnchantmentLevel(poisonAspectHolder);
            int poisonAspectOffHand = attacker.getOffhandItem().getEnchantmentLevel(poisonAspectHolder);
            int poisonAspectLevel = Math.max(poisonAspectOffHand, poisonAspectMainHand);
            if (poisonAspectLevel > 0) {
                entity.addEffect(new MobEffectInstance(io.redspace.ironsspellbooks.registries.MobEffectRegistry.BLIGHT, 80 * poisonAspectLevel, poisonAspectLevel));
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 80 * poisonAspectLevel, poisonAspectLevel - 1));
            }
        }
    }

    @SubscribeEvent
    public static void stealingEnchants(LivingDamageEvent.Post event) {
        Entity entity = event.getSource().getEntity();
        if (entity instanceof Player player) {
            Holder.Reference<Enchantment> lifeStealHolder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "life_steal")));
            Holder.Reference<Enchantment> manaStealHolder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "mana_steal")));
            Holder.Reference<Enchantment> speedStealHolder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "speed_steal")));
            int lifeStealLevel = Math.max(player.getOffhandItem().getEnchantmentLevel(lifeStealHolder), player.getMainHandItem().getEnchantmentLevel(lifeStealHolder));
            int manaStealLevel = Math.max(player.getOffhandItem().getEnchantmentLevel(manaStealHolder), player.getMainHandItem().getEnchantmentLevel(manaStealHolder));
            int speedStealLevel = Math.max(player.getOffhandItem().getEnchantmentLevel(speedStealHolder), player.getMainHandItem().getEnchantmentLevel(speedStealHolder));
            //life steal
            if (lifeStealLevel > 0) {
                player.heal(lifeStealLevel);
            }
            //mana steal
            if (manaStealLevel > 0 && player instanceof ServerPlayer serverPlayer) {
                MagicData.getPlayerMagicData(serverPlayer).addMana(5 * manaStealLevel);
                PacketDistributor.sendToPlayer(serverPlayer, new SyncManaPacket(MagicData.getPlayerMagicData(serverPlayer)));
            }
            //speed steal
            if (speedStealLevel > 0) {
                player.addEffect(new MobEffectInstance(MobEffectRegistry.SPEED_STEAL, 40 + (20 * (speedStealLevel - 1)), speedStealLevel - 1, false, false, true));
                if (event.getEntity() instanceof LivingEntity living) {
                    living.addEffect(new MobEffectInstance(MobEffectRegistry.SPEED_STOLEN, 40 + (20 * (speedStealLevel - 1)), speedStealLevel - 1, false, false, true));
                }
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
        var damageSource = CQSpellRegistry.RIPOSTE_SPELL.get().getDamageSource(entity);

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
            /*MagicManager.spawnParticles(entity.level(), ParticleTypes.POOF, entity.getX(), entity.getY() + 1, entity.getZ(), 15, 1 * Utils.random.nextDouble(), 1, 1 * Utils.random.nextDouble(), Utils.random.nextDouble(), false);
            MagicManager.spawnParticles(entity.level(), ParticleTypes.POOF, entity.getX(), entity.getY() + 1, entity.getZ(), 15, -1 * Utils.random.nextDouble(), 1, -1 * Utils.random.nextDouble(), Utils.random.nextDouble(), false);
            MagicManager.spawnParticles(entity.level(), ParticleTypes.POOF, entity.getX(), entity.getY() + 1, entity.getZ(), 15, -1 * Utils.random.nextDouble(), 1, 1 * Utils.random.nextDouble(), Utils.random.nextDouble(), false);
            MagicManager.spawnParticles(entity.level(), ParticleTypes.POOF, entity.getX(), entity.getY() + 1, entity.getZ(), 15, 1 * Utils.random.nextDouble(), 1, -1 * Utils.random.nextDouble(), Utils.random.nextDouble(), false);
            */

            Vec3 pos = entity.position().add(0, entity.getBbHeight() / 2, 0);
            MagicManager.spawnParticles(entity.level(), ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 70, entity.getBbWidth() / 4, entity.getBbHeight() / 5, entity.getBbWidth() / 4, .035, false);

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), com.example.cqsarmory.registry.SoundRegistry.DODGE_SOUND, SoundSource.PLAYERS, 1, 1.5f);

            //entity.removeEffect(MobEffectRegistry.DODGE);

            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void stopUsingShield(LivingEntityUseItemEvent.Stop event) {
        if (ServerConfigs.DISABLE_SHIELD_CHANGES.get()) return;

        LivingEntity livingEntity = event.getEntity();
        Item item = livingEntity.getUseItem().getItem();

        if (item instanceof ShieldItem && livingEntity instanceof Player player) {
            CQtils.disableShield(player, 20);
            AbilityData.get(player).currentShieldDamage = 0;
        }

    }

    @SubscribeEvent
    public static void shieldBlock(LivingShieldBlockEvent event) {
        if (ServerConfigs.DISABLE_SHIELD_CHANGES.get()) return;

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
    public static void shieldEffects(LivingShieldBlockEvent event) {
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
    public static void rageOnHit(LivingDamageEvent.Pre event) {
        if (ServerConfigs.DISABLE_RAGE.get()) return;

        Entity directEntity = event.getSource().getDirectEntity();
        Entity sourceEntity = event.getSource().getEntity();
        DamageSource dmgSource = event.getSource();

        if (sourceEntity instanceof Player player && dmgSource.is(Tags.DamageTypes.CAUSES_RAGE_GAIN)) {
            int abilityGainMultiplier = dmgSource.is(DamageTypes.MELEE_SKILL) ? 5 : 1;
            if (AbilityData.get(player).getRage() > 0) {
                event.setNewDamage(event.getNewDamage() + (event.getNewDamage() * (float) player.getAttribute(AttributeRegistry.RAGE_DAMAGE).getValue() * AbilityData.get(player).getRage()));
            }

            float newRageTest = (AbilityData.get(player).getRage() + ((float) player.getAttribute(AttributeRegistry.RAGE_ON_HIT).getValue() * abilityGainMultiplier));
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
    public static void outOfCombatRageLoss(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        if ((!AbilityData.inCombatRage(player) || player.hasEffect(MobEffectRegistry.BERSERK)) && player.level().getGameTime() % 20 == 0) {
            float newRageTest = ((float) (AbilityData.get(player).getRage() - player.getAttribute(AttributeRegistry.MAX_RAGE).getValue() * 0.1));
            float newRage = newRageTest > player.getAttribute(AttributeRegistry.MIN_RAGE).getValue() ? newRageTest : (float) player.getAttribute(AttributeRegistry.MIN_RAGE).getValue();
            AbilityData.get(player).setRage(newRage);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncRagePacket((int) newRage));
        }

        /*if (player.hasEffect(MobEffectRegistry.BERSERK) && player.level().getGameTime() % 20 == 0) {
            float newRageTest = ((float) (AbilityData.get(player).getRage() - player.getAttribute(AttributeRegistry.MAX_RAGE).getValue() * 0.1));
            float newRage = newRageTest > player.getAttribute(AttributeRegistry.MIN_RAGE).getValue() ? newRageTest : (float) player.getAttribute(AttributeRegistry.MIN_RAGE).getValue();
            AbilityData.get(player).setRage(newRage);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncRagePacket((int) newRage));
        }*/
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
    public static void momentumSpeed(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            var speedStacks = AbilityData.get(player).momentumOrbEffects.speedStacks;
            //capped at +100% speed, TBD, fix in momentumspeedoverlay if changed FIXME
            float speed = (float) Math.min(speedStacks * 0.1, 1);

            ResourceLocation orbSpeedModifier = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "orb_speed_modifier");
            AttributeModifier speedModifierOrb = new AttributeModifier(orbSpeedModifier, speed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
            attributeinstance.removeModifier(speedModifierOrb.id());

            if (speedStacks > 0) {
                attributeinstance.addTransientModifier(speedModifierOrb);
            }
            if (AbilityData.get(player).momentumOrbEffects.speedEnd < player.tickCount && player.level().getGameTime() % 20 == 0) {
                int newSpeedStacks = Math.max(AbilityData.get(player).momentumOrbEffects.speedStacks - 1, 0);
                AbilityData.get(player).momentumOrbEffects.speedStacks = newSpeedStacks;
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumSpeedPacket(newSpeedStacks));
            }
        }

    }

    @SubscribeEvent
    public static void momentumOrbArrowDamage(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            var dmgStacks = AbilityData.get(player).momentumOrbEffects.arrowDamageStacks;
            //capped at +100% dmg, TBD, fix in momentumdamageoverlay if changed FIXME
            float dmg = (float) Math.min(dmgStacks * 0.1, 1);

            ResourceLocation orbDmgModifier = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "orb_dmg_modifier");
            AttributeModifier dmgModifierOrb = new AttributeModifier(orbDmgModifier, dmg, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            AttributeInstance attributeinstance = player.getAttribute(BowAttributes.ARROW_DAMAGE);
            attributeinstance.removeModifier(dmgModifierOrb.id());

            if (dmgStacks > 0) {
                attributeinstance.addTransientModifier(dmgModifierOrb);
            }
            if (AbilityData.get(player).momentumOrbEffects.arrowDamageEnd < player.tickCount && player.level().getGameTime() % 20 == 0) {
                int newDamageStacks = Math.max(AbilityData.get(player).momentumOrbEffects.arrowDamageStacks - 1, 0);
                AbilityData.get(player).momentumOrbEffects.arrowDamageStacks = newDamageStacks;
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumDamagePacket(newDamageStacks));
            }
        }

    }

    @SubscribeEvent
    public static void momentumOnHit(LivingDamageEvent.Pre event) {
        if (ServerConfigs.DISABLE_MOMENTUM.get()) return;

        Entity directEntity = event.getSource().getDirectEntity();
        Entity sourceEntity = event.getSource().getEntity();
        DamageSource dmgSource = event.getSource();
        Entity target = event.getEntity();

        if (directEntity instanceof AbstractArrow && !(directEntity instanceof ScytheProjectile) && sourceEntity instanceof Player player && !dmgSource.is(Tags.DamageTypes.CAUSES_RAGE_GAIN)) {

            if (AbilityData.get(player).getMomentum() == player.getAttribute(AttributeRegistry.MAX_MOMENTUM).getValue()) {
                AbilityData.get(player).setMomentum((float) player.getAttribute(AttributeRegistry.MIN_MOMENTUM).getValue());
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumPacket((int) player.getAttribute(AttributeRegistry.MIN_MOMENTUM).getValue()));
                //add logic for creating momentum orbs
                Level level = player.level();
                int startX = Utils.random.nextBoolean() ? 1 : -1;
                int startZ = Utils.random.nextBoolean() ? 1 : -1;
                Vec3 startingPos = target == null ? player.getEyePosition().add(0, 1, 0) : target.getEyePosition().add(startX * Utils.random.nextFloat(), 2, startZ * Utils.random.nextFloat());


                int orbsSpawned = (int) player.getAttribute(AttributeRegistry.MOMENTUM_ORBS_SPAWNED).getValue();
                for (int i = 0; i < orbsSpawned; i++) {
                    MomentumOrb orb = CQtils.getRandomOrbType(level, player);
                    if (ItemRegistry.BLASTER.get().isEquippedBy(player)) {
                        orb = new ExplosiveMomentumOrb(EntityRegistry.EXPLOSIVE_MOMENTUM_ORB.get(), level, player);
                    }
                    CQtils.findOrbLoc(startingPos, orb, level);
                    level.addFreshEntity(orb);
                }

            } else {

                int abilityGainMultiplier = directEntity instanceof AbilityArrow abilityArrow && abilityArrow.getShotFromAbility() ? 3 : 1;
                float newMomentumTest = (AbilityData.get(player).getMomentum() + (float) player.getAttribute(AttributeRegistry.MOMENTUM_ON_HIT).getValue() * abilityGainMultiplier);
                float newMomentum = newMomentumTest < player.getAttribute(AttributeRegistry.MAX_MOMENTUM).getValue() ? newMomentumTest : (float) player.getAttribute(AttributeRegistry.MAX_MOMENTUM).getValue();
                AbilityData.get(player).setMomentum(newMomentum);
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumPacket((int) newMomentum));

                //remove rage on momentum gain
            /*AbilityData.get(player).setRage(0);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncRagePacket((int) 0));*/


                AbilityData.get(player).combatEndMomentum = player.tickCount + (20 * 15);
            }

        }

    }

    @SubscribeEvent
    public static void outOfCombatMomentumLoss(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        if (!AbilityData.inCombatMomentum(player) && player.level().getGameTime() % 20 == 0) {
            float newMomentumTest = ((float) (AbilityData.get(player).getMomentum() - 5));
            float newMomentum = newMomentumTest > player.getAttribute(AttributeRegistry.MIN_MOMENTUM).getValue() ? newMomentumTest : (float) player.getAttribute(AttributeRegistry.MIN_MOMENTUM).getValue();
            AbilityData.get(player).setMomentum(newMomentum);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncMomentumPacket((int) newMomentum));

        }
    }

    @SubscribeEvent
    public static void shootMomentumOrb(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof MomentumOrb momentumOrb && !DamageSources.isFriendlyFireBetween(momentumOrb.getCreator(), event.getProjectile().getOwner()) && momentumOrb.getCreator() != event.getProjectile().getOwner()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void mageAOE(PlayerTickEvent.Pre event) {
        if (ServerConfigs.DISABLE_MAGE_AOE.get()) return;

        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        float defaultMinManaSpent = ItemRegistry.MANASAVER.get().isEquippedBy(player) ? 250 : 500;
        int seconds = ItemRegistry.CHRONOWARP_RUNE.get().isEquippedBy(player) ? 16 : 8;

        if (AbilityData.get(player).manaSpentSinceLastAOE >= defaultMinManaSpent) {
            if (ItemRegistry.HELLFIRE_SIGIL.get().isEquippedBy(player)) {
                player.addEffect(new MobEffectInstance(MobEffectRegistry.HELLFIRE_MAGE_AOE, (20 * seconds), 0, false, false, false));
            } else {
                player.addEffect(new MobEffectInstance(MobEffectRegistry.GENERIC_MAGE_AOE, (20 * seconds), 0, false, false, false));
            }
            AbilityData.get(player).manaSpentSinceLastAOE = 0;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncManaSpentPacket(0));
        }

    }

    @SubscribeEvent
    public static void trackManaSpent(ChangeManaEvent event) {
        if (ServerConfigs.DISABLE_MAGE_AOE.get()) return;

        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        float manaSpent = event.getOldMana() - event.getNewMana();

        if (manaSpent > 0 && !player.hasEffect(MobEffectRegistry.GENERIC_MAGE_AOE) && !player.hasEffect(MobEffectRegistry.HELLFIRE_MAGE_AOE)) {
            float newManaSpent = AbilityData.get(player).manaSpentSinceLastAOE + manaSpent;
            AbilityData.get(player).manaSpentSinceLastAOE = newManaSpent;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncManaSpentPacket((int) newManaSpent));
            AbilityData.get(player).startMageAOEDecay = player.tickCount + (20 * 5);
        }
    }

    @SubscribeEvent
    public static void manaSpentDecay(PlayerTickEvent.Pre event) {
        if (ServerConfigs.DISABLE_MAGE_AOE.get()) return;

        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        float manaNeeded = ItemRegistry.MANASAVER.get().isEquippedBy(player) ? 250 : 500;
        if (AbilityData.get(player).startMageAOEDecay < player.tickCount && player.level().getGameTime() % 20 == 0) {
            float newManaSpent = Math.max(AbilityData.get(player).manaSpentSinceLastAOE - (manaNeeded / 10), 0);
            AbilityData.get(player).manaSpentSinceLastAOE = newManaSpent;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncManaSpentPacket((int) newManaSpent));
        }
    }

    @SubscribeEvent
    public static void damageReduction(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && ItemRegistry.WARDSTONE.get().isEquippedBy(player) && AbilityData.get(player).getRage() >= player.getAttribute(AttributeRegistry.MAX_RAGE).getValue()) {
            event.setAmount(event.getAmount() * 0.75f);
        }
        if (ServerConfigs.DISABLE_IFRAMES.get() || event.getSource().getDirectEntity() instanceof AbilityArrow || event.getSource().is(DamageTypes.BLEEDING) || event.getSource().is(net.neoforged.neoforge.common.Tags.DamageTypes.IS_POISON)) {
            event.setInvulnerabilityTicks(0);
        }
        if (DamageData.get(event.getEntity()).markedBy == event.getSource().getEntity() && event.getSource().getDirectEntity() instanceof AbstractArrow) {
            event.setAmount(event.getAmount() * 2);
            DamageData.get(event.getEntity()).markedBy = null;
        }
        if (DodgeData.get(event.getEntity()).invulnerableTimeEnd > event.getEntity().level().getGameTime()) {
            event.setCanceled(true);
        }
        if (event.getEntity().hasEffect(MobEffectRegistry.SPIN)) {
            event.setAmount(event.getAmount() * 0.75f);
        }
    }

    @SubscribeEvent
    public static void arrowPierce(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() != null && arrow.getOwner() instanceof LivingEntity living) {
            arrow.setPierceLevel((byte) (arrow.getPierceLevel() + living.getAttributeValue(AttributeRegistry.ARROW_PIERCING)));
        }
    }

    @SubscribeEvent
    public static void quiverArrows(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        int capacity = (int) player.getAttribute(AttributeRegistry.QUIVER_CAPACITY).getValue();
        if (player.level().isClientSide) return;
        if (AbilityData.get(player).quiverArrowCount < capacity && player.level().getGameTime() % 40 == 0 && !CQtils.getPlayerCurioStack(player, "quiver").isEmpty()) {
            int newArrowCount = AbilityData.get(player).quiverArrowCount + 1;
            AbilityData.get(player).quiverArrowCount = newArrowCount;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncQuiverArrowsPacket(newArrowCount));
        }
    }

    @SubscribeEvent
    public static void brandEffects(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        if (ItemRegistry.VEIL_BRAND.get().isEquippedBy(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false, true));
        }

        if (ItemRegistry.ARCANE_BRAND.get().isEquippedBy(player) && player instanceof ServerPlayer serverPlayer) {
            var RING = ((ArcaneBrand) ItemRegistry.ARCANE_BRAND.get());
            if ((MagicData.getPlayerMagicData(player).getMana() <= player.getAttributeValue(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA) * 0.1) && RING.tryProcCooldown(serverPlayer)) {
                MagicData.getPlayerMagicData(serverPlayer).setMana((float) serverPlayer.getAttributeValue(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA));
                PacketDistributor.sendToPlayer(serverPlayer, new SyncManaPacket(MagicData.getPlayerMagicData(serverPlayer)));
            }
        }

    }

    /*@SubscribeEvent
    public static void infinityBrandCD(SpellCooldownAddedEvent.Pre event) {
        Player player = event.getEntity();
        if (player.hasEffect(MobEffectRegistry.INFINITE_MAGIC) && event.getSpell() != CQSpellRegistry.INFINITE_MAGIC_SPELL.get()) {
            event.setEffectiveCooldown(0);
        }
    }*/ // removed for being too broken

    @SubscribeEvent
    public static void infinityBrandMana(ChangeManaEvent event) {
        Player player = event.getEntity();
        if (player.hasEffect(MobEffectRegistry.INFINITE_MAGIC)) {
            float oldMana = event.getOldMana();
            float newMana = event.getNewMana();
            if (newMana - oldMana < 0) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void explosiveDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Entity entity = event.getSource().getEntity();
        if (entity instanceof Player attacker) {
            float multiplier = (float) attacker.getAttributeValue(AttributeRegistry.EXPLOSIVE_DAMAGE);
            if (event.getSource().is(Tags.DamageTypes.EXPLOSIVE_DAMAGE) || (event.getSource().getDirectEntity() != null && event.getSource().getDirectEntity().getType().is(Tags.EntityTypes.EXPLOSIVE_ENTITIES))) {
                event.setAmount(event.getAmount() * multiplier);
            }
        }
    }

    @SubscribeEvent
    public static void bowVelocity(ArrowLooseEvent event) {
        int charge = event.getCharge();
        Player player = event.getEntity();

        float f = (float) charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        AbilityData.get(player).bowVelocity = f;
    }

    @SubscribeEvent
    public static void poisonEffects(LivingIncomingDamageEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity instanceof Player player) {
            Level level = player.level();
            if (ItemRegistry.POISON_QUIVER.get().isEquippedBy(player) && event.getSource().getDirectEntity() instanceof AbstractArrow) {
                AbilityData.get(player).poisonStacks++;
            } else if (ItemRegistry.POISON_COATING.get().isEquippedBy(player) && event.getSource().getDirectEntity() == event.getSource().getEntity()) {
                AbilityData.get(player).poisonStacks++;
            }
            if (AbilityData.get(player).poisonStacks >= 20) {
                AbilityData.get(player).poisonStacks = 0;
                LivingEntity target = event.getEntity();
                AcidOrb orb = new AcidOrb(level, player);
                orb.setPos(target.position().add(0, 1, 0));
                orb.shoot(new Vec3(0, 0.5f, 0));
                //orb.setDeltaMovement(orb.getDeltaMovement().add(0, 0.2, 0));
                orb.setExplosionRadius(3);
                orb.setRendLevel(4);
                orb.setRendDuration(200);
                level.addFreshEntity(orb);
            }
        }
    }

    @SubscribeEvent
    public static void onHitEffects(LivingDamageEvent.Post event) {
        Entity entity = event.getSource().getEntity();
        Entity directEntity = event.getSource().getDirectEntity();
        DamageSource source = event.getSource();
        float damage = event.getNewDamage();
        LivingEntity target = event.getEntity();
        if (entity instanceof Player player) {
            var coatingSlot = CQtils.getPlayerCurioStack(player, "coating");
            var brandSlot = CQtils.getPlayerCurioStack(player, "brand");
            if (!coatingSlot.isEmpty() && coatingSlot.getItem() instanceof OnHitCoating coating && source.is(net.minecraft.world.damagesource.DamageTypes.PLAYER_ATTACK)) {
                coating.doOnHitEffect(player, target, damage);
            } else if (!coatingSlot.isEmpty() && coatingSlot.getItem() instanceof OnSwingCoating coating && source.is(net.minecraft.world.damagesource.DamageTypes.PLAYER_ATTACK)) {
                coating.doOnSwingEffect(player, damage);
            } else if (!brandSlot.isEmpty() && brandSlot.getItem() instanceof OnHitBrand brand) {

                final List<ResourceKey<DamageType>> spellTypes = List.of(
                        ISSDamageTypes.FIRE_MAGIC,
                        ISSDamageTypes.HOLY_MAGIC,
                        ISSDamageTypes.ICE_MAGIC,
                        ISSDamageTypes.LIGHTNING_MAGIC,
                        ISSDamageTypes.BLOOD_MAGIC,
                        ISSDamageTypes.ELDRITCH_MAGIC,
                        ISSDamageTypes.EVOCATION_MAGIC,
                        ISSDamageTypes.NATURE_MAGIC,
                        ISSDamageTypes.ENDER_MAGIC
                );

                for (ResourceKey<DamageType> spellType : spellTypes) {
                    if (source.is(spellType)) {
                        brand.doOnHitEffect(player, target, damage, source);
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void bleedStacks(MobEffectEvent.Added event) {
        MobEffectInstance newInstance = event.getEffectInstance();
        MobEffectInstance oldInstance = event.getOldEffectInstance();
        LivingEntity living = event.getEntity();
        if (newInstance.getEffect() == MobEffectRegistry.BLEED && oldInstance != null) {
            if (newInstance instanceof CQMobEffectInstance newInstanceCQ && oldInstance instanceof CQMobEffectInstance oldInstanceCQ && newInstanceCQ.getOwner() == oldInstanceCQ.getOwner() && newInstanceCQ.getTriggersEvent()) {
                int newAmplifier = newInstance.getAmplifier() + oldInstance.getAmplifier() + 1;
                newAmplifier = Math.min(newAmplifier, 9); //bleed capped at 10 stacks
                int duration = Math.max(newInstance.getDuration(), oldInstance.getDuration());
                living.addEffect(new CQMobEffectInstance(MobEffectRegistry.BLEED, duration, newAmplifier, false, false, true, newInstanceCQ.getOwner(), false));
            }
        }
    }

    @SubscribeEvent
    public static void bleedChance(LivingIncomingDamageEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity instanceof LivingEntity attacker && Utils.random.nextFloat() <= attacker.getAttributeValue(AttributeRegistry.BLEED_CHANCE)) {
            event.getEntity().addEffect(new CQMobEffectInstance(MobEffectRegistry.BLEED, 40, 0, false, false, true, attacker, true));
        }
    }

    @SubscribeEvent
    public static void cancelFallDmg(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (DamageData.get(entity).cancelNextFall + 200 >= entity.level().getGameTime()) {
            event.setCanceled(true);
            DamageData.get(entity).cancelNextFall = 0;
        }

    }

    @SubscribeEvent
    public static void autoCrit(CriticalHitEvent event) {
        Player player = event.getEntity();
        double autoCrit = player.getAttribute(AttributeRegistry.AUTO_CRIT).getValue();
        if (player.hasEffect(MobEffectRegistry.AUTO_CRIT)) {
            event.setCriticalHit(true);
            event.setDisableSweep(true);
            event.setDamageMultiplier(event.getDamageMultiplier() == 1f ? 1.5f : event.getDamageMultiplier());
        } else if (event.isVanillaCritical() && autoCrit > 0) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.AUTO_CRIT, (int) (autoCrit * 20), 0, false, false, true));
        }
    }

    @SubscribeEvent
    public static void chainWhipTether(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity living) {
            if (!living.hasEffect(MobEffectRegistry.CHAINED) && DamageData.get(living).chainWhipLocation != null) {
                DamageData.get(living).chainWhipLocation = null;
            }
            if (living.hasEffect(MobEffectRegistry.CHAINED) && DamageData.get(living).chainWhipLocation != null) {
                Vec3 delta = DamageData.get(living).chainWhipLocation.subtract(living.position());
                double distance = delta.length();
                double springConstant = 0.15 / 1.5;
                Vec3 force = delta.normalize().scale(distance * distance * springConstant);
                living.push(force);
                living.hurtMarked = true;
            }

        }
    }

}
