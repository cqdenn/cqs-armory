package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.ItemRegistry;
import com.example.cqsarmory.registry.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public final class CQItemTagsProvider extends ItemTagsProvider {
    public CQItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.AMETHYST_SHARD);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.IRON_INGOT);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.GOLD_INGOT);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.COPPER_INGOT);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.OBSIDIAN);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.DIAMOND);

        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.SCULK_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.LIVING_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.UMBRITE_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.SILVERSTEEL_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.DWARVEN_STEEL_WEAPONSET.ingot().get().asItem());

        tag(Tags.Items.MATERIALS_POWER_THREE).add(ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_THREE).add(ItemRegistry.BLAZING_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_THREE).add(Items.NETHERITE_INGOT);
    }
}
