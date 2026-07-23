package com.example.cqsarmory.spells;

import com.example.cqsarmory.api.AbilityAnimations;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.cast.CastType;
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

public class SpinSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(20)
            .build();

    public SpinSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 100;
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
        return CastType.CONTINUOUS;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return AbilityAnimations.SPIN_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.stop();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", 100 + (25 * (castContext.getSkillLevel() - 1))),
                Component.literal("25% Damage Reduction")
        );
    }

    public double getDamage(Entity target, LivingEntity caster, ItemStack weaponItem, int spellLevel) {
        float playerAttackDamage = (float) caster.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        float damage = playerAttackDamage + ((playerAttackDamage / 4) * (spellLevel - 1));
        var source = CQSpellRegistry.SPIN_SPELL.get().getDamageSource(caster.level(), null, caster);
        if (caster.level() instanceof ServerLevel serverLevel && target != null) {
            return EnchantmentHelper.modifyDamage(serverLevel, weaponItem, target, source, damage);
        }
        return damage;
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        var entities = level.getEntities(entity, entity.getBoundingBox().inflate(2));
        var damageSource = CQSpellRegistry.SPIN_SPELL.get().getDamageSource(entity.level(), null, entity);
        ItemStack weaponItem = castContext.getCastSource().isFromSlot(EquipmentSlot.OFFHAND) ? entity.getOffhandItem() : entity.getMainHandItem();

        for (Entity target : entities) {
            if (!DamageSources.isFriendlyFireBetween(entity, target) && !entity.isSpectator() && Utils.hasLineOfSight(target.level(), entity.position(), target.getBoundingBox().getCenter(), true)) {
                float damage = (float) getDamage(target, entity, weaponItem, castContext.getSkillLevel());
                if (target.hurt(damageSource, damage)) {
                    EnchantmentHelper.doPostAttackEffects((ServerLevel) level, target, damageSource);
                }
            }
        }
        entity.addEffect(new MobEffectInstance(MobEffectRegistry.SPIN, 11, 0, false, false, false));
    }
}
