package com.example.cqsarmory.data.biomes;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.BiomesRegistry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CQBiomeRegister
{
    public static final RegistrySetBuilder BIOME_BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, CQBiomeRegister::bootstrapBiomes);

    private static void bootstrapBiomes(BootstrapContext<Biome> context)
    {
        context.register(BiomesRegistry.DWARVEN_CAVES, CQOverworldBiomes.dwarvenCaves(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
    }
}
