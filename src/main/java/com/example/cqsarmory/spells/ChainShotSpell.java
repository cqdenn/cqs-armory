package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.entity.ability.ChainArrow;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class ChainShotSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.ARCHER_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(30)
            .build();

    public ChainShotSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 20;
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
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.BOW_CHARGE_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.stop();
    }

    @Override
    public Optional<PlayableSound> getCastStartSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundRegistry.ARROW_VOLLEY_PREPARE.get()));
    }

    @Override
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.empty();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", getWeaponDamagePercent(castContext.getSkillLevel()) * 100),
                Component.translatable("ui.cqs_armory.chained_duration", getDuration(castContext)/20 + "s")
        );
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.CAST_TIME, CQtils.getEffectiveBowCastTime(castContext));
        castContext.set(SkillcastingComponentTypes.EFFECT_DURATION_TICKS, 20 + 20 * castContext.getSkillLevel());
    }

    public float getWeaponDamagePercent(int spellLevel) {
        return 1;
    }

    public static int getDuration(CastContext castContext) {
        return castContext.getOrDefault(SkillcastingComponentTypes.EFFECT_DURATION_TICKS, 0);
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        ChainArrow magicArrow = new ChainArrow(level, getDuration(castContext));
        Entity entity = castContext.asEntityCaster();
        magicArrow.setOwner(entity);
        magicArrow.setScale(2);
        magicArrow.setPos(entity.position().add(0, entity.getEyeHeight() - magicArrow.getBoundingBox().getYsize() * .5f, 0).add(entity.getForward()));
        magicArrow.setDeltaMovement(entity.getLookAngle().scale(2.5));
        Vec3 vec3 = magicArrow.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        magicArrow.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
        magicArrow.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
        magicArrow.yRotO = magicArrow.getYRot();
        magicArrow.xRotO = magicArrow.getXRot();
        magicArrow.setBaseDamage(getArrowDamage(castContext));
        level.addFreshEntity(magicArrow);
    }

    public float getArrowDamage(CastContext castContext) {
        if (castContext.asEntityCaster() instanceof LivingEntity living) {
            return (float) living.getAttributeValue(BowAttributes.ARROW_DAMAGE) * getWeaponDamagePercent(castContext.getSkillLevel());
        }
        return getSpellPower(castContext);
    }

}
