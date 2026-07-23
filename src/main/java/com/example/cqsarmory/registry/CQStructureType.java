package com.example.cqsarmory.registry;

import com.example.cqsarmory.data.structure.UndergroundNoCarveStructure;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CQStructureType {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, "cqs_armory");

    public static final DeferredHolder<StructureType<?>, StructureType<UndergroundNoCarveStructure>> UNDERGROUND_NO_CARVE =
            STRUCTURE_TYPES.register("underground_no_carve", () -> () -> UndergroundNoCarveStructure.CODEC);
}
