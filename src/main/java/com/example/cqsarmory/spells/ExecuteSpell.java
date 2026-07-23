package com.example.cqsarmory.spells;

import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ExecuteSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(30)
            .build();

    public ExecuteSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 10;
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
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ONE_HANDED_HORIZONTAL_SWING_ANIMATION;
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
                Component.literal("+" + getBonusDamagePercent(castContext.getSkillLevel()) + "% Damage Per Debuff on Target")
        );
    }

    public float getBonusDamage (int spellLevel) {
        return switch (spellLevel) {
            case 1 -> .25f;
            case 2 -> .50f;
            case 3 -> 1.00f;
            default -> 1.50f;
        };
    }

    public int getBonusDamagePercent (int spellLevel) {
        return (int) (getBonusDamage(spellLevel) * 100);
    }

    public double getDamage(LivingEntity target, LivingEntity caster, ItemStack weaponItem, int spellLevel) {
        float damage = (float) caster.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        int harmfulEffects = 0;
        for (MobEffectInstance effect : target.getActiveEffects()) {
            if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                harmfulEffects += 1;
            }
        }
        float bonusDamage = (getBonusDamage(spellLevel) * harmfulEffects) * damage;
        damage += bonusDamage;
        var source = CQSpellRegistry.STUN_SPELL.get().getDamageSource(caster.level(), null, caster);
        if (caster.level() instanceof ServerLevel serverLevel && target != null) {
            return EnchantmentHelper.modifyDamage(serverLevel, weaponItem, target, source, damage);
        }
        return damage;
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.CASTING_MOVESPEED_MULTIPLIER, 1f);
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        float radius = 3.25f;
        float distance = 1.9f;
        Entity test = castContext.asEntityCaster();
        if (!(test instanceof LivingEntity entity)) return;
        Vec3 forward = entity.getForward();
        Vec3 hitLocation = entity.position().add(0, entity.getBbHeight() * .3f, 0).add(forward.scale(distance));
        var entities = level.getEntities(entity, AABB.ofSize(hitLocation, radius * 2, radius, radius * 2));
        var damageSource = CQSpellRegistry.EXECUTE_SPELL.get().getDamageSource(level, null, entity);
        ItemStack weaponItem = castContext.getCastSource().isFromSlot(EquipmentSlot.OFFHAND) ? entity.getOffhandItem() : entity.getMainHandItem();
        for (Entity targetEntity : entities) {
            if (targetEntity instanceof LivingEntity living && targetEntity.isAlive() && entity.isPickable() && targetEntity.position().subtract(entity.getEyePosition()).dot(forward) >= 0 && entity.distanceToSqr(targetEntity) < radius * radius && Utils.hasLineOfSight(level, entity.getEyePosition(), targetEntity.getBoundingBox().getCenter(), true)) {
                Vec3 offsetVector = targetEntity.getBoundingBox().getCenter().subtract(entity.getEyePosition());
                if (offsetVector.dot(forward) >= 0) {
                    if (targetEntity.hurt(damageSource, (float) getDamage(living, entity, weaponItem, castContext.getSkillLevel()))) {
                        EnchantmentHelper.doPostAttackEffects((ServerLevel) level, targetEntity, damageSource);
                    }
                }
            }
        }
    }
}

