package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomesRegistry {

    public static final ResourceKey<Biome> DWARVEN_CAVES = register("dwarven_caves");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, name));
    }
}
