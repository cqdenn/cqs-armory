package com.example.cqsarmory.data.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.*;
import java.util.function.BiPredicate;

public final class TerrainAvoidingJigsawPlacement {

    private static final java.util.Set<String> PADDING_EXEMPT_PIECES = java.util.Set.of(
            "Single[Left[cqs_armory:dwarven_caves/minecart_track/minecart_pillar_1]]"
    );

    private static final java.util.Set<String> MAX_DEPTH_EXEMPT_PIECES = java.util.Set.of(
            "Single[Left[cqs_armory:dwarven_caves/minecart_track/minecart_pillar_1]]"
    );

    private static final java.util.List<String> TRACK_END_PIECES = java.util.List.of(
            "cqs_armory:dwarven_caves/minecart_track/minecart_end"
    );

    private static boolean isPaddingExempt(StructurePoolElement element) {
        if (element instanceof net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement single) {
            return PADDING_EXEMPT_PIECES.contains(single.toString());
        }
        return false;
    }

    private static boolean isDepthExempt(StructurePoolElement element) {
        if (element instanceof net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement single) {
            return MAX_DEPTH_EXEMPT_PIECES.contains(single.toString());
        }
        return false;
    }

    private static StructurePoolElement randomTrackEndPiece(RandomSource random) {
        List<String> options = new ArrayList<>(TRACK_END_PIECES);
        String location = options.get(random.nextInt(options.size()));
        return net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement
                .single(location)
                .apply(net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection.RIGID);
    }

    /**
     * @param terrainCheck tests whether a given piece (its template + world-space bounding box)
     *                      has enough open space to be placed. Takes the element so callers can
     *                      special-case specific pieces (e.g. exempting them from padding checks).
     */
    public static Optional<Structure.GenerationStub> addPieces(
            Structure.GenerationContext context,
            Holder<StructureTemplatePool> startPool,
            Optional<ResourceLocation> startJigsawName,
            int maxDepth,
            BlockPos startPos,
            int maxDistanceFromCenter,
            PoolAliasLookup aliasLookup,
            LiquidSettings liquidSettings,
            BiPredicate<StructurePoolElement, BoundingBox> terrainCheck
    ) {
        RandomSource random = context.random();
        StructureTemplateManager structureManager = context.structureTemplateManager();

        StructurePoolElement startElement = startPool.value().getRandomTemplate(random);
        if (startElement == EmptyPoolElement.INSTANCE) {
            return Optional.empty();
        }

        List<Rotation> rotations = new ArrayList<>(List.of(Rotation.values()));
        Collections.shuffle(rotations, new Random(random.nextLong()));

        for (Rotation startRotation : rotations) {
            BoundingBox startBox = startElement.getBoundingBox(structureManager, startPos, startRotation);
            if (!terrainCheck.test(startElement, startBox)) {
                continue;
            }

            int groundDelta = startElement.getGroundLevelDelta();
            BlockPos piecePos = startPos;

            List<PoolElementStructurePiece> pieces = new ArrayList<>();
            PoolElementStructurePiece startPiece = new PoolElementStructurePiece(
                    structureManager, startElement, piecePos, groundDelta, startRotation, startBox, liquidSettings);
            pieces.add(startPiece);

            Deque<Entry> queue = new ArrayDeque<>();
            queue.add(new Entry(startPiece, 0));

            while (!queue.isEmpty()) {
                Entry entry = queue.poll();
                //if (entry.depth() >= maxDepth) continue;

                expand(entry, context, aliasLookup, liquidSettings, maxDistanceFromCenter,
                        startPos, pieces, queue, terrainCheck, maxDepth);
            }

            return Optional.of(new Structure.GenerationStub(piecePos,
                    builder -> pieces.forEach(builder::addPiece)));
        }

        return Optional.empty();
    }

    private record Entry(PoolElementStructurePiece piece, int depth) {}

    private static void expand(
            Entry entry,
            Structure.GenerationContext context,
            PoolAliasLookup aliasLookup,
            LiquidSettings liquidSettings,
            int maxDistanceFromCenter,
            BlockPos origin,
            List<PoolElementStructurePiece> pieces,
            Deque<Entry> queue,
            BiPredicate<StructurePoolElement, BoundingBox> terrainCheck,
            int maxDepth
    ) {
        PoolElementStructurePiece current = entry.piece();
        RandomSource random = context.random();
        StructureTemplateManager structureManager = context.structureTemplateManager();

        List<StructureTemplate.StructureBlockInfo> jigsaws = current.getElement()
                .getShuffledJigsawBlocks(structureManager, current.getPosition(), current.getRotation(), random);

        for (StructureTemplate.StructureBlockInfo jigsawBlock : jigsaws) {
            CompoundTag data = jigsawBlock.nbt();
            if (data == null) continue;

            String targetName = data.getString("target");

            ResourceLocation rawPoolLoc = ResourceLocation.tryParse(data.getString("pool"));
            if (rawPoolLoc == null) continue;

            ResourceKey<StructureTemplatePool> rawPoolKey =
                    ResourceKey.create(Registries.TEMPLATE_POOL, rawPoolLoc);
            ResourceKey<StructureTemplatePool> poolKey = aliasLookup.lookup(rawPoolKey);

            Optional<Holder.Reference<StructureTemplatePool>> targetPoolHolder = context.registryAccess()
                    .registryOrThrow(Registries.TEMPLATE_POOL)
                    .getHolder(poolKey);
            if (targetPoolHolder.isEmpty()) continue;

            List<StructurePoolElement> candidates =
                    new ArrayList<>(targetPoolHolder.get().value().getShuffledTemplates(random));

            Direction jigsawFacing = JigsawBlock.getFrontFacing(jigsawBlock.state());
            BlockPos jigsawWorldPos = jigsawBlock.pos();

            tryPlaceOneOf(candidates, targetName, jigsawFacing, jigsawWorldPos,
                    context, liquidSettings, maxDistanceFromCenter, origin, pieces, terrainCheck,
                    entry.depth(), queue, maxDepth);
        }
    }

    private static boolean tryPlaceOneOf(
            List<StructurePoolElement> candidates,
            String targetName,
            Direction jigsawFacing,
            BlockPos jigsawWorldPos,
            Structure.GenerationContext context,
            LiquidSettings liquidSettings,
            int maxDistanceFromCenter,
            BlockPos origin,
            List<PoolElementStructurePiece> pieces,
            BiPredicate<StructurePoolElement, BoundingBox> terrainCheck,
            int depth,
            Deque<Entry> queue,
            int maxDepth
    ) {
        RandomSource random = context.random();
        StructureTemplateManager structureManager = context.structureTemplateManager();
        BlockPos targetConnectPos = jigsawWorldPos.relative(jigsawFacing);

        for (StructurePoolElement candidateTemplate : candidates) {
            if (candidateTemplate == EmptyPoolElement.INSTANCE) continue;

            for (Rotation candidateRotation : Rotation.values()) {

                List<StructureTemplate.StructureBlockInfo> candidateJigsaws = candidateTemplate
                        .getShuffledJigsawBlocks(structureManager, BlockPos.ZERO, candidateRotation, random);

                for (StructureTemplate.StructureBlockInfo candidateJigsaw : candidateJigsaws) {
                    CompoundTag candidateData = candidateJigsaw.nbt();
                    if (candidateData == null) continue;
                    if (!candidateData.getString("name").equals(targetName)) continue;
                    if (depth >= maxDepth && !isDepthExempt(candidateTemplate)) continue;

                    Direction candidateFacing = JigsawBlock.getFrontFacing(candidateJigsaw.state());
                    if (candidateFacing != jigsawFacing.getOpposite()) continue;

                    BlockPos offset = targetConnectPos.subtract(candidateJigsaw.pos());
                    BoundingBox candidateBox = candidateTemplate.getBoundingBox(structureManager, offset, candidateRotation);

                    if (offset.distSqr(origin) > (long) maxDistanceFromCenter * maxDistanceFromCenter) continue;

                    boolean overlaps = pieces.stream().anyMatch(p -> p.getBoundingBox().intersects(candidateBox));
                    if (overlaps) continue;

                    if (!isPaddingExempt(candidateTemplate) && !terrainCheck.test(candidateTemplate, candidateBox)) continue;

                    int groundDelta = candidateTemplate.getGroundLevelDelta();
                    PoolElementStructurePiece newPiece = new PoolElementStructurePiece(
                            structureManager, candidateTemplate, offset, groundDelta,
                            candidateRotation, candidateBox, liquidSettings);

                    pieces.add(newPiece);
                    queue.add(new Entry(newPiece, depth + 1));
                    return true;
                }
            }
        }

        StructurePoolElement candidateTemplate = randomTrackEndPiece(random);

        for (Rotation candidateRotation : Rotation.values()) {

            List<StructureTemplate.StructureBlockInfo> candidateJigsaws = candidateTemplate
                    .getShuffledJigsawBlocks(structureManager, BlockPos.ZERO, candidateRotation, random);

            for (StructureTemplate.StructureBlockInfo candidateJigsaw : candidateJigsaws) {
                CompoundTag candidateData = candidateJigsaw.nbt();
                if (candidateData == null) {
                    continue;
                }
                //if (!candidateData.getString("name").equals(targetName)) continue;

                Direction candidateFacing = JigsawBlock.getFrontFacing(candidateJigsaw.state());
                if (candidateFacing != jigsawFacing.getOpposite()) {
                    continue;
                }

                BlockPos offset = targetConnectPos.subtract(candidateJigsaw.pos());
                BoundingBox candidateBox = candidateTemplate.getBoundingBox(structureManager, offset, candidateRotation);

                //if (offset.distSqr(origin) > (long) maxDistanceFromCenter * maxDistanceFromCenter) continue;

                boolean overlaps = pieces.stream().anyMatch(p -> p.getBoundingBox().intersects(candidateBox));
                if (overlaps) {
                    continue;
                }

                //if (!isPaddingExempt(candidateTemplate) && !terrainCheck.test(candidateTemplate, candidateBox)) continue;

                int groundDelta = candidateTemplate.getGroundLevelDelta();
                PoolElementStructurePiece newPiece = new PoolElementStructurePiece(
                        structureManager, candidateTemplate, offset, groundDelta,
                        candidateRotation, candidateBox, liquidSettings);

                pieces.add(newPiece);
                queue.add(new Entry(newPiece, depth + 1));
                return true;
            }
        }
        return false;
    }
}
