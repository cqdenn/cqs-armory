package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.data.effects.BleedEffect;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.DamageTypes;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.data.cast.PositionAnchor;
import io.redspace.skillcasting.data.component.TargetedEntitiesData;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import io.redspace.skillcasting.util.RaycastBuilder;
import io.redspace.skillcasting.util.SkillcastingUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Optional;

public class ConsumeBleedSpell extends AbstractSpell {

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
        this.castTime = 10;
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
        return CastType.LONG;
    }

    @Override
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.empty();
    }

    @Override
    public Optional<PlayableSound> getCastStartSound(CastContext castContext) {
        return Optional.empty();
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return new AnimationHolder(SpellAnimations.MOB_ANIMATION_RESOURCE, ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "simple_sword_stab_alternate"), true);
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.stop();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.literal("Consumes bleed stacks for the damage it would deal")
        );
    }

    @Override
    public boolean checkPreCastConditions(CastContext castContext) {
        var target = RaycastBuilder.fromCast(castContext, PositionAnchor.CASTING_POSITION, 2).checkForBlocks(true).bbInflation(0.1f).build();
        var distance = RaycastBuilder.fromCast(castContext, PositionAnchor.CASTING_POSITION, 64).checkForBlocks(true).bbInflation(0.1f).build();
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return false;
        if (target instanceof EntityHitResult entityHitResult && !entity.level().isClientSide) {
            if (entityHitResult.getEntity() instanceof LivingEntity living && DamageData.get(living).bleedStacks.containsKey(entity) && DamageData.get(living).bleedStacks.get(entity) > 0) {
                if (entity instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.spell_target_success", living.getDisplayName().getString(), this.getDisplayName(serverPlayer)).withStyle(ChatFormatting.GREEN)));
                }
                castContext.set(SkillcastingComponentTypes.TARGETED_ENTITIES, new TargetedEntitiesData(living));
                return true;
            }
        }
        if (distance instanceof EntityHitResult && !(target instanceof EntityHitResult) && !entity.level().isClientSide) {
            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.literal("Too Far!").withStyle(ChatFormatting.RED)));
            }
        } else if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.cqs_armory.no_bleed_stack_error").withStyle(ChatFormatting.RED)));
        }
        return false;
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        LivingEntity target = SkillcastingUtils.getTargetedLivingEntity(level, castContext);
        if (target != null && castContext.asEntityCaster() instanceof LivingEntity entity) {
            MobEffectInstance effectInstance = target.getEffect(MobEffectRegistry.BLEED);
            if (DamageData.get(target).bleedStacks.get(entity) == null) return;
            int stacks = DamageData.get(target).bleedStacks.get(entity);
            int seconds = effectInstance.getDuration() / 20;
            float damage = BleedEffect.DAMAGE_PER_STACK * stacks * seconds;
            float explosionRadius = 5;
            MagicManager.spawnParticles(level, ParticleHelper.BLOOD, target.getX(), target.getY() + .25f, target.getZ(), 100, .03, .4, .03, .4, true);
            MagicManager.spawnParticles(level, ParticleHelper.BLOOD, target.getX(), target.getY() + .25f, target.getZ(), 100, .03, .4, .03, .4, false);
            MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.BLOOD.get().getTargetingColor(), explosionRadius), target.getX(), target.getBoundingBox().getCenter().y, target.getZ(), 1, 0, 0, 0, 0, true);
            var entities = level.getEntities(null, target.getBoundingBox().inflate(explosionRadius));
            for (Entity victim : entities) {
                double distanceSqr = victim.distanceToSqr(target.position());
                if (victim != entity && victim.canBeHitByProjectile() && distanceSqr < explosionRadius * explosionRadius && Utils.hasLineOfSight(level, target.getBoundingBox().getCenter(), victim.getBoundingBox().getCenter(), true)) {
                    float p = (float) (distanceSqr / (explosionRadius * explosionRadius));
                    p = 1 - p * p * p;
                    victim.hurt(new DamageSource(entity.level().damageSources().damageTypes.getHolder(DamageTypes.BLEEDING).get(), null, entity), damage * p);
                }
            }
            CameraShakeManager.addCameraShake(new CameraShakeData(level, 10, target.position(), 10, 10));
            target.removeEffect(MobEffectRegistry.BLEED);
            level.playSound(null, target.blockPosition(), SoundRegistry.BLOOD_EXPLOSION.get(), SoundSource.PLAYERS, 3, Utils.random.nextIntBetweenInclusive(8, 12) * .1f);

        }
    }

    public double getDamagePerStack(LivingEntity caster) {
        return caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5;
    }
}

