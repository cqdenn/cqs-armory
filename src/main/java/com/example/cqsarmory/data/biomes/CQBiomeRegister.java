package com.example.cqsarmory.data.biomes;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.features.UndergroundTemplateFeatureConfiguration;
import com.example.cqsarmory.registry.CQFeatureRegistry;
import com.example.cqsarmory.registry.BiomesRegistry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class CQBiomeRegister
{
    public static final ResourceKey<ConfiguredFeature<?, ?>> DWARVEN_CAVES_FEATURES_CONFIGURED = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            CqsArmory.id("dwarven_caves_features")
    );
    public static final ResourceKey<PlacedFeature> DWARVEN_CAVES_FEATURES_PLACED = ResourceKey.create(
            Registries.PLACED_FEATURE,
            CqsArmory.id("dwarven_caves_features")
    );

    public static final RegistrySetBuilder BIOME_BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, CQBiomeRegister::bootstrapConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, CQBiomeRegister::bootstrapPlacedFeatures)
            .add(Registries.BIOME, CQBiomeRegister::bootstrapBiomes);

    private static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context)
    {
        context.register(DWARVEN_CAVES_FEATURES_CONFIGURED, new ConfiguredFeature<>(
                CQFeatureRegistry.UNDERGROUND_TEMPLATE.get(),
                new UndergroundTemplateFeatureConfiguration(List.of(
                        CqsArmory.id("dwarven_caves/features/ore_coal"),
                        CqsArmory.id("dwarven_caves/features/ore_copper"),
                        //CqsArmory.id("dwarven_caves/features/ore_diamond"),
                        CqsArmory.id("dwarven_caves/features/ore_emerald"),
                        CqsArmory.id("dwarven_caves/features/ore_gold"),
                        CqsArmory.id("dwarven_caves/features/ore_iron"),
                        CqsArmory.id("dwarven_caves/features/ore_coal"),
                        CqsArmory.id("dwarven_caves/features/ore_copper"),
                        CqsArmory.id("dwarven_caves/features/ore_diamond"),
                        CqsArmory.id("dwarven_caves/features/ore_emerald"),
                        CqsArmory.id("dwarven_caves/features/ore_gold"),
                        CqsArmory.id("dwarven_caves/features/ore_iron"),

                        CqsArmory.id("dwarven_caves/features/chest"),
                        CqsArmory.id("dwarven_caves/features/point"),
                        CqsArmory.id("dwarven_caves/features/chest"),
                        CqsArmory.id("dwarven_caves/features/point"),

                        CqsArmory.id("dwarven_caves/features/statue")
                ), 96)
        ));
    }

    private static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context)
    {
        context.register(DWARVEN_CAVES_FEATURES_PLACED, new PlacedFeature(
                context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(DWARVEN_CAVES_FEATURES_CONFIGURED),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.absolute(0)),
                        BiomeFilter.biome()
                )
        ));
    }

    private static void bootstrapBiomes(BootstrapContext<Biome> context)
    {
        context.register(BiomesRegistry.DWARVEN_CAVES, CQOverworldBiomes.dwarvenCaves(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
    }
}
