package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.conditions.HealthCheck;
import com.example.cqsarmory.data.enchants.LightningAspect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;


import java.util.function.Supplier;

public class LootItemConditionRegistry {
    private static final DeferredRegister<LootItemConditionType> CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        CONDITION_TYPES.register(eventBus);
    }

    public static  final Supplier<LootItemConditionType> HEALTH_CHECK_CONDITION =
            CONDITION_TYPES.register("health_check", ()-> new LootItemConditionType(HealthCheck.CODEC));
}
