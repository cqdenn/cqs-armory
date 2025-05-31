package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.example.cqsarmory.registry.ItemRegistry.WEAPONSETS;

public class SwordTagsProvider extends IntrinsicHolderTagsProvider<Item> {


    protected SwordTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, item -> ResourceKey.create(Registries.ITEM, BuiltInRegistries.ITEM.getKey(item)), CqsArmory.MODID, existingFileHelper);
    }

    public static final TagKey<Item> swordTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "swords"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (ItemRegistry.Weaponset weaponset : WEAPONSETS) {
            for (DeferredItem weapon : weaponset) {
                tag(swordTag).add((Item) weapon.get());
            }
        }
        for (DeferredHolder weapon : ItemRegistry.ITEMS.getEntries()) {
            if (weapon.get() instanceof SwordItem) {
                tag(swordTag).add((Item)weapon.get());
            }
        }
        tag(swordTag).add(ItemRegistry.MJOLNIR.get());
    }
}
