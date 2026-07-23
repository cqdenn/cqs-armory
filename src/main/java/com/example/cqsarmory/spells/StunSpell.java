package com.example.cqsarmory.spells;

import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;
import java.util.Optional;

public class StunSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(30)
            .build();

    public StunSpell() {
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
        return CastType.LONG;
    }

    @Override
    public Optional<PlayableSound> getCastStartSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundRegistry.DIVINE_SMITE_WINDUP.get()));
    }

    @Override
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.empty();
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.OVERHEAD_MELEE_SWING_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", 100),
                Component.translatable("ui.cqs_armory.stun_duration", getDurationTicks(castContext.getSkillLevel())/20 + "s")
        );
    }

    public double getDamage(Entity target, LivingEntity caster, ItemStack weaponItem) {
        float damage = (float) caster.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        var source = CQSpellRegistry.STUN_SPELL.get().getDamageSource(caster.level(), null, caster);
        if (caster.level() instanceof ServerLevel serverLevel && target != null) {
            return EnchantmentHelper.modifyDamage(serverLevel, weaponItem, target, source, damage);
        }
        return damage;
    }

    public int getDurationTicks (int spellLevel) {
        return 20 + (20 * spellLevel);
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.CASTING_MOVESPEED_MULTIPLIER, 1f);
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        var entities = level.getEntities(entity, entity.getBoundingBox().inflate(2));
        var damageSource = CQSpellRegistry.STUN_SPELL.get().getDamageSource(entity.level(), null, entity);
        ItemStack weaponItem = castContext.getCastSource().isFromSlot(EquipmentSlot.OFFHAND) ? entity.getOffhandItem() : entity.getMainHandItem();

        for (Entity target : entities) {
            if (target instanceof LivingEntity livingEntity) {
                float damage = (float) getDamage(target, entity, weaponItem);
                if (!DamageSources.isFriendlyFireBetween(entity, target) && !livingEntity.isSpectator() && Utils.hasLineOfSight(level, entity.position(), livingEntity.getBoundingBox().getCenter(), true)) {
                    if (target.hurt(damageSource, damage)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.STUNNED, getDurationTicks(castContext.getSkillLevel()), 100, false, false, true));
                    }
                }
            }
        }

    }
}
