package com.example.cqsarmory.data.biomes;

import com.example.cqsarmory.registry.BiomesRegistry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.List;
import java.util.function.Consumer;

public class CQRegions extends Region {
    public CQRegions(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {

            //builder.replaceBiome(Biomes.DRIPSTONE_CAVES, BiomesRegistry.DWARVEN_CAVES);

            List<Climate.ParameterPoint> dwarvenCaves = new ParameterUtils.ParameterPointListBuilder()
                    .temperature(FULL_RANGE)
                    .humidity(FULL_RANGE)
                    .continentalness(FULL_RANGE)
                    .erosion(FULL_RANGE)
                    .depth(ParameterUtils.Depth.FLOOR)
                    .weirdness(FULL_RANGE)
                    .build();
            //System.out.println("DWARVEN CAVES SIZE: " + dwarvenCaves.size());

            dwarvenCaves.forEach(point -> this.addBiome(mapper, point, BiomesRegistry.DWARVEN_CAVES));
        });
    }
}
