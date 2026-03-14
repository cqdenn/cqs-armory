package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.network.SyncMomentumPacket;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class FocusSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "focus_spell");
    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.ARCHER_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(20)
            .build();

    public FocusSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 16;
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
        return Optional.of(SoundRegistry.FORCE_IMPACT.get());
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                //Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(100, 1)),
                Component.literal("Next Arrow Gains +1% Damage Per Momentum"),
                Component.literal("Consumes all Momentum")
        );
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        if (AbilityData.get(entity).getMomentum() > 0) return true;
        if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.cqs_armory.no_momentum_error").withStyle(ChatFormatting.RED)));
        }
        return false;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        AbilityData.get(entity).focusArrowDamage = 1 + AbilityData.get(entity).getMomentum() * 0.01f;
        float newMomentum = (float) entity.getAttributeValue(AttributeRegistry.MIN_MOMENTUM);
        AbilityData.get(entity).setMomentum(newMomentum);
        if (entity instanceof ServerPlayer serverPlayer) PacketDistributor.sendToPlayer(serverPlayer, new SyncMomentumPacket((int) newMomentum));
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
}
