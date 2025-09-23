package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.effects.ShieldBashEffect;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class ShieldBashSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "shield_bash_spell");

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(20)
            .build();

    public ShieldBashSpell() {
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
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    public float getWeaponDamagePercent() {
        return ShieldBashEffect.SHIELD_BASH_ATTACK_DAMAGE_MULTIPLIER * 100;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", getWeaponDamagePercent()),
                Component.translatable("ui.cqs_armory.stun_duration", (1 * spellLevel) + "s")
        );
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        if (entity.getOffhandItem().getItem() instanceof ShieldItem) {
            return true;
        } else if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.cqs_armory.shield_bash_target_failure").withStyle(ChatFormatting.RED)));
        }
        return false;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);

        Vec3 direction = new Vec3(entity.getForward().x * spellLevel, 0.001, entity.getForward().z * spellLevel);
        var entities = level.getEntities(entity, entity.getBoundingBox().inflate(4));

        if (entity.onGround()) {
            entity.push(direction);
        } else {
            entity.push(direction.multiply(0.3, 0, 0.3));
        }
        entity.hurtMarked = true;

        if (!entities.isEmpty()) {
            entity.addEffect(new MobEffectInstance(MobEffectRegistry.SHIELD_BASH, 10, spellLevel - 1, false, false, false));
            entity.invulnerableTime = 20;
        }
    }
}
