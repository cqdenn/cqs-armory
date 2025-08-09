package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.items.BowType;
import io.redspace.atlasapi.api.AssetHandler;
import io.redspace.atlasapi.api.data.BakingPreparations;
import io.redspace.atlasapi.api.data.ModelLayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class WeaponsetAssetHandler extends AssetHandler {


    @Override
    public @NotNull List<SpriteSource> buildSpriteSources() {
        Map <String, ResourceLocation> permutaions = new HashMap<>();
        permutaions.put("diamond", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/diamond"));
        permutaions.put("copper", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/copper"));
        permutaions.put("gold", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/gold"));
        permutaions.put("netherite", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/netherite"));
        permutaions.put("withersteel", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/withersteel"));
        permutaions.put("sculk", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/sculk"));
        permutaions.put("amethyst", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/amethyst"));
        permutaions.put("blazing", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/blazing"));
        permutaions.put("umbrite", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/umbrite"));
        permutaions.put("living", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/living"));
        permutaions.put("obsidian", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/obsidian"));
        permutaions.put("iron", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/sword_key"));
        permutaions.put("dwarvensteel", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/dwarvensteel"));
        permutaions.put("silversteel", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/silversteel"));

        Map <String, ResourceLocation> bowPermutations = new HashMap<>();
        bowPermutations.put("diamond", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_diamond"));
        bowPermutations.put("netherite", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_netherite"));
        bowPermutations.put("copper", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_copper"));
        bowPermutations.put("iron", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_iron"));
        bowPermutations.put("gold", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_gold"));
        bowPermutations.put("withersteel", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_withersteel"));
        bowPermutations.put("sculk", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_sculk"));
        bowPermutations.put("amethyst", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_amethyst"));
        bowPermutations.put("blazing", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_blazing"));
        bowPermutations.put("umbrite", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_umbrite"));
        bowPermutations.put("living", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_living"));
        bowPermutations.put("obsidian", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_obsidian"));
        bowPermutations.put("dwarvensteel", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_dwarvensteel"));
        bowPermutations.put("silversteel", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_silversteel"));

        var weapon_source = new PalettedPermutations(List.of(
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_warhammer_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_warhammer_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_greatsword_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_greatsword_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_halberd_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_halberd_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_scythe_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_scythe_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_mace_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_mace_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_spear_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_spear_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_rapier_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_rapier_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_greataxe_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_greataxe_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_ice_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_fire_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_lightning_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_necromancy_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_arcane_handheld"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_holy_handheld")),ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/sword_key"), permutaions);
        var ingot_source = new PalettedPermutations(List.of(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/ingot_key")), ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/gold"), permutaions);
        var bow_source = new PalettedPermutations(List.of(
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/longbow"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/longbow_pulling_0"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/longbow_pulling_1"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/longbow_pulling_2"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/shortbow"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/shortbow_pulling_0"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/shortbow_pulling_1"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/shortbow_pulling_2"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/recurve"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/recurve_pulling_0"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/recurve_pulling_1"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/recurve_pulling_2")), ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/bow_palette_key"), bowPermutations
        );
        List<SpriteSource> spriteSources = new ArrayList<>();
        for (BowType bowType : BowType.values()) {
            for (int i=0;i<=2;i++) {
                spriteSources.add(new SingleFile(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, String.format("item/%s_arrow_%s", bowType.getArrowString(), i)), Optional.empty()));
            }
        }
        spriteSources.add(weapon_source);
        spriteSources.add(ingot_source);
        spriteSources.add(bow_source);
        return spriteSources;
    }

    @Override
    public @NotNull BakingPreparations makeBakedModelPreparations(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed) {
        ResourceLocation resources;
        if (itemStack.getItem() instanceof TieredItem tieredItem) {
            var path = getAtlasLocation(tieredItem, true);

            resources = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, path);
            return new BakingPreparations(List.of(new ModelLayer(resources, 1, Optional.empty())));
        } else {
            var tier = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath().split("_")[0];
            var path = String.format("item/ingot_key_%s", tier);
            resources = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, path);
            return new BakingPreparations(List.of(new ModelLayer(resources, 1, Optional.empty())));
        }



    }

    public static @NotNull String getAtlasLocation(TieredItem tieredItem, boolean gui) {
        var transform = gui ? "gui" : "handheld";
        var tier = tieredItem.getTier().toString().toLowerCase();
        var type = BuiltInRegistries.ITEM.getKey(tieredItem).getPath().split("_")[1];

        var path = String.format("item/iron_%s_%s_%s", type, transform, tier);
        return path;
    }

    public static @NotNull String getBowAtlasLocation(BowItem bowItem) {
        String[] name = BuiltInRegistries.ITEM.getKey(bowItem).getPath().split("_");
        var tier = name[0];
        var type = name[1];

        var path = String.format("item/%s_%s", type, tier);
        return path;
    }

    public static @NotNull String getBowAtlasLocation(BowItem bowItem, int pull) {
        String[] name = BuiltInRegistries.ITEM.getKey(bowItem).getPath().split("_");
        var tier = name[0];
        var type = name[1];

        var path = String.format("item/%s_pulling_%s_%s", type, pull, tier);
        return path;
    }

    public static Map <Holder<Item>, Integer> cache = new HashMap<>();
    static int counter = 0;



    @Override
    public int modelId(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed) {
        if (!cache.containsKey(itemStack.getItemHolder())) {
            counter++;
            cache.put(itemStack.getItemHolder(), counter);
        }
        return cache.get(itemStack.getItemHolder());

    }
}
