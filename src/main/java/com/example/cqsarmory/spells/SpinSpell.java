package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.api.AbilityAnimations;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class SpinSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "spin_spell");

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(8)
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
        return AnimationHolder.none();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", 100 + (50 * spellLevel)),
                Component.literal("25% damage reduction")
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
        var entities = level.getEntities(entity, entity.getBoundingBox().inflate(2));
        var damageSource = CQSpellRegistry.SPIN_SPELL.get().getDamageSource(entity);
        float playerAttackDamage = (float) entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        float dmg = playerAttackDamage + ((playerAttackDamage / 2) * spellLevel);
        for (Entity target : entities) {
            if (!DamageSources.isFriendlyFireBetween(entity, target) && !entity.isSpectator() && Utils.hasLineOfSight(target.level(), entity.position(), target.getBoundingBox().getCenter(), true)) {
                if (DamageSources.applyDamage(target, dmg, damageSource)) {
                    EnchantmentHelper.doPostAttackEffects((ServerLevel) level, target, damageSource);
                }
            }
        }
        entity.addEffect(new MobEffectInstance(MobEffectRegistry.SPIN, 11, 0, false, false, false));
    }
}
