package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.data.effects.BleedEffect;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AutoSpellConfig
public class FlankStepSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "flank_step_spell");
    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(20)
            .build();

    public FlankStepSpell() {
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
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundRegistry.BLOOD_STEP.get());
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.literal("Teleports you behind your target"),
                Component.translatable("ui.irons_spellbooks.distance", getRange())
        );
    }

    public int getRange () {
        return 20;
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        var target = Utils.raycastForEntity(entity.level(), entity, getRange(), true, 0.1f);
        if (target instanceof EntityHitResult entityHitResult && !entity.level().isClientSide) {
            if (entityHitResult.getEntity() instanceof LivingEntity living) {
                if (entity instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.spell_target_success", living.getDisplayName().getString(), this.getDisplayName(serverPlayer)).withStyle(ChatFormatting.GREEN)));
                }
                playerMagicData.setAdditionalCastData(new TargetEntityCastData(living));
                return true;
            }
        }
        if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.cast_error_target").withStyle(ChatFormatting.RED)));
        }
        return false;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);

        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetingData) {
            var target = targetingData.getTarget((ServerLevel) level);
            if (target != null) {
                Vec3 pos = target.position().add(target.getForward().multiply(-1, 0, -1));
                double y = Utils.findRelativeGroundLevel(level, pos, 2) + 0.1;
                double diff = y - target.position().y;
                diff = diff < 0.2 && diff > -0.2 ? 0 : (diff > 0 ? 45 + (10 * diff) : -45 - (10 * diff));
                if (level instanceof ServerLevel serverLevel) {
                    entity.teleportTo(serverLevel, pos.x, y, pos.z, Set.of(), target.getYRot(), (float) diff);
                }
            }
        }
    }
}
