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

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource dwarvenCaveBlocks = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.29D, -0.28D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.26D, -0.25D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.25D, 0.26D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.28D, 0.29D),
                        MAGMA
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.49D, -0.48D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.46D, -0.45D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.45D, 0.46D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.48D, 0.49D),
                        MAGMA
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.69D, -0.68D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.66D, -0.65D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.65D, 0.66D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.68D, 0.69D),
                        MAGMA
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.89D, -0.88D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.86D, -0.85D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.85D, 0.86D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.88D, 0.89D),
                        MAGMA
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.09D, -0.08D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.06D, -0.05D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, -0.01D, 0.01D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.05D, 0.06D),
                        MAGMA
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.GRAVEL, 0.08D, 0.09D),
                        MAGMA
                ),

                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, -1.0D, -0.8D),
                        TUFF
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, -0.6D, -0.4D),
                        TUFF
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, -0.2D, 0.0D),
                        TUFF
                ),SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, 0.2D, 0.4D),
                        TUFF
                ),SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, 0.6D, 0.8D),
                        TUFF
                ),

                // Fallback
                SMOOTH_BASALT

        );

        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(BiomesRegistry.DWARVEN_CAVES),
                dwarvenCaveBlocks
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
