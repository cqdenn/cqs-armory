package com.example.cqsarmory.data.features;

import com.example.cqsarmory.data.structure.AirOnlyProcessor;
import com.example.cqsarmory.registry.BiomesRegistry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import io.redspace.ironsspellbooks.worldgen.StructureFoundationProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.HashSet;
import java.util.Set;

public class UndergroundTemplateFeature extends Feature<UndergroundTemplateFeatureConfiguration> {
    private static final int PLACE_FLAGS = 4;
    private static final int MAX_CEILING_DISTANCE = 96;
    private static final int MAX_FOUNDATION_DISTANCE = 2;

    public UndergroundTemplateFeature(Codec<UndergroundTemplateFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<UndergroundTemplateFeatureConfiguration> context) {
        UndergroundTemplateFeatureConfiguration config = context.config();
        if (config.templates().isEmpty()) {
            return false;
        }

        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        ResourceLocation templateId = config.templates().get(random.nextInt(config.templates().size()));
        StructureTemplateManager templateManager = level.getLevel().getServer().getStructureManager();
        StructureTemplate template = templateManager.getOrCreate(templateId);
        Rotation rotation = Rotation.getRandom(random);
        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setMirror(Mirror.NONE)
                .setRotation(rotation)
                .setRandom(random)
                .setIgnoreEntities(false)
                .addProcessor(createFoundationProcessor())
                .addProcessor(new AirOnlyProcessor());

        BlockPos floorPos = findFloor(level, context.origin(), config.maxScanDistance());
        if (floorPos == null) {
            return false;
        }

        BlockPos cornerPos = floorPos.offset(-template.getSize(rotation).getX() / 2, 0, -template.getSize(rotation).getZ() / 2);
        BlockPos placePos = template.getZeroPositionWithTransform(cornerPos, Mirror.NONE, rotation);
        if (!canPlace(level, placePos, template, settings)) {
            return false;
        }

        return template.placeInWorld(level, placePos, placePos, settings, random, PLACE_FLAGS);
    }

    private static BlockPos findFloor(WorldGenLevel level, BlockPos origin, int maxScanDistance) {
        int minY = Math.max(level.getMinBuildHeight() + 1, origin.getY() - maxScanDistance);
        int maxY = Math.min(level.getMaxBuildHeight() - 1, origin.getY() + maxScanDistance);
        BlockPos.MutableBlockPos mutable = origin.mutable();

        int originY = Mth.clamp(origin.getY(), minY, maxY);
        int maxOffset = Math.max(maxY - originY, originY - minY);

        for (int offset = 0; offset <= maxOffset; offset++) {
            int upY = originY + offset;
            if (upY <= maxY) {
                mutable.setY(upY);
                if (isValidFloor(level, mutable)) {
                    return mutable.immutable();
                }
            }

            int downY = originY - offset;
            if (offset != 0 && downY >= minY) {
                mutable.setY(downY);
                if (isValidFloor(level, mutable)) {
                    return mutable.immutable();
                }
            }
        }

        return null;
    }

    private static boolean isValidFloor(WorldGenLevel level, BlockPos pos) {
        return level.isEmptyBlock(pos)
                && level.getBiome(pos).is(BiomesRegistry.DWARVEN_CAVES)
                && hasGroundWithin(level, pos.below(), MAX_FOUNDATION_DISTANCE)
                && hasSturdyCeiling(level, pos);
    }

    private static boolean canPlace(WorldGenLevel level, BlockPos placePos, StructureTemplate template, StructurePlaceSettings settings) {
        Set<BlockPos> supportPositions = new HashSet<>();
        int lowestSolidY = Integer.MAX_VALUE;
        CompoundTag templateTag = template.save(new CompoundTag());
        ListTag palette = templateTag.getList("palette", Tag.TAG_COMPOUND);
        ListTag blocks = templateTag.getList("blocks", Tag.TAG_COMPOUND);

        for (int i = 0; i < blocks.size(); i++) {
            CompoundTag blockTag = blocks.getCompound(i);
            CompoundTag stateTag = palette.getCompound(blockTag.getInt("state"));
            ResourceLocation blockId = ResourceLocation.tryParse(stateTag.getString("Name"));
            if (blockId == null || blockId.equals(ResourceLocation.withDefaultNamespace("air"))
                    || blockId.equals(ResourceLocation.withDefaultNamespace("structure_void"))) {
                continue;
            }

            ListTag posTag = blockTag.getList("pos", Tag.TAG_INT);
            BlockPos localPos = new BlockPos(posTag.getInt(0), posTag.getInt(1), posTag.getInt(2));
            BlockPos worldPos = StructureTemplate.calculateRelativePosition(settings, localPos).offset(placePos);
            if (!level.isEmptyBlock(worldPos)) {
                return false;
            }

            if (worldPos.getY() < lowestSolidY) {
                lowestSolidY = worldPos.getY();
                supportPositions.clear();
            }
            if (worldPos.getY() == lowestSolidY) {
                supportPositions.add(worldPos.below());
            }
        }

        if (supportPositions.isEmpty()) {
            return false;
        }

        for (BlockPos supportPos : supportPositions) {
            if (!hasGroundWithin(level, supportPos, MAX_FOUNDATION_DISTANCE)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isGround(WorldGenLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isFaceSturdy(level, pos, Direction.UP);
    }

    private static boolean hasGroundWithin(WorldGenLevel level, BlockPos pos, int maxDistance) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int i = 0; i <= maxDistance && mutable.getY() >= level.getMinBuildHeight(); i++) {
            if (isGround(level, mutable)) {
                return true;
            }
            mutable.move(Direction.DOWN);
        }

        return false;
    }

    private static boolean hasSturdyCeiling(WorldGenLevel level, BlockPos floorPos) {
        if (level.canSeeSky(floorPos)) {
            return false;
        }

        int maxY = Math.min(level.getMaxBuildHeight() - 1, floorPos.getY() + MAX_CEILING_DISTANCE);
        BlockPos.MutableBlockPos mutable = floorPos.mutable();
        for (int y = floorPos.getY() + 1; y <= maxY; y++) {
            mutable.setY(y);
            BlockState state = level.getBlockState(mutable);
            if (state.isFaceSturdy(level, mutable, Direction.DOWN)) {
                return true;
            }
        }

        return false;
    }

    private static StructureProcessor createFoundationProcessor() {
        JsonObject block = new JsonObject();
        block.add("Name", new JsonPrimitive("minecraft:tuff_bricks"));
        JsonObject config = new JsonObject();
        config.add("block", block);
        return StructureFoundationProcessor.CODEC.codec()
                .parse(JsonOps.INSTANCE, config)
                .getOrThrow(JsonParseException::new);
    }
}
