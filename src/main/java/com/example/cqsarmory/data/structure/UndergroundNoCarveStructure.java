package com.example.cqsarmory.data.structure;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.CQStructureType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class UndergroundNoCarveStructure extends Structure {
    public static final DimensionPadding DEFAULT_DIMENSION_PADDING = DimensionPadding.ZERO;
    public static final LiquidSettings DEFAULT_LIQUID_SETTINGS = LiquidSettings.APPLY_WATERLOGGING;
    private static final int SAMPLE_STEP = 3; // spacing between sample points, in blocks
    private static final int PADDING = 2; // blocks of buffer sampled around the piece, folded into the one check
    // Fraction of sampled points that must read as "air" for the spot to count as open.
    private static final double REQUIRED_OPEN_FRACTION = 0.75;
    private static final int MAX_ATTEMPTS = 6;
    public static final MapCodec<UndergroundNoCarveStructure> CODEC = RecordCodecBuilder.<UndergroundNoCarveStructure>mapCodec(
                    p_227640_ -> p_227640_.group(
                                    settingsCodec(p_227640_),
                                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(p_227656_ -> p_227656_.startPool),
                                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(p_227654_ -> p_227654_.startJigsawName),
                                    Codec.intRange(0, 20).fieldOf("size").forGetter(p_227652_ -> p_227652_.maxDepth),
                                    HeightProvider.CODEC.fieldOf("start_height").forGetter(p_227649_ -> p_227649_.startHeight),
                                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter(p_227646_ -> p_227646_.useExpansionHack),
                                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(p_227644_ -> p_227644_.projectStartToHeightmap),
                                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(p_227642_ -> p_227642_.maxDistanceFromCenter),
                                    Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(p_307187_ -> p_307187_.poolAliases),
                                    DimensionPadding.CODEC
                                            .optionalFieldOf("dimension_padding", DEFAULT_DIMENSION_PADDING)
                                            .forGetter(p_348455_ -> p_348455_.dimensionPadding),
                                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", DEFAULT_LIQUID_SETTINGS).forGetter(p_352036_ -> p_352036_.liquidSettings)
                            )
                            .apply(p_227640_, UndergroundNoCarveStructure::new)
            )
            .validate(UndergroundNoCarveStructure::verifyRange);
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final List<PoolAliasBinding> poolAliases;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;

    private static DataResult<UndergroundNoCarveStructure> verifyRange(UndergroundNoCarveStructure structure) {
        int i = switch (structure.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX, ENCAPSULATE -> 12;
        };
        return structure.maxDistanceFromCenter + i > 128
                ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128")
                : DataResult.success(structure);
    }

    public UndergroundNoCarveStructure(
            Structure.StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            Optional<ResourceLocation> startJigsawName,
            int maxDepth,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Optional<Heightmap.Types> projectStartToHeightmap,
            int maxDistanceFromCenter,
            List<PoolAliasBinding> poolAliases,
            DimensionPadding dimensionPadding,
            LiquidSettings liquidSettings
    ) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.poolAliases = poolAliases;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
    }

    public UndergroundNoCarveStructure(
            Structure.StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            int maxDepth,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Heightmap.Types projectStartToHeightmap
    ) {
        this(
                settings,
                startPool,
                Optional.empty(),
                maxDepth,
                startHeight,
                useExpansionHack,
                Optional.of(projectStartToHeightmap),
                80,
                List.of(),
                DEFAULT_DIMENSION_PADDING,
                DEFAULT_LIQUID_SETTINGS
        );
    }

    public UndergroundNoCarveStructure(
            Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack
    ) {
        this(
                settings,
                startPool,
                Optional.empty(),
                maxDepth,
                startHeight,
                useExpansionHack,
                Optional.empty(),
                80,
                List.of(),
                DEFAULT_DIMENSION_PADDING,
                DEFAULT_LIQUID_SETTINGS
        );
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int y = pickCandidateY(context.random(), context);
            BlockPos candidate = new BlockPos(chunkPos.getMinBlockX(), y, chunkPos.getMinBlockZ());

            DensityFunction finalDensity = context.randomState().router().finalDensity();
            java.util.function.BiPredicate<StructurePoolElement, BoundingBox> terrainCheck =
                    (element, box) -> isBoxOpenEnough(element, box, finalDensity, context.heightAccessor())
                   /* && isBoxFluidFree(box, context.chunkGenerator(), context.randomState(), context.heightAccessor())*/;

            Optional<Structure.GenerationStub> result = TerrainAvoidingJigsawPlacement.addPieces(
                    context, this.startPool, this.startJigsawName, this.maxDepth, candidate,
                    this.maxDistanceFromCenter, PoolAliasLookup.create(this.poolAliases, candidate, context.seed()),
                    this.liquidSettings, terrainCheck
            );
            if (result.isPresent()) return result;
        }
        return Optional.empty();
    }

    private int pickCandidateY(net.minecraft.util.RandomSource random, Structure.GenerationContext context) {
        return this.startHeight.sample(random, new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
    }

    // Ensures the box's far edge is always sampled even if (max - min) isn't a multiple of step —
    // this is the fix for pieces clipping right at their own boundary.
    private static int[] sampleCoords(int min, int max, int step) {
        if (max <= min) return new int[]{min};
        java.util.List<Integer> coords = new java.util.ArrayList<>();
        for (int v = min; v <= max; v += step) coords.add(v);
        if (coords.get(coords.size() - 1) != max) coords.add(max);
        int[] arr = new int[coords.size()];
        for (int i = 0; i < arr.length; i++) arr[i] = coords.get(i);
        return arr;
    }

    private boolean isBoxOpenEnough(StructurePoolElement element, BoundingBox box, DensityFunction finalDensity, LevelHeightAccessor heightAccessor) {
        int cx = (box.minX() + box.maxX()) / 2;
        int cy = (box.minY() + box.maxY()) / 2;
        int cz = (box.minZ() + box.maxZ()) / 2;
        double centerDensity = finalDensity.compute(new DensityFunction.SinglePointContext(cx, cy, cz));
        if (centerDensity > 2.0D) { // clearly deep in solid rock, not just borderline
            return false; // skip the expensive full grid entirely
        }

        BoundingBox padded = box.inflatedBy(PADDING);
        int[] xs = sampleCoords(padded.minX(), padded.maxX(), SAMPLE_STEP);
        int[] ys = sampleCoords(padded.minY(), padded.maxY(), SAMPLE_STEP);
        int[] zs = sampleCoords(padded.minZ(), padded.maxZ(), SAMPLE_STEP);

        int totalSamples = 0;
        int openSamples = 0;

        for (int x : xs) {
            for (int z : zs) {
                for (int y : ys) {
                    if (y < heightAccessor.getMinBuildHeight() || y >= heightAccessor.getMaxBuildHeight()) {
                        continue;
                    }

                    totalSamples++;

                    DensityFunction.SinglePointContext context =
                            new DensityFunction.SinglePointContext(x, y, z);

                    double density = finalDensity.compute(context);

                    if (density <= 0.0D) {
                        openSamples++;
                    }
                }
            }
        }

        if (totalSamples == 0) {
            return false;
        }

        double openFraction = (double) openSamples / totalSamples;
        return openFraction >= REQUIRED_OPEN_FRACTION;
    }

    private boolean isBoxFluidFree(BoundingBox box, ChunkGenerator generator, RandomState randomState, LevelHeightAccessor heightAccessor) {
        int[] xs = sampleCoords(box.minX(), box.maxX(), SAMPLE_STEP);
        int[] ys = sampleCoords(box.minY(), box.maxY(), SAMPLE_STEP);
        int[] zs = sampleCoords(box.minZ(), box.maxZ(), SAMPLE_STEP);

        for (int x : xs) {
            for (int z : zs) {
                NoiseColumn column = generator.getBaseColumn(x, z, heightAccessor, randomState);
                for (int y : ys) {
                    if (y < heightAccessor.getMinBuildHeight() || y >= heightAccessor.getMaxBuildHeight()) continue;

                    BlockState state = column.getBlock(y);
                    if (!state.getFluidState().isEmpty()) {
                        return false; // found water or lava — reject immediately
                    }
                }
            }
        }
        return true;
    }

    @Override
    public StructureType<?> type() {
        return CQStructureType.UNDERGROUND_NO_CARVE.get();
    }
}