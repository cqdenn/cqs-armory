package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import io.redspace.ironsspellbooks.entity.spells.summoned_weapons.SummonedClaymoreEntity;
import io.redspace.ironsspellbooks.entity.spells.summoned_weapons.SummonedRapierEntity;
import io.redspace.ironsspellbooks.entity.spells.summoned_weapons.SummonedSwordEntity;
import io.redspace.ironsspellbooks.entity.spells.summoned_weapons.SummonedWeaponEntity;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ArcaneSwordsMageAOEEffect extends NonCurableEffect {
    public ArcaneSwordsMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level lev = livingEntity.level();
        if (lev instanceof ServerLevel level) {
            Entity caster = livingEntity;
            //values extracted from level 4 summon swords spell getSpellPower()
            float power = (float) ((1 + 2 * 3) * livingEntity.getAttributeValue(AttributeRegistry.SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.ENDER_SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.ELDRITCH_SPELL_POWER));
            SummonedEntitiesCastData summonedEntitiesCastData = new SummonedEntitiesCastData();
            AttributeModifier healthModifier = new AttributeModifier(
                    IronsSpellbooks.id("spell_power_health_bonus"), getHealthBonus(power), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            AttributeModifier damageModifier = new AttributeModifier(
                    IronsSpellbooks.id("spell_power_damage_bonus"), getDamageBonus(power), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

            SummonedWeaponEntity claymore = new SummonedClaymoreEntity(EntityRegistry.SUMMONED_CLAYMORE.get(), level);
            SummonedWeaponEntity rapier = new SummonedRapierEntity(EntityRegistry.SUMMONED_RAPIER.get(), level);
            SummonedWeaponEntity sword = new SummonedSwordEntity(EntityRegistry.SUMMONED_SWORD.get(), level);

            Vec3 spawnBase = livingEntity.position().add(0, 1.2, 0);
            for (SummonedWeaponEntity weapon : List.of(claymore, rapier, sword)) {
                weapon.moveTo(spawnBase.add(Utils.getRandomVec3(1)));
                weapon.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(damageModifier);
                weapon.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthModifier);
                weapon.setHealth(weapon.getMaxHealth());
                Entity creature = weapon;
                level.addFreshEntity(creature);
                SummonManager.initSummon(caster, creature, CQtils.getAOETimeSeconds(livingEntity) * 20, summonedEntitiesCastData);
            }
        }

        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration == 1;
    }

    private static double getHealthBonus(float spellPower) {
        return (spellPower - 1) * 0.10;
    }

    private static double getDamageBonus(float spellPower) {
        return (spellPower - 1) * 0.05;
    }
}
