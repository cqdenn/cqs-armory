package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.effects.GenericEffect;
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

    public static final DeferredHolder<MobEffect, MobEffect> SPEED_STEAL = MOB_EFFECT_DEFERRED_REGISTER.register("speed_steal", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0xc1e0dc).addAttributeModifier(Attributes.MOVEMENT_SPEED, CqsArmory.id("mobeffect_speed_steal"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> SPEED_STOLEN = MOB_EFFECT_DEFERRED_REGISTER.register("speed_stolen", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0x313635).addAttributeModifier(Attributes.MOVEMENT_SPEED, CqsArmory.id("mobeffect_speed_stolen"), -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> STUNNED = MOB_EFFECT_DEFERRED_REGISTER.register("stunned", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0xc5d15a).addAttributeModifier(Attributes.MOVEMENT_SPEED, CqsArmory.id("mobeffect_stunned"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.ATTACK_SPEED, CqsArmory.id("mobeffect_stunned"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> ABSORBING_RUPTURE = MOB_EFFECT_DEFERRED_REGISTER.register("absorbing_rupture", () -> new GenericEffect(MobEffectCategory.BENEFICIAL, 0xc5d15a).addAttributeModifier(Attributes.MAX_ABSORPTION, CqsArmory.id("mobeffect_absorbing_rupture"), 2, AttributeModifier.Operation.ADD_VALUE));

}
