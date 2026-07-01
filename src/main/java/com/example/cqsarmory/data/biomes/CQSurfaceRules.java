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
    private static final SurfaceRules.RuleSource COAL = makeStateRule(Blocks.COAL_ORE);
    private static final SurfaceRules.RuleSource COPPER = makeStateRule(Blocks.COPPER_ORE);
    private static final SurfaceRules.RuleSource GOLD = makeStateRule(Blocks.GOLD_ORE);
    private static final SurfaceRules.RuleSource LAPIS = makeStateRule(Blocks.LAPIS_ORE);
    private static final SurfaceRules.RuleSource IRON = makeStateRule(Blocks.IRON_ORE);
    private static final SurfaceRules.RuleSource REDSTONE = makeStateRule(Blocks.REDSTONE_ORE);
    private static final SurfaceRules.RuleSource EMERALD = makeStateRule(Blocks.EMERALD_ORE);
    private static final SurfaceRules.RuleSource DIAMOND = makeStateRule(Blocks.DIAMOND_ORE);


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource dwarvenCaveBlocks = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),

                dwarvenCavesOreVeins(),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.29D, -0.28D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.26D, -0.25D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.25D, 0.26D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.28D, 0.29D),
                        STONE
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.49D, -0.48D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.46D, -0.45D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.45D, 0.46D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.48D, 0.49D),
                        STONE
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.69D, -0.68D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.66D, -0.65D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.65D, 0.66D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.68D, 0.69D),
                        STONE
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.89D, -0.88D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.86D, -0.85D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.85D, 0.86D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.88D, 0.89D),
                        STONE
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.09D, -0.08D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.06D, -0.05D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.01D, 0.01D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.05D, 0.06D),
                        STONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.08D, 0.09D),
                        STONE
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

    private static SurfaceRules.RuleSource dwarvenCavesOreVeins() {
        double commonOreThreshold = 0.5;
        double midOreThreshold = 0.3;
        double rareOreThreshold = 0.2;
        double commonDiff = (1 - commonOreThreshold) * 0.5 * 0.01;
        double midDiff = (1 - midOreThreshold) * 0.5 * 0.01;
        double rareDiff = (1 - rareOreThreshold) * 0.5 * 0.01;

        SurfaceRules.RuleSource sequence = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.29D + commonDiff, -0.28D - commonDiff),
                        COAL
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.26D + commonDiff, -0.25D - commonDiff),
                        COPPER
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.25D + midDiff, 0.26D - midDiff),
                        IRON
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.28D + rareDiff, 0.29D - rareDiff),
                        LAPIS
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.49D + commonDiff, -0.48D - commonDiff),
                        COPPER
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.46D + rareDiff, -0.45D - rareDiff),
                        REDSTONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.45D + commonDiff, 0.46D - commonDiff),
                        COAL
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.48D + midDiff, 0.49D - midDiff),
                        IRON
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.69D + midDiff, -0.68D - midDiff),
                        GOLD
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.66D + commonDiff, -0.65D - commonDiff),
                        COPPER
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.65D + rareDiff, 0.66D - rareDiff),
                        LAPIS
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.68D + rareDiff, 0.69D - rareDiff),
                        EMERALD
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.89D + rareDiff, -0.88D - rareDiff),
                        DIAMOND
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.86D + midDiff, -0.85D - midDiff),
                        REDSTONE
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.85D + midDiff, 0.86D - midDiff),
                        GOLD
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.88D + commonDiff, 0.89D - commonDiff),
                        COAL
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.09D + commonDiff, -0.08D - commonDiff),
                        GOLD
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.06D + commonDiff, -0.05D - commonDiff),
                        COAL
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.01D + commonDiff, 0.01D - commonDiff),
                        COPPER
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.05D + rareDiff, 0.06D - rareDiff),
                        EMERALD
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.08D + midDiff, 0.09D - midDiff),
                        IRON
                )
        );

        return sequence;
    }
}
