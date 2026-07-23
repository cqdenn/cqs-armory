package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.entity.ability.IceArrow;
import com.example.cqsarmory.registry.SoundRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class IceArrowSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(15)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getArrowDamage(castContext), 1)));
                //Component.translatable("ui.irons_spellbooks.aoe_damage", Utils.stringTruncation(getAOEDamage(spellLevel, caster), 1)));
    }

    public IceArrowSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
        this.castTime = 20;
        this.baseManaCost = 40;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
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
    public Optional<PlayableSound> getCastStartSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundEvents.CROSSBOW_LOADING_MIDDLE.value()));
    }

    @Override
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundRegistry.ARROW_SHOOT_CQ.get()));
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.CAST_TIME, CQtils.getEffectiveBowCastTime(castContext));
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        IceArrow magicArrow = new IceArrow(level);
        Entity entity = castContext.asEntityCaster();
        magicArrow.setOwner(entity);
        magicArrow.setSpellArrow(true);
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
        return getSpellPower(castContext);
    }

    /*public float getAOEDamage(int spellLevel, LivingEntity caster) {return getSpellPower(spellLevel, caster) * .185f;}*/

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.BOW_CHARGE_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.stop();
    }

}
