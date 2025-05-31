package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.enchants.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EnchantmentEntityEffectRegistry {
    private static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENCHANTMENT_ENTITY_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        ENCHANTMENT_ENTITY_EFFECT_DEFERRED_REGISTER.register(eventBus);
    }

    public static  final  Supplier<MapCodec<? extends EnchantmentEntityEffect>> CHAIN_LIGHTNING_ENCHANTMENT_EFFECT =
            ENCHANTMENT_ENTITY_EFFECT_DEFERRED_REGISTER.register("chain_lightning", ()-> LightningAspect.CODEC);

    public static  final  Supplier<MapCodec<? extends EnchantmentEntityEffect>> FROST_ENCHANTMENT_EFFECT =
            ENCHANTMENT_ENTITY_EFFECT_DEFERRED_REGISTER.register("frost", ()-> FrostAspect.CODEC);

    public static  final  Supplier<MapCodec<? extends EnchantmentEntityEffect>> POISON_ENCHANTMENT_EFFECT =
            ENCHANTMENT_ENTITY_EFFECT_DEFERRED_REGISTER.register("poison", ()-> PoisonAspect.CODEC);

    public static  final  Supplier<MapCodec<? extends EnchantmentEntityEffect>> LIFE_STEAL_ENCHANTMENT_EFFECT =
            ENCHANTMENT_ENTITY_EFFECT_DEFERRED_REGISTER.register("life_steal", ()-> LifeSteal.CODEC);

    public static  final  Supplier<MapCodec<? extends EnchantmentEntityEffect>> MANA_STEAL_ENCHANTMENT_EFFECT =
            ENCHANTMENT_ENTITY_EFFECT_DEFERRED_REGISTER.register("mana_steal", ()-> ManaSteal.CODEC);
}
