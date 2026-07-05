package com.example.cqsarmory.data.structure;

import com.example.cqsarmory.registry.CQStructureProcessorType;
import com.mojang.serialization.MapCodec;
import io.redspace.ironsspellbooks.registries.StructureProcessorRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

public class ComplexFoundationProcessor extends StructureProcessor {
    public static final MapCodec<ComplexFoundationProcessor> CODEC = MapCodec.unit(ComplexFoundationProcessor::new);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(@NotNull LevelReader levelReader,
                                                             @NotNull BlockPos jigsawPiecePos,
                                                             @NotNull BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.@NotNull StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        // check if the current block is at local y = 0 (meaning we are at the min height of the structure piece)
        // and it is not air (and thus we wouldnt need foundation here)
        // and some other check from yungnickyung
        if (blockInfoLocal.pos().getY() == 0 && !blockInfoGlobal.state().is(Blocks.AIR)
                && !(levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.getCenter().equals(new ChunkPos(blockInfoGlobal.pos())))
        ) {
            BlockState baseState = blockInfoGlobal.state();
            BlockPos.MutableBlockPos mutable = blockInfoGlobal.pos().mutable().move(Direction.DOWN);
            BlockState currentState = levelReader.getBlockState(mutable);

            // while we have not traversed outside the bounds of the world and we have not yet hit solid material, create foundation block
            while (mutable.getY() > levelReader.getMinBuildHeight()
                    && mutable.getY() < levelReader.getMaxBuildHeight()
                    && (currentState.isAir() || !levelReader.getFluidState(mutable).isEmpty())) {

                BlockState placedState = baseState;

                // walls (and similar connecting blocks) need their shape recomputed
                // against the actual neighbors at THIS position, not the original placement spot
                if (baseState.getBlock() instanceof WallBlock && levelReader instanceof LevelAccessor levelAccessor) {
                    for (Direction dir : Direction.Plane.HORIZONTAL) {
                        BlockPos neighborPos = mutable.relative(dir);
                        BlockState neighborState = levelReader.getBlockState(neighborPos);
                        placedState = placedState.updateShape(dir, neighborState, levelAccessor, mutable, neighborPos);
                    }
                }

                levelReader.getChunk(mutable).setBlockState(mutable, placedState, false);
                // iterate downward
                mutable.move(Direction.DOWN);
                currentState = levelReader.getBlockState(mutable);
            }
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return CQStructureProcessorType.COMPLEX_FOUNDATION.get();
    }
}