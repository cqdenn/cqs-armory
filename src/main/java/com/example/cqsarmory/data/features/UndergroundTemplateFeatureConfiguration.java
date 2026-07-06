package com.example.cqsarmory.data.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public record UndergroundTemplateFeatureConfiguration(
        List<ResourceLocation> templates,
        int maxScanDistance
) implements FeatureConfiguration {
    public static final Codec<UndergroundTemplateFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.listOf().fieldOf("templates").forGetter(UndergroundTemplateFeatureConfiguration::templates),
            Codec.intRange(1, 384).optionalFieldOf("max_scan_distance", 96).forGetter(UndergroundTemplateFeatureConfiguration::maxScanDistance)
    ).apply(instance, UndergroundTemplateFeatureConfiguration::new));
}
