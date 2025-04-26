package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.atlasapi.api.AssetHandler;
import io.redspace.atlasapi.api.data.BakingPreparations;
import io.redspace.atlasapi.api.data.ModelLayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WeaponsetAssetHandler extends AssetHandler {


    @Override
    public @NotNull List<SpriteSource> buildSpriteSources() {
        Map <String, ResourceLocation> permutaions = new HashMap<>();
        permutaions.put("diamond", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/diamond"));
        permutaions.put("copper", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/copper"));
        permutaions.put("gold", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/gold"));
        permutaions.put("netherite", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/netherite"));
        permutaions.put("withersteel", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/withersteel"));


        var weapon_source = new PalettedPermutations(List.of(
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_warhammer_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_greatsword_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_halberd_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_scythe_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_mace_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_spear_gui"),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/iron_rapier_gui")),ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/sword_key"), permutaions);
        var ingot_source = new PalettedPermutations(List.of(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/ingot_key")), ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "palettes/gold"), permutaions);
        return List.of(weapon_source, ingot_source);
    }

    @Override
    public @NotNull BakingPreparations makeBakedModelPreparations(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed) {
        ResourceLocation resources;
        if (itemStack.getItem() instanceof TieredItem tieredItem) {
            var tier = tieredItem.getTier().toString().toLowerCase();
            var type = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath().split("_")[1];

            var path = String.format("item/iron_%s_gui_%s", type, tier);

            resources = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, path);
            return new BakingPreparations(List.of(new ModelLayer(resources, 1, Optional.empty())));
        } else {
            var tier = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath().split("_")[0];
            var path = String.format("item/ingot_key_%s", tier);
            resources = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, path);
            return new BakingPreparations(List.of(new ModelLayer(resources, 1, Optional.empty())));
        }



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
