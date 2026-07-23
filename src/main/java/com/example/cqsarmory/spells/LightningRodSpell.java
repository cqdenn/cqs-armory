package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.entity.ability.LightningRodEntity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.cast.CastType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class LightningRodSpell extends AbstractSpell {
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(20)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.literal("Splits Damage Between Targets"),
                Component.literal(getLifetime()/20 + " Second Lifetime"),
                Component.translatable("ui.irons_spellbooks.radius", getRadius()));
    }

    public LightningRodSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
        this.castTime = 20;
        this.baseManaCost = 0;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
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

    /*@Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        double entityCastTimeModifier = 1;
        if (entity != null && entity.getAttributeValue(BowAttributes.DRAW_SPEED) > 0) {
            entityCastTimeModifier = entity.getAttributeValue(BowAttributes.DRAW_SPEED);
        }
        return Math.round(this.getCastTime(spellLevel) / (float) entityCastTimeModifier);
    }*/

    public int getLifetime() {
        return 400;
    }

    public int getRadius() {
        return 5;
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        Entity test = castContext.asEntityCaster();
        if (!(test instanceof LivingEntity entity)) return;
        LightningRodEntity rod = new LightningRodEntity(level, entity, getLifetime(), getRadius());
        rod.setDeltaMovement(entity.getForward().scale(0.75));
        rod.setNoGravity(false);
        rod.noPhysics = false;
        rod.setPos(entity.getEyePosition());
        level.addFreshEntity(rod);
    }

}
