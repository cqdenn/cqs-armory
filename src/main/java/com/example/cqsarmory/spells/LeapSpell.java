package com.example.cqsarmory.spells;

import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class LeapSpell extends AbstractSpell {

    public static final float WEAPON_DAMAGE_PERCENT = 0.5f;
    public static final int DURATION = 30;
    public static final int RADIUS = 3;
    public static final int RAGE_PER_HIT = 5;

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(10)
            .build();

    public LeapSpell() {
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

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", getWeaponDamagePercent() * 100),
                Component.translatable("ui.irons_spellbooks.radius", getRadius()),
                Component.literal(String.format("+%s Rage per Hit", getRagePerEnemy(castContext.getSkillLevel())))
        );
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.CASTING_MOVESPEED_MULTIPLIER, 1f);
    }

    public double getDamage(Entity target, LivingEntity caster, ItemStack weaponItem) {
        float damage = (float) caster.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * getWeaponDamagePercent();
        var source = CQSpellRegistry.LEAP_SPELL.get().getDamageSource(caster.level(), null, caster);
        if (caster.level() instanceof ServerLevel serverLevel && target != null) {
            return EnchantmentHelper.modifyDamage(serverLevel, weaponItem, target, source, damage);
        }
        return damage;
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        int spellLevel = castContext.getSkillLevel();
        Vec3 forward = entity.getLookAngle().scale(2);
        float y = 1;

        entity.setDeltaMovement(forward.x, y, forward.z);
        entity.hurtMarked = true;
        entity.addEffect(new MobEffectInstance(MobEffectRegistry.LEAP, getDuration(), spellLevel, false, false, false));
        entity.invulnerableTime = 20;
    }

    public int getRadius() {
        return RADIUS;
    }

    public int getDuration() {
        return DURATION;
    }

    public int getRagePerEnemy (int spellLevel) {
        return spellLevel * RAGE_PER_HIT;
    }

    public float getWeaponDamagePercent () {
        return WEAPON_DAMAGE_PERCENT;
    }
}
