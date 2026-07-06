package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.features.UndergroundTemplateFeature;
import com.example.cqsarmory.data.features.UndergroundTemplateFeatureConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CQFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, CqsArmory.MODID);

    public static final Supplier<Feature<UndergroundTemplateFeatureConfiguration>> UNDERGROUND_TEMPLATE =
            FEATURES.register("underground_template", () -> new UndergroundTemplateFeature(UndergroundTemplateFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
