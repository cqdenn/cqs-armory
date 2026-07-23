package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.effects.SkewerEffect;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.registry.SoundRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class SkewerSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(20)
            .build();

    public SkewerSpell() {
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
        return Optional.of(PlayableSound.standard(SoundRegistry.SPEAR_SKEWER_SOUND.get()));
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    public float getWeaponDamagePercent() {
        return SkewerEffect.SKEWER_ATTACK_DAMAGE_MULTIPLIER * 100;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", (int) getWeaponDamagePercent()),
                Component.translatable("ui.cqs_armory.bleed_duration", getBleedDurationTicks(castContext.getSkillLevel())/20 + "s")
        );
    }

    public static int getBleedDurationTicks (int spellLevel) {
        return 40 * spellLevel;
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        Vec3 forward = entity.getLookAngle().scale(2 + (0.5f * castContext.getSkillLevel()));
        float y = !entity.onGround() ? 0.1f : 0.5f;

        entity.setDeltaMovement(forward.x, y, forward.z);
        entity.hurtMarked = true;
        entity.addEffect(new MobEffectInstance(MobEffectRegistry.SKEWER, 20, castContext.getSkillLevel(), false, false, false));
        entity.invulnerableTime = 20;
    }
}
