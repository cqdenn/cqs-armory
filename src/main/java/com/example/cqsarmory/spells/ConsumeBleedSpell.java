package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.config.ServerConfigs;
import com.example.cqsarmory.data.effects.BleedEffect;
import com.example.cqsarmory.data.effects.CQMobEffectInstance;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class ConsumeBleedSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "consume_bleed_spell");
    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(60)
            .build();

    public ConsumeBleedSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 0;
    }

    @Override
    public boolean allowCrafting() {
        return false;
    }

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.empty();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.literal("Consumes bleed stacks for the damage it would deal")
        );
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        var target = Utils.raycastForEntity(entity.level(), entity, 64, true, 0.1f);
        if (target instanceof EntityHitResult entityHitResult && !entity.level().isClientSide) {
            if (entityHitResult.getEntity() instanceof LivingEntity living && living.getEffect(MobEffectRegistry.BLEED) instanceof CQMobEffectInstance effectInstance) {
                if (effectInstance.getOwner() == entity) {
                    if (entity instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.spell_target_success", living.getDisplayName().getString(), this.getDisplayName(serverPlayer)).withStyle(ChatFormatting.GREEN)));
                    }
                    playerMagicData.setAdditionalCastData(new TargetEntityCastData(living));
                    return true;
                }
            }
        }
        if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.cqs_armory.no_bleed_stack_error").withStyle(ChatFormatting.RED)));
        }
        return false;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);

        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetingData) {
            var target = targetingData.getTarget((ServerLevel) level);
            if (target != null) {
                MobEffectInstance effectInstance = target.getEffect(MobEffectRegistry.BLEED);
                int stacks = effectInstance.getAmplifier() + 1;
                int seconds = (int) Math.ceil(effectInstance.getDuration() / 20);
                float damage = BleedEffect.DAMAGE_PER_STACK * stacks * seconds;
                float explosionRadius = 5;
                MagicManager.spawnParticles(level, ParticleHelper.BLOOD, target.getX(), target.getY() + .25f, target.getZ(), 100, .03, .4, .03, .4, true);
                MagicManager.spawnParticles(level, ParticleHelper.BLOOD, target.getX(), target.getY() + .25f, target.getZ(), 100, .03, .4, .03, .4, false);
                MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.BLOOD.get().getTargetingColor(), explosionRadius), target.getX(), target.getBoundingBox().getCenter().y, target.getZ(), 1, 0, 0, 0, 0, true);
                var entities = level.getEntities(null, target.getBoundingBox().inflate(explosionRadius));
                for (Entity victim : entities) {
                    double distanceSqr = victim.distanceToSqr(target.position());
                    if (victim.canBeHitByProjectile() && distanceSqr < explosionRadius * explosionRadius && Utils.hasLineOfSight(level, target.getBoundingBox().getCenter(), victim.getBoundingBox().getCenter(), true)) {
                        float p = (float) (distanceSqr / (explosionRadius * explosionRadius));
                        p = 1 - p * p * p;
                        DamageSources.applyDamage(victim, damage * p, new DamageSource(entity.level().damageSources().damageTypes.getHolder(DamageTypes.BLEEDING).get(), null, entity));
                    }
                }
                CameraShakeManager.addCameraShake(new CameraShakeData(10, target.position(), 20));
                target.removeEffect(MobEffectRegistry.BLEED);
                level.playSound(null, target.blockPosition(), SoundRegistry.BLOOD_EXPLOSION.get(), SoundSource.PLAYERS, 3, Utils.random.nextIntBetweenInclusive(8, 12) * .1f);

            }
        }
    }

    public double getDamagePerStack(LivingEntity caster) {
        return caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5;
    }
}

