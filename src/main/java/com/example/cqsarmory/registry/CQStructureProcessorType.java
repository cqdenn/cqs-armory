package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.structure.AirOnlyProcessor;
import com.example.cqsarmory.data.structure.ComplexFoundationProcessor;
import com.mojang.serialization.MapCodec;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.worldgen.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CQStructureProcessorType {

    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        PROCESSORS.register(eventBus);
    }

    public static final Supplier<StructureProcessorType<AirOnlyProcessor>> AIR_ONLY = PROCESSORS.register("air_only", () -> () -> AirOnlyProcessor.CODEC);
    public static final Supplier<StructureProcessorType<ComplexFoundationProcessor>> COMPLEX_FOUNDATION = PROCESSORS.register("complex_foundation", () -> () -> ComplexFoundationProcessor.CODEC);
}

