package com.example.cqsarmory.data.biomes;

import com.example.cqsarmory.registry.BiomesRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class CQSurfaceRules {
    private static final SurfaceRules.RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource SMOOTH_BASALT = makeStateRule(Blocks.SMOOTH_BASALT);
    private static final SurfaceRules.RuleSource OBSIDIAN = makeStateRule(Blocks.OBSIDIAN);
    private static final SurfaceRules.RuleSource COAL_BLOCK = makeStateRule(Blocks.COAL_BLOCK);
    private static final SurfaceRules.RuleSource TUFF = makeStateRule(Blocks.TUFF);
    private static final SurfaceRules.RuleSource MAGMA = makeStateRule(Blocks.MAGMA_BLOCK);
    private static final SurfaceRules.RuleSource COBBLED_DEEPSLATE = makeStateRule(Blocks.COBBLED_DEEPSLATE);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource COAL_IN_STONE = blockWithSpeckles(Blocks.STONE, Blocks.COAL_ORE, 0.5D);
    private static final SurfaceRules.RuleSource COPPER_IN_STONE = blockWithSpeckles(Blocks.STONE, Blocks.COPPER_ORE, 0.5D);
    private static final SurfaceRules.RuleSource GOLD_IN_STONE = blockWithSpeckles(Blocks.STONE, Blocks.GOLD_ORE, 0.3D);
    private static final SurfaceRules.RuleSource LAPIS_IN_STONE = blockWithSpeckles(Blocks.STONE, Blocks.LAPIS_ORE, 0.2D);
    private static final SurfaceRules.RuleSource IRON_IN_STONE = blockWithSpeckles(Blocks.STONE, Blocks.IRON_ORE, 0.2D);
    private static final SurfaceRules.RuleSource REDSTONE_IN_STONE = blockWithSpeckles(Blocks.STONE, Blocks.REDSTONE_ORE, 0.2D);
    private static final SurfaceRules.RuleSource EMERALD_IN_STONE = blockWithSpeckles(Blocks.STONE, Blocks.EMERALD_ORE, 0.2D);
    private static final SurfaceRules.RuleSource DIAMOND_IN_STONE = blockWithSpeckles(Blocks.STONE, Blocks.DIAMOND_ORE, 0.2D);


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource dwarvenCaveBlocks = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.29D, -0.28D),
                        COAL_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.26D, -0.25D),
                        COPPER_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.25D, 0.26D),
                        IRON_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.28D, 0.29D),
                        LAPIS_IN_STONE
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.49D, -0.48D),
                        COPPER_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.46D, -0.45D),
                        REDSTONE_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.45D, 0.46D),
                        COAL_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.48D, 0.49D),
                        IRON_IN_STONE
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.69D, -0.68D),
                        GOLD_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.66D, -0.65D),
                        COPPER_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.65D, 0.66D),
                        LAPIS_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.68D, 0.69D),
                        EMERALD_IN_STONE
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.89D, -0.88D),
                        DIAMOND_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.86D, -0.85D),
                        REDSTONE_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.85D, 0.86D),
                        GOLD_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.88D, 0.89D),
                        COAL_IN_STONE
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.09D, -0.08D),
                        GOLD_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.06D, -0.05D),
                        COAL_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.01D, 0.01D),
                        COPPER_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.05D, 0.06D),
                        EMERALD_IN_STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.08D, 0.09D),
                        IRON_IN_STONE
                ),

                /*SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, -1.0D, -0.8D),
                        DEEPSLATE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, -0.6D, -0.4D),
                        DEEPSLATE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, -0.2D, 0.0D),
                        DEEPSLATE
                ),SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, 0.2D, 0.4D),
                        DEEPSLATE
                ),SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, 0.6D, 0.8D),
                        DEEPSLATE
                ),*/

                // Fallback
                DEEPSLATE

        );

        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(BiomesRegistry.DWARVEN_CAVES),
                dwarvenCaveBlocks
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    private static SurfaceRules.RuleSource blockWithSpeckles(
            Block baseBlock,
            Block speckleBlock,
            double threshold) {
        SurfaceRules.RuleSource base = makeStateRule(baseBlock);
        SurfaceRules.RuleSource speckle = makeStateRule(speckleBlock);
        double diff = (1- threshold) * 0.5 * 0.01;

        SurfaceRules.RuleSource sequence = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.29D + diff, -0.28D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.26D + diff, -0.25D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.25D + diff, 0.26D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.28D + diff, 0.29D - diff),
                        speckle
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.49D + diff, -0.48D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.46D + diff, -0.45D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.45D + diff, 0.46D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.48D + diff, 0.49D - diff),
                        speckle
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.69D + diff, -0.68D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.66D + diff, -0.65D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.65D + diff, 0.66D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.68D + diff, 0.69D - diff),
                        speckle
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.89D + diff, -0.88D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.86D + diff, -0.85D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.85D + diff, 0.86D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.88D + diff, 0.89D - diff),
                        speckle
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.09D + diff, -0.08D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.06D + diff, -0.05D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.01D + diff,  0.01D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.05D + diff,  0.06D - diff),
                        speckle
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.08D + diff, 0.09D - diff),
                        speckle
                ),
                base
        );

        return sequence;
    }
}
