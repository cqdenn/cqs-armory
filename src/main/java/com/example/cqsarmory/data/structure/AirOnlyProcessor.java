package com.example.cqsarmory.data.structure;

import com.example.cqsarmory.registry.CQStructureProcessorType;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class AirOnlyProcessor extends StructureProcessor {
    public static final MapCodec<AirOnlyProcessor> CODEC = MapCodec.unit(AirOnlyProcessor::new);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(
            LevelReader level, BlockPos offset, BlockPos pos,
            StructureTemplate.StructureBlockInfo blockInfo,
            StructureTemplate.StructureBlockInfo relativeBlockInfo,
            StructurePlaceSettings settings) {

        BlockState worldState = level.getBlockState(relativeBlockInfo.pos());
        if (!worldState.isAir()) {
            // world block is solid — skip this piece's block entirely, leave world untouched
            return null;
        }
        return relativeBlockInfo; // world was air, proceed with normal placement
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return CQStructureProcessorType.AIR_ONLY.get();
    }
}
