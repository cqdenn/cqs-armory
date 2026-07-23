package com.example.cqsarmory.spells;

import com.example.cqsarmory.registry.CQSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FlankStepSpell extends AbstractSpell {

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
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundRegistry.BLOOD_STEP.get()));
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.literal("Teleports you behind your target"),
                Component.translatable("ui.irons_spellbooks.distance", getRange())
        );
    }

    public int getRange() {
        return 20;
    }

    @Override
    public boolean checkPreCastConditions(CastContext castContext) {
        var target = RaycastBuilder.fromCast(castContext, PositionAnchor.CASTING_POSITION, getRange()).checkForBlocks(true).bbInflation(0.1f).build();
        Entity entity = castContext.asEntityCaster();
        if (target instanceof EntityHitResult entityHitResult && !entity.level().isClientSide) {
            if (entityHitResult.getEntity() instanceof LivingEntity living) {
                if (entity instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.spell_target_success", living.getDisplayName().getString(), this.getDisplayName(serverPlayer)).withStyle(ChatFormatting.GREEN)));
                }
                castContext.set(SkillcastingComponentTypes.TARGETED_ENTITIES, new TargetedEntitiesData(living));
                return true;
            }
        }
        if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.cast_error_target").withStyle(ChatFormatting.RED)));
        }
        return false;
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        LivingEntity target = SkillcastingUtils.getTargetedLivingEntity(level, castContext);
        if (target != null) {
            Entity entity = castContext.asEntityCaster();
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
