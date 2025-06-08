package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.enchants.RottingCurse;
import com.mojang.serialization.MapCodec;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.loot.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class LootModifierRegistry {
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, CqsArmory.MODID);
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        LOOT_FUNCTIONS.register(eventBus);
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ROTTING_CURSE_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("rotting_curse", RottingCurse.CODEC);
}