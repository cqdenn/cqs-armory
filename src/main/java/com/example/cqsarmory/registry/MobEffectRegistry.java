package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.effects.*;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MobEffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
    }

    public static final DeferredHolder<MobEffect, MobEffect> SPEED_STEAL = MOB_EFFECT_DEFERRED_REGISTER.register("speed_steal", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0xc1e0dc).addAttributeModifier(Attributes.MOVEMENT_SPEED, CqsArmory.id("mobeffect_speed_steal"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    public static final DeferredHolder<MobEffect, MobEffect> SPEED_STOLEN = MOB_EFFECT_DEFERRED_REGISTER.register("speed_stolen", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0x313635).addAttributeModifier(Attributes.MOVEMENT_SPEED, CqsArmory.id("mobeffect_speed_stolen"), -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    public static final DeferredHolder<MobEffect, MobEffect> STUNNED = MOB_EFFECT_DEFERRED_REGISTER.register("stunned", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0xc5d15a).addAttributeModifier(Attributes.MOVEMENT_SPEED, CqsArmory.id("mobeffect_stunned"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.ATTACK_SPEED, CqsArmory.id("mobeffect_stunned"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> ABSORBING_RUPTURE = MOB_EFFECT_DEFERRED_REGISTER.register("absorbing_rupture", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0xc5d15a).addAttributeModifier(Attributes.MAX_ABSORPTION, CqsArmory.id("mobeffect_absorbing_rupture"), 2, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> BERSERK = MOB_EFFECT_DEFERRED_REGISTER.register("berserk", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0xe60800).addAttributeModifier(Attributes.ATTACK_DAMAGE, CqsArmory.id("mobeffect_berserk"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE).addAttributeModifier(Attributes.ATTACK_SPEED, CqsArmory.id("mobeffect_berserk"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.MOVEMENT_SPEED, CqsArmory.id("mobeffect_berserk"), 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    public static final DeferredHolder<MobEffect, MobEffect> SKEWER = MOB_EFFECT_DEFERRED_REGISTER.register("skewer", () -> new SkewerEffect(MobEffectCategory.BENEFICIAL, 0x6b6969));
    //Bleed Note: Must use CQMobEffectInstance for the bleed effect to be affected by ConsumeBleedSpell
    public static final DeferredHolder<MobEffect, MobEffect> BLEED = MOB_EFFECT_DEFERRED_REGISTER.register("bleed", () -> new BleedEffect(MobEffectCategory.HARMFUL, 0x630606));
    public static final DeferredHolder<MobEffect, MobEffect> SKY_STRIKE = MOB_EFFECT_DEFERRED_REGISTER.register("sky_strike", () -> new SkyStrikeEffect(MobEffectCategory.BENEFICIAL, 0x99a2a8));
    public static final DeferredHolder<MobEffect, MobEffect> SHIELD_BASH = MOB_EFFECT_DEFERRED_REGISTER.register("shield_bash", () -> new ShieldBashEffect(MobEffectCategory.BENEFICIAL, 0x9882a8));
    public static final DeferredHolder<MobEffect, MobEffect> DODGE = MOB_EFFECT_DEFERRED_REGISTER.register("dodge", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0x9882a8).addAttributeModifier(AttributeRegistry.DODGE_CHANCE, CqsArmory.id("mobeffect_dodge"), 1, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> INSTA_DRAW = MOB_EFFECT_DEFERRED_REGISTER.register("insta_draw", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0x7772a8).addAttributeModifier(BowAttributes.DRAW_SPEED, CqsArmory.id("mobeffect_insta_draw"), 20, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> GENERIC_MAGE_AOE = MOB_EFFECT_DEFERRED_REGISTER.register("generic_mage_aoe", () -> new GenericMageAOEEffect(MobEffectCategory.BENEFICIAL, 0x7112a8));
    public static final DeferredHolder<MobEffect, MobEffect> HELLFIRE_MAGE_AOE = MOB_EFFECT_DEFERRED_REGISTER.register("hellfire_mage_aoe", () -> new HellfireMageAOEEffect(MobEffectCategory.BENEFICIAL, 0x7112a8));
    public static final DeferredHolder<MobEffect, MobEffect> GRAVITY_SNARE = MOB_EFFECT_DEFERRED_REGISTER.register("gravity_snare", () -> new GravitySnareEffect(MobEffectCategory.HARMFUL, 0xae34eb).addAttributeModifier(Attributes.GRAVITY, CqsArmory.id("mobeffect_gravity_snare"), -1.02, AttributeModifier.Operation.ADD_MULTIPLIED_BASE).addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, CqsArmory.id("mobeffect_gravity_snare"), 0.9, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> INFINITE_MAGIC = MOB_EFFECT_DEFERRED_REGISTER.register("infinite_magic", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0x1134eb));
    public static final DeferredHolder<MobEffect, MobEffect> SPIN = MOB_EFFECT_DEFERRED_REGISTER.register("spin", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0x1221eb).addAttributeModifier(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.CASTING_MOVESPEED, CqsArmory.id("mobeffect_spin_casting_movespeed"), 0.6, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    public static final DeferredHolder<MobEffect, MobEffect> AUTO_CRIT = MOB_EFFECT_DEFERRED_REGISTER.register("auto_crit", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0x1221af));
}
