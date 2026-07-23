package com.example.cqsarmory.spells;

import com.example.cqsarmory.api.AbilityAnimations;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
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

public class RuptureSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(30)
            .build();

    public RuptureSpell() {
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
        return AbilityAnimations.RUPTURE_ANIMATION;
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
                Component.translatable("ui.cqs_armory.weapon_damage", 75),
                Component.translatable("ui.irons_spellbooks.radius", getRadius(castContext.getSkillLevel())),
                Component.translatable("ui.cqs_armory.absorption_per_enemy", getAbsorptionPerEnemy(castContext.getSkillLevel()))
        );
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.CASTING_MOVESPEED_MULTIPLIER, 1f);
    }

    public double getDamage(Entity target, LivingEntity caster, ItemStack weaponItem) {
        float damage = (float) caster.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 0.75f;
        var source = CQSpellRegistry.RUPTURE_SPELL.get().getDamageSource(caster.level(), null, caster);
        if (caster.level() instanceof ServerLevel serverLevel && target != null) {
            return EnchantmentHelper.modifyDamage(serverLevel, weaponItem, target, source, damage);
        }
        return damage;
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        int spellLevel = castContext.getSkillLevel();
        int radius = getRadius(spellLevel);
        var entities = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        if (entities.contains(entity)) entities.remove(entity);
        ItemStack weaponItem = castContext.getCastSource().isFromSlot(EquipmentSlot.OFFHAND) ? entity.getOffhandItem() : entity.getMainHandItem();
        EarthquakeAoe aoeEntity = new EarthquakeAoe(level);
        aoeEntity.moveTo(entity.position());
        aoeEntity.setOwner(entity);
        aoeEntity.setCircular();
        aoeEntity.setRadius(radius);
        aoeEntity.setDuration(20);
        aoeEntity.setSlownessAmplifier(1);
        level.addFreshEntity(aoeEntity);
        entity.addEffect(new MobEffectInstance(MobEffectRegistry.ABSORBING_RUPTURE, 100 * spellLevel, (entities.size() * getAbsorptionPerEnemy(spellLevel)) - 1 + (int) entity.getAbsorptionAmount(), false, false, true));
        entity.setAbsorptionAmount(entity.getAbsorptionAmount() + (entities.size() * (getAbsorptionPerEnemy(spellLevel))));
        for (Entity target : entities) {
            if (target instanceof LivingEntity || target instanceof MomentumOrb) {
                if (!(target == entity)) {
                    float damage = (float) getDamage(target, entity, weaponItem);
                    target.hurt(CQSpellRegistry.RUPTURE_SPELL.get().getDamageSource(entity.level(), null, entity), damage);
                }
            }
        }

    }

    public int getRadius(int spellLevel) {
        return 6;
    }

    public int getAbsorptionPerEnemy (int spellLevel) {
        return spellLevel;
    }
}

