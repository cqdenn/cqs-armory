package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.effects.ShieldBashEffect;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class ShieldBashSpell extends AbstractSpell {

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
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
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
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", getWeaponDamagePercent()),
                Component.translatable("ui.cqs_armory.stun_duration", (castContext.getSkillLevel()) + "s")
        );
    }

    @Override
    public boolean checkPreCastConditions(CastContext castContext) {
        if (castContext.asEntityCaster() instanceof LivingEntity entity) {
            if (entity.getOffhandItem().getItem() instanceof ShieldItem) {
                return true;
            } else if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.cqs_armory.shield_bash_target_failure").withStyle(ChatFormatting.RED)));
            }
        }
        return false;
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        Vec3 direction = new Vec3(entity.getForward().x * castContext.getSkillLevel(), 0.001, entity.getForward().z * castContext.getSkillLevel());
        var entities = level.getEntities(entity, entity.getBoundingBox().inflate(4));

        if (entity.onGround()) {
            entity.push(direction);
        } else {
            entity.push(direction.multiply(0.3, 0, 0.3));
        }
        entity.hurtMarked = true;

        if (!entities.isEmpty()) {
            entity.addEffect(new MobEffectInstance(MobEffectRegistry.SHIELD_BASH, 10, castContext.getSkillLevel() - 1, false, false, false));
            entity.invulnerableTime = 20;
        }
    }
}
